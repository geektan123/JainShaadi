package com.example.jainshaadi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Completeform_Image_02 extends AppCompatActivity {

    ShapeableImageView shapeableImageView;
    Uri imageUri;
    Uri uncompressedImage;
    ProgressBar progressBar;
    boolean uncompressedImageUploaded = false;

    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    final private StorageReference reference = FirebaseStorage.getInstance().getReference();
    LinearLayout layout;

    // ActivityResultLauncher for image picker
    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    startCropActivity(selectedImageUri);
                }
            }
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_form_image_02);
        getSupportActionBar().hide();

        shapeableImageView = findViewById(R.id.shapeable_image_view);
        layout = findViewById(R.id.Next);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        layout.setOnClickListener(v -> {
            if (imageUri != null) {
                int imageSizeInBytes = getImageSize(uncompressedImage);
                if (imageSizeInBytes > 10 * 1024 * 1024) { // 10 MB limit
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Completeform_Image_02.this, "Please Upload Smaller Image Size", Toast.LENGTH_SHORT).show();
                }
                else {
                    layout.setEnabled(false);
                    uploadUncompressedCroppedImageToFirebase(uncompressedImage);

                }
            } else {
                Toast.makeText(Completeform_Image_02.this, "Please Select Image", Toast.LENGTH_SHORT).show();
            }
        });

        shapeableImageView.setOnClickListener(v -> openImagePickerWithCrop());
    }

    private void openImagePickerWithCrop() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(galleryIntent);
    }

    private void startCropActivity(Uri imageUri) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.withAspectRatio(1, 1);
        UCrop.of(imageUri, Uri.fromFile(new File(getCacheDir(), "cropped_image")))
                .withOptions(options)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
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
                        Toast.makeText(Completeform_Image_02.this, "Failed to upload uncropped image", Toast.LENGTH_SHORT).show();
                    });
        }
    }
    private void saveUncompressedImageUrlToDatabase(String imageUrl) {
        HashMap<String, Object> imageMap = new HashMap<>();
        imageMap.put("image03", imageUrl);
        imageMap.put("status","Completed");

        String userId = FirebaseAuth.getInstance().getUid();
//        imageMap.put("profileId", userId);

        databaseReference.child(userId).updateChildren(imageMap)
                .addOnSuccessListener(aVoid -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Completeform_Image_02.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                })
                .addOnFailureListener(e -> {
                    layout.setEnabled(true);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Completeform_Image_02.this, "Failed to upload image, Try again", Toast.LENGTH_SHORT).show();
                    Log.e("UploadError", e.getMessage());
                });
    }

    private int getImageSize(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            return inputStream != null ? inputStream.available() : 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}