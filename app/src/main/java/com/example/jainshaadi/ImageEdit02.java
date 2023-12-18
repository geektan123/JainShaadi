package com.example.jainshaadi;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ImageEdit02 extends DialogFragment {
    ShapeableImageView shapeableImageView;
    Uri imageUri;

    Dialog dialog;
    String tempimageUri;
    Uri uncompressedImage;
    ProgressBar progressBar;

    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    final private StorageReference reference = FirebaseStorage.getInstance().getReference();
    LinearLayout layout;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    startCropActivity(selectedImageUri);
                }
            }
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_image, container, false);
        shapeableImageView = view.findViewById(R.id.shapeable_image_view);
        layout = view.findViewById(R.id.Next);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        layout.setOnClickListener(v -> {
            if (imageUri != null) {
                int imageSizeInBytes = getImageSize(uncompressedImage);
                if (imageSizeInBytes > 10 * 1024 * 1024) { // 10 MB limit
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(requireContext(), "Please Upload Smaller Image Size", Toast.LENGTH_SHORT).show();
                } else {
                    layout.setEnabled(false);
                    uploadUncompressedCroppedImageToFirebase(uncompressedImage);
                }
            } else {
                Toast.makeText(requireContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
            }
        });

        shapeableImageView.setOnClickListener(v -> openImagePickerWithCrop());

        return view;
    }

    private void openImagePickerWithCrop() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(galleryIntent);
    }

    private void startCropActivity(Uri imageUri) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.withAspectRatio(1, 1);
        UCrop.of(imageUri, Uri.fromFile(new File(requireContext().getCacheDir(), "cropped_image")))
                .withOptions(options)
                .start(requireContext(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UCrop.REQUEST_CROP && resultCode == getActivity().RESULT_OK) {
            handleCroppedImage(UCrop.getOutput(data));
        }
    }

    private void handleCroppedImage(Uri croppedUri) {
        if (croppedUri != null) {
            // Display the compressed image in the ShapeableImageView
            shapeableImageView.setImageBitmap(null);
            shapeableImageView.setImageURI(croppedUri);
            imageUri = croppedUri;
            uncompressedImage = croppedUri;
        }
    }

    private void uploadUncompressedCroppedImageToFirebase(Uri croppedUri) {
        dialog.setCancelable(false);
        if (croppedUri != null) {
            progressBar.setVisibility(View.VISIBLE);

            StorageReference fileReference = reference.child("uncropped_image_" + System.currentTimeMillis() + ".jpg");

            fileReference.putFile(croppedUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            saveUncompressedImageUrlToDatabase(imageUrl);
                        });
                    })
                    .addOnFailureListener(e -> {
                        dialog.setCancelable(true);
                        layout.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(requireContext(), "Failed to upload uncropped image", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void saveUncompressedImageUrlToDatabase(String imageUrl) {

        HashMap<String, Object> imageMap = new HashMap<>();
        imageMap.put("image02", imageUrl);

        String userId = FirebaseAuth.getInstance().getUid();
        imageMap.put("profileId", userId);
        databaseReference.child(userId).child("imageUrl1").get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        tempimageUri = documentSnapshot.getValue(String.class);
                        databaseReference.child(userId).updateChildren(imageMap)
                                .addOnSuccessListener(aVoid -> {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    deleteImage(tempimageUri);
                                    dismiss();
                                })
                                .addOnFailureListener(e -> {
                                    dialog.setCancelable(true);
                                    layout.setEnabled(true);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Log.e("UploadError", e.getMessage());
                                });
                    } else {
                        dialog.setCancelable(true);
                        Toast.makeText(requireContext(), "Failed to upload image, Try again", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->{
                    dialog.setCancelable(true);
                    Toast.makeText(requireContext(), "Failed to upload image, Try again", Toast.LENGTH_SHORT).show();});
    }

    private int getImageSize(Uri imageUri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
            return inputStream != null ? inputStream.available() : 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = super.onCreateDialog(savedInstanceState);

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setAttributes(params);

        return dialog;
    }

    public void deleteImage(String imageUrl) {
        dialog.setCancelable(false);
        // Get a reference to the Firebase Storage instance
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Convert the download URL to a StorageReference
        StorageReference photoRef = storage.getReferenceFromUrl(imageUrl);

        // Delete the file
        photoRef.delete()
                .addOnSuccessListener(aVoid -> {
//                    Toast.makeText(requireContext(), "Sucessfully Deleted Prev Image", Toast.LENGTH_SHORT).show();
                    dialog.setCancelable(true);
                    dismiss();
                })
                .addOnFailureListener(exception -> {
//                    Toast.makeText(requireContext(), "Failed to Delete", Toast.LENGTH_SHORT).show();
                    dialog.setCancelable(true);
                    dismiss();
                });

    }
}
