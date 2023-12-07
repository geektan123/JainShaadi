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
import android.widget.TextView;
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

public class ImageActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_image);
        getSupportActionBar().hide();

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
                    uploadUncompressedCroppedImageToFirebase(uncompressedImage);

                }
            } else {
                Toast.makeText(ImageActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
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
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ImageActivity.this, "Profile Image uploaded successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), welcome_instructions.class);
                        startActivity(i);
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.INVISIBLE);
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
