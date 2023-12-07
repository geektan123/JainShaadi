package com.example.jainshaadi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progressBar);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
            DatabaseReference currentProfileRef = databaseRef.child(FirebaseAuth.getInstance().getUid());

            currentProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        status = dataSnapshot.child("status").getValue(String.class);
                        if (status != null) {
                            if (status.equals("Registered") || status.equals("Completed")) {
                                Intent intent = new Intent(getApplicationContext(), Homeactivity.class);
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
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("a","here3");
                    Toast.makeText(Login.this, "Try again !!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            textView = findViewById(R.id.google_sign_in_button);
            GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            client = GoogleSignIn.getClient(this, options);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressBar.setVisibility(View.VISIBLE);
                    Intent i = client.getSignInIntent();
                    startActivityForResult(i, 1234);
                }
            });
        }
    }

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
                                progressBar.setVisibility(View.INVISIBLE);

                                if (task.isSuccessful()) {
                                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                    Log.e("a","here1");
                                    if (currentUser != null) {
                                        Log.e("a","here2");
                                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
                                        DatabaseReference currentProfileRef = databaseRef.child(FirebaseAuth.getInstance().getUid());
                                        currentProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    status = dataSnapshot.child("status").getValue(String.class);
                                                    if (status != null) {
                                                        if (status.equals("Registered") || status.equals("Completed")) {
                                                            Intent intent = new Intent(getApplicationContext(), Homeactivity.class);
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
                                                }
                                                else {
                                                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.e("a","here3");
                                                Toast.makeText(Login.this, "Try again !!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("a","here4");
                                    Toast.makeText(Login.this,"Try Again !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (ApiException e) {
                Log.e("a","here5");
                e.printStackTrace();
            }
        }
    }
}
