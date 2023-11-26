package com.example.jainshaadi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class ImageActivity extends AppCompatActivity {

    ShapeableImageView shapeableImageView;
    Uri imageUri;
    ImageView plus;
    ProgressBar progressBar;

    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    final private StorageReference reference = FirebaseStorage.getInstance().getReference();
    LinearLayout layout;
    LinearLayout Next;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        getSupportActionBar().hide();

        shapeableImageView = findViewById(R.id.shapeable_image_view);
        layout = findViewById(R.id.Next);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null){
                     // Call the uploadImageToFirebase method
                    uploadImageToFirebase();

                } else {
                    Toast.makeText(ImageActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        shapeableImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

                imageUri = data.getData();
                shapeableImageView.setImageURI(imageUri);


            }
        }

        private void uploadImageToFirebase() {
            if (imageUri != null) {
                progressBar.setVisibility(View.VISIBLE);

                // Get the file extension
                ContentResolver contentResolver = getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String fileExtension = mime.getExtensionFromMimeType(contentResolver.getType(imageUri));

                // Create a reference to the Firebase Storage location
                StorageReference fileReference = reference.child(System.currentTimeMillis() + "." + fileExtension);

                // Upload the image
                fileReference.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get the download URL
                                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();

                                        // Create a HashMap to store the image URL
                                        HashMap<String, Object> imageMap = new HashMap<>();
                                        imageMap.put("imageUrl1", imageUrl);


                                        // Get the current user's ID (assuming you have a way to identify users)
                                        String userId =FirebaseAuth.getInstance().getUid();
                                        imageMap.put("profileId", userId);
                                        // Add the image URL to the database
                                        databaseReference.child(userId).updateChildren(imageMap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        progressBar.setVisibility(View.INVISIBLE);

                                                        Toast.makeText(ImageActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(getApplicationContext(), welcome_instructions.class);
                                                        startActivity(i);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressBar.setVisibility(View.INVISIBLE);

                                                        Toast.makeText(ImageActivity.this, "Failed to upload image to database", Toast.LENGTH_SHORT).show();
                                                        Log.e("UploadError", e.getMessage()); // Add this line to log the error

                                                    }
                                                });
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(ImageActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }