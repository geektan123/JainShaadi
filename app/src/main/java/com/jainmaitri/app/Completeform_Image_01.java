package com.jainmaitri.app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Completeform_Image_01 extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;

    ShapeableImageView shapeableImageView;
    Uri imageUri;
    Uri uncompressedImage;
    ProgressBar progressBar;
    boolean uncompressedImageUploaded = false;
    private static  String READ_STORAGE_PERMISSION;
    private int REQUEST_CODE = 11;

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
        setContentView(R.layout.activity_complete_form_image_01);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.verification_bar);
        ImageView BackButton = actionBar.getCustomView().findViewById(R.id.logo);
        actionBar.getCustomView().setBackgroundColor(Color.parseColor("#FFFFFF"));
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for the logo
                onBackPressed();
            }
        });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            READ_STORAGE_PERMISSION = Manifest.permission.READ_MEDIA_IMAGES;
        }else {
            READ_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;

        }

        shapeableImageView = findViewById(R.id.shapeable_image_view);
        layout = findViewById(R.id.Next);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        layout.setEnabled(true);

        layout.setOnClickListener(v -> {
            if (imageUri != null) {
                int imageSizeInBytes = getImageSize(uncompressedImage);
                if (imageSizeInBytes > 10 * 1024 * 1024) { // 10 MB limit
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Completeform_Image_01.this, "Please Upload Smaller Image Size", Toast.LENGTH_SHORT).show();
                }
                else {
                    layout.setEnabled(false);
                    uploadUncompressedCroppedImageToFirebase(uncompressedImage);

                }
            } else {
                Toast.makeText(Completeform_Image_01.this, "Please Select Image", Toast.LENGTH_SHORT).show();
            }
        });

        shapeableImageView.setOnClickListener(v -> {
            showPermissionDialog();
        });
    }

    private void showPermissionDialog() {

        if (ContextCompat.checkSelfPermission(this,READ_STORAGE_PERMISSION)  == PackageManager.PERMISSION_GRANTED
        ){
            openImagePickerWithCrop();
//            Toast.makeText(this, "Permission accepted", Toast.LENGTH_SHORT).show();

        }else {
            ActivityCompat.requestPermissions(this, new String[]{ READ_STORAGE_PERMISSION },REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                ){
                    openImagePickerWithCrop();

                }
                else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }

        }else {
            showPermissionDialog();
        }

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
                        progressBar.setVisibility(View.INVISIBLE);
                        layout.setEnabled(true);
                        Toast.makeText(Completeform_Image_01.this, "Failed to upload uncropped image", Toast.LENGTH_SHORT).show();
                    });
        }
    }
    private void saveUncompressedImageUrlToDatabase(String imageUrl) {
        HashMap<String, Object> imageMap = new HashMap<>();
        imageMap.put("image02", imageUrl);

        String userId = FirebaseAuth.getInstance().getUid();
//        imageMap.put("profileId", userId);

        databaseReference.child(userId).updateChildren(imageMap)
                .addOnSuccessListener(aVoid -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Completeform_Image_01.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Completeform_Image_02.class);
                    startActivity(i);
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Completeform_Image_01.this, "Failed to upload image, Try again", Toast.LENGTH_SHORT).show();
                    Log.e("UploadError", e.getMessage());
                    layout.setEnabled(true);
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
    public void onStart()
    {
        super.onStart();
        layout.setEnabled(true);
    }
}
