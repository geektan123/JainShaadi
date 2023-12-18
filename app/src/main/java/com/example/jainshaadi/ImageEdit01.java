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

public class ImageEdit01 extends DialogFragment {
    ShapeableImageView shapeableImageView;
    Uri imageUri ;
    String tempimageUri;
    String tempimageUri2;
    Uri uncompressedImage;
    Dialog dialog;
    ProgressBar progressBar;
    boolean uncompressedImageUploaded = false;

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
                    dialog.setCancelable(false);
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
                        layout.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        dialog.setCancelable(true);
                        Toast.makeText(requireContext(), "Failed to upload uncropped image", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void saveUncompressedImageUrlToDatabase(String imageUrl) {
        HashMap<String, Object> imageMap = new HashMap<>();
        imageMap.put("image01", imageUrl);

        String userId = FirebaseAuth.getInstance().getUid();
        imageMap.put("profileId", userId);

        databaseReference.child(userId).updateChildren(imageMap)
                .addOnSuccessListener(aVoid -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    uncompressedImageUploaded = true;
                    uploadCompressedImageToFirebase();
                })
                .addOnFailureListener(e -> {
                    dialog.setCancelable(true);
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.e("UploadError", e.getMessage());
                });
    }

    private void uploadCompressedImageToFirebase() {
        if (imageUri != null) {
            progressBar.setVisibility(View.VISIBLE);

            Bitmap compressedBitmap = compressImage(imageUri);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            String filename = "compressed_image_" + System.currentTimeMillis() + ".jpg";

            reference.child(filename)
                    .putBytes(byteArray)
                    .addOnSuccessListener(taskSnapshot -> {
                        reference.child(filename).getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            saveCompressedImageUrlToDatabase(imageUrl);
                        });
                    })
                    .addOnFailureListener(e -> {
                        dialog.setCancelable(true);
                        layout.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(requireContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private Bitmap compressImage(Uri imageUri) {
        Bitmap originalBitmap = null;
        try {
            originalBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int originalSizeInBytes = getBitmapSize(originalBitmap);
        Log.e("OriginalImageSize", "Size of original image: " + originalSizeInBytes + " bytes");

        int croppedWidth = shapeableImageView.getWidth();
        int croppedHeight = shapeableImageView.getHeight();
        int compressedWidth = croppedWidth / 5;
        int compressedHeight = croppedHeight / 5;
        Bitmap compressedBitmap = decodeSampledBitmap(originalBitmap, compressedWidth, compressedHeight);
        int sizeInBytes = getBitmapSize(compressedBitmap);
        Log.e("CompressedImageSize", "Size of compressed image: " + sizeInBytes + " bytes");
        return compressedBitmap;
    }

    private void saveCompressedImageUrlToDatabase(String imageUrl) {
        HashMap<String, Object> imageMap = new HashMap<>();
        imageMap.put("imageUrl1", imageUrl);

        String userId = FirebaseAuth.getInstance().getUid();
        imageMap.put("profileId", userId);
        databaseReference.child(userId).get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        tempimageUri = documentSnapshot.child("imageUrl1").getValue(String.class);
                        tempimageUri2 = documentSnapshot.child("image01").getValue(String.class);
                        databaseReference.child(userId).updateChildren(imageMap)
                                .addOnSuccessListener(aVoid -> {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    deleteImage(tempimageUri);
                                    deleteImage(tempimageUri2);
                                    Toast.makeText(requireContext(), "Profile Image uploaded successfully", Toast.LENGTH_SHORT).show();

                                })
                                .addOnFailureListener(e -> {
                                    dialog.setCancelable(true);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(requireContext(), "Failed to upload image, Try again", Toast.LENGTH_SHORT).show();
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

    private Bitmap decodeSampledBitmap(Bitmap bitmap, int reqWidth, int reqHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float widthRatio = (float) reqWidth / width;
        float heightRatio = (float) reqHeight / height;

        float scaleFactor = Math.min(widthRatio, heightRatio);

        return Bitmap.createScaledBitmap(bitmap,
                Math.round(width * scaleFactor),
                Math.round(height * scaleFactor),
                false);
    }

    private int getBitmapSize(Bitmap bitmap) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
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
