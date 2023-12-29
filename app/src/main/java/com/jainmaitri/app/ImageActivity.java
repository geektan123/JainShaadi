package com.jainmaitri.app;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

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
import com.google.firebase.messaging.FirebaseMessaging;
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

public class ImageActivity extends AppCompatActivity {

    ShapeableImageView shapeableImageView;
    Uri imageUri;
    Uri uncompressedImage;
    ProgressBar progressBar;
    boolean uncompressedImageUploaded = false;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    /*private static final String CAMERA_PERMISSION = Manifest.permission.POST_NOTIFICATIONS;
    private static final String LOCATION_PERMISSION =  Manifest.permission.ACCESS_FINE_LOCATION;*/
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
        setContentView(R.layout.activity_image);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_regestration);
        ImageView BackButton = actionBar.getCustomView().findViewById(R.id.logo);
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

        TextView Question = findViewById(R.id.Question);
        Intent intent = getIntent();
        String gen = intent.getStringExtra("Gender");
        String acc = intent.getStringExtra("Account");
        if(acc.equals("1"))
        {
            Question.setText("Add your recent picture");
        }
        else
        {
            if(gen.equals("1"))
            {
                Question.setText("Add his recent picture");
            }
            else if(gen.equals("2"))
            {
                Question.setText("Add her recent picture");
            }
        }

        layout.setOnClickListener(v -> {
            if (imageUri != null) {
                int imageSizeInBytes = getImageSize(uncompressedImage);
                if (imageSizeInBytes > 10 * 1024 * 1024) { // 10 MB limit
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ImageActivity.this, "Please Upload Smaller Image Size", Toast.LENGTH_SHORT).show();
                }
                else {
                    layout.setEnabled(false);
                    uploadUncompressedCroppedImageToFirebase(uncompressedImage);
                }
            } else {
                Toast.makeText(ImageActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
            }
        });

        shapeableImageView.setOnClickListener(v -> {
           /* if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openImagePickerWithCrop();
            } else {*/
                // Request storage permission
            //    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
            showPermissionDialog();

        });

    }
    private void showPermissionDialog() {

        if (ContextCompat.checkSelfPermission(this,READ_STORAGE_PERMISSION)  == PackageManager.PERMISSION_GRANTED
        ){
            openImagePickerWithCrop();

            Toast.makeText(this, "Permission accepted", Toast.LENGTH_SHORT).show();

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
  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open the image picker
                openImagePickerWithCrop();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
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
                        Toast.makeText(ImageActivity.this, "Failed to upload uncropped image", Toast.LENGTH_SHORT).show();
                    });
        }
    }
    private void saveUncompressedImageUrlToDatabase(String imageUrl) {
        HashMap<String, Object> imageMap = new HashMap<>();
        imageMap.put("image01", imageUrl);
        imageMap.put("image02", imageUrl);
        imageMap.put("image03", imageUrl);

        String userId = FirebaseAuth.getInstance().getUid();
        imageMap.put("profileId", userId);

        databaseReference.child(userId).updateChildren(imageMap)
                .addOnSuccessListener(aVoid -> {
                    progressBar.setVisibility(View.INVISIBLE);
//                    Toast.makeText(ImageActivity.this, "Uncropped image uploaded successfully", Toast.LENGTH_SHORT).show();
                    uncompressedImageUploaded = true;
                    uploadCompressedImageToFirebase();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    layout.setEnabled(true);
//                    Toast.makeText(ImageActivity.this, "Failed to upload image to database", Toast.LENGTH_SHORT).show();
                    Log.e("UploadError", e.getMessage());
                });
    }



    private void uploadCompressedImageToFirebase() {
        if (imageUri != null) {
            progressBar.setVisibility(View.VISIBLE);

            Bitmap compressedBitmap = compressImage(imageUri);

            // Convert compressed Bitmap to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // Generate a unique filename for the compressed image
            String filename = "compressed_image_" + System.currentTimeMillis() + ".jpg";

            // Upload the compressed image to Firebase Storage
            reference.child(filename)
                    .putBytes(byteArray)
                    .addOnSuccessListener(taskSnapshot -> {
                        reference.child(filename).getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            // Save the compressed image URL to Firebase Database
                            saveCompressedImageUrlToDatabase(imageUrl);
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ImageActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private Bitmap compressImage(Uri imageUri) {
        // Load the original image
        Bitmap originalBitmap = null;
        try {
            originalBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Log the size of the original image
        int originalSizeInBytes = getBitmapSize(originalBitmap);
        Log.e("OriginalImageSize", "Size of original image: " + originalSizeInBytes + " bytes");

        // Compress the image
        int croppedWidth = shapeableImageView.getWidth(); // Assuming shapeableImageView is the View displaying the cropped image
        int croppedHeight = shapeableImageView.getHeight();
        int compressedWidth = croppedWidth / 5; // Set the desired width for the compressed image
        int compressedHeight = croppedHeight / 5;Bitmap compressedBitmap = decodeSampledBitmap(originalBitmap, compressedWidth, compressedHeight);
        int sizeInBytes = getBitmapSize(compressedBitmap);
        Log.e("CompressedImageSize", "Size of compressed image1: " + sizeInBytes + " bytes");
        return compressedBitmap;
    }

    private void saveCompressedImageUrlToDatabase(String imageUrl) {
        HashMap<String, Object> imageMap = new HashMap<>();
        imageMap.put("imageUrl1", imageUrl);
        imageMap.put("status","Registered");
        imageMap.put("active","enabled");

        String userId = FirebaseAuth.getInstance().getUid();
        imageMap.put("profileId", userId);

        databaseReference.child(userId).updateChildren(imageMap)
                .addOnSuccessListener(aVoid -> {
                    Intent intent = getIntent();
                    String gen = intent.getStringExtra("Gender");
                    String acc = intent.getStringExtra("Account");
                    FirebaseMessaging.getInstance().subscribeToTopic("IncompleteProfile")
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.d("tag","User is subscribed");
                                }
                                else {

                                }
                            });
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("IncompleteRegistration")
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.d("tag","User is subscribed");
                                }
                                else {

                                }
                            });
                    if(gen.equals("1"))
                    {
                        FirebaseMessaging.getInstance().subscribeToTopic("MaleUsers")
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Log.d("tag","User is subscribed");
                                    }
                                    else {

                                    }
                                });
                    }
                    else if(gen.equals("2"))
                    {
                        FirebaseMessaging.getInstance().subscribeToTopic("FemaleUsers")
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Log.d("tag","User is subscribed");
                                    }
                                    else {

                                    }
                                });
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ImageActivity.this, "Profile Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), welcome_instructions.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    layout.setEnabled(true);
                    Toast.makeText(ImageActivity.this, "Failed to upload image, Try again", Toast.LENGTH_SHORT).show();
                    Log.e("UploadError", e.getMessage());
                });
    }

    // Decode and sample down a bitmap from resources to the requested width and height.
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
        // Calculate size in bytes of the bitmap
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
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            return inputStream != null ? inputStream.available() : 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
