package com.jainmaitri.app;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    LinearLayout textView;
    GoogleSignInClient client;
    ProgressBar progressBar;
    String status = "";
 TextView login_privacy_policy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);
        login_privacy_policy=findViewById(R.id.login_privacy_policy_part2);
        login_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Define the URL for the privacy policy
                String privacyPolicyUrl = "https://jainmaitri.com/TermsAndConditions";

                // Create an Intent to open a web page
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(privacyPolicyUrl));

                // Start the activity with the intent
                startActivity(intent);
            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (currentUser != null) {
//            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
//            DatabaseReference currentProfileRef = databaseRef.child(FirebaseAuth.getInstance().getUid());
//
//            currentProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        status = dataSnapshot.child("status").getValue(String.class);
//                        if (status != null) {
//                            if (status.equals("Registered") || status.equals("Completed")) {
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else {
//                                Intent intent = new Intent(getApplicationContext(), Profile.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                        } else {
//                            Intent intent = new Intent(getApplicationContext(), Profile.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//                    else {
//                        Intent intent = new Intent(getApplicationContext(), Profile.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    Log.e("a","here3");
//                    Toast.makeText(Login.this, "Try again !!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
            textView = findViewById(R.id.google_sign_in_button);
            textView.requestFocus();
            ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                    textView,
                    PropertyValuesHolder.ofFloat("scaleX", 0.9f),
                    PropertyValuesHolder.ofFloat("scaleY", 0.9f)
            );
            scaleDown.setDuration(300);
            scaleDown.setDuration(300);

            GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            client = GoogleSignIn.getClient(this, options);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    textView.setEnabled(false);
                    Intent i = client.getSignInIntent();
                    startActivityForResult(i, 1234);
                }
            });
            textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        scaleDown.start();
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        scaleDown.reverse();
                    }
                    return false;
                }
            });
        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                    Log.e("a", "here1");
                                    if (currentUser != null) {
                                        Log.e("a", "here2");
                                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
                                        DatabaseReference currentProfileRef = databaseRef.child(FirebaseAuth.getInstance().getUid());
                                        currentProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                progressBar.setVisibility(View.INVISIBLE);
                                                textView.setEnabled(true);
                                                if (dataSnapshot.exists()) {
                                                    status = dataSnapshot.child("status").getValue(String.class);
                                                    if (status != null) {
                                                        if (status.equals("Registered") || status.equals("Completed")) {
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
                                                progressBar.setVisibility(View.INVISIBLE);
                                                textView.setEnabled(true);
                                                Log.e("a", "here3");
                                                Toast.makeText(Login.this, "Try again !!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    Toast.makeText(Login.this, "Logging You In.", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    textView.setEnabled(true);
                                    Log.e("a", "here4");
                                    Toast.makeText(Login.this, "Try Again !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (ApiException e) {
                textView.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                Log.e("YourTag", "Google sign-in failed. Error code: " + e.getStatusCode(), e);
//                Log.e("a",e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public void onStart()
    {
        super.onStart();
        textView.setEnabled(true);
    }
}
