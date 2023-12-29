package com.jainmaitri.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class Launcher extends AppCompatActivity {
    // Set the dimensions of the sign-in button.
//    LinearLayout textView;
    GoogleSignInClient client;
    //    ProgressBar progressBar;
    String status = "";
    public static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        getSupportActionBar().hide();
        setContentView(R.layout.launcher_splash);
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
//            if (task.isSuccessful() && task.getResult() != null) {
//                String token = task.getResult();
//                Log.e("FCM Token", "token = "+token);
//            } else {
//                Log.e("FCM Token", "Error fetching FCM token");
//            }
//        });
        FirebaseMessaging.getInstance().subscribeToTopic("AllUsers")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("tag","User is subscribed");
                    }
                    else {

                    }
                });




        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // User is already logged in, redirect to HomeActivity
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
            DatabaseReference currentProfileRef = databaseRef.child(FirebaseAuth.getInstance().getUid());

            currentProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        status = dataSnapshot.child("status").getValue(String.class);
                        String gender = dataSnapshot.child("Gender").getValue(String.class);
                        if(gender != null)
                        {
                            if(gender.equals("Male"))
                            {
                                FirebaseMessaging.getInstance().subscribeToTopic("MaleUsers")
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                Log.d("tag","User is subscribed");
                                            }
                                            else {

                                            }
                                        });
                            } else if (gender.equals("Female")) {
                                FirebaseMessaging.getInstance().subscribeToTopic("FemaleUsers")
                                        .addOnCompleteListener(task -> {
                                            if (task.isSuccessful()) {
                                                Log.d("tag","User is subscribed");
                                            }
                                            else {

                                            }
                                        });
                            }
                        }
                        if (status != null) {
                            if (status.equals("Registered") || status.equals("Completed")) {
                                if(status.equals(("Registered")))
                                {
                                    FirebaseMessaging.getInstance().subscribeToTopic("IncompleteProfile")
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    Log.d("tag","User is subscribed");
                                                }
                                                else {

                                                }
                                            });
                                }
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(getApplicationContext(), Profile.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Intent intent = new Intent(getApplicationContext(), Profile.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle any errors
                }
            });

        } else {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
    }



}




    /* @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);

         if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
             if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

             } else {
                 // Permission denied, show a message or handle accordingly
                 Toast.makeText(getApplicationContext(), "Notification  permission denied", Toast.LENGTH_SHORT).show();
             }
         }
     }
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    Toast.makeText(getApplicationContext(), "Notification permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Notification permission denied", Toast.LENGTH_SHORT).show();
                }
            }
    );
}
*/

