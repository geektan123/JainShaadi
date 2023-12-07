package com.example.jainshaadi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import javax.annotation.Nullable;

public class ProfileOptionsBottomSheetFragment extends BottomSheetDialogFragment {
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
    Boolean comp = false;
    Boolean disable = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_options, container, false);

        DatabaseReference currentProfileRef = databaseRef.child(FirebaseAuth.getInstance().getUid());
        LinearLayout btnEditProfile = view.findViewById(R.id.llEditProfile);
        LinearLayout btnLogout = view.findViewById(R.id.llLogout);
        LinearLayout btnFeedback = view.findViewById(R.id.llFeedback);
        LinearLayout btnDisableProfile = view.findViewById(R.id.llDisableProfile);
        LinearLayout btnTestimonial = view.findViewById(R.id.llTestimonials);
        LinearLayout btnContact = view.findViewById(R.id.llContact);
        TextView disableText = view.findViewById(R.id.disableIdText);

        currentProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve profile data
                    String status = dataSnapshot.child("status").getValue(String.class);
                    String active = dataSnapshot.child("active").getValue(String.class);
                    if ((active.equals("disabled"))) {
                        disable = true;
                        disableText.setText("Enable Discovery");
                    }
                    if ((status.equals("Completed"))) {
                        comp = true;
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });


//
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comp) {
                    Intent i = new Intent(requireContext(),EditProfile.class);
                    // Add this line to attach the bundle
                    startActivity(i);
                    dismiss();
                }
                else
                {
                    Toast.makeText(requireContext(), "Complete Your Profile to Edit", Toast.LENGTH_SHORT).show();
                }
                // Handle Edit Profile click
                // Dismiss the bottom sheet
                // Add your logic to open the Edit Profile screen
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
//                dismiss();
                // Handle Logout click
                 // Dismiss the bottom sheet
                // Add your logic to perform logout
            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Feedback click
                dismiss(); // Dismiss the bottom sheet
                // Add your logic to open the Feedback screen
            }
        });

        btnDisableProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("s","you r 0 " + disable);
                // Handle Review click
                if(disable) {
                    HashMap<String, Object> userData = new HashMap<>();
                    userData.put("active", "enabled");
                    Log.e("s","you r 1");
                    String userKey = FirebaseAuth.getInstance().getUid();
                    currentProfileRef.updateChildren(userData);
                    dismiss();

                }
                else
                {
                    HashMap<String, Object> userData = new HashMap<>();
                    userData.put("active", "disabled");
                    Log.e("s","you r 2");
                    String userKey = FirebaseAuth.getInstance().getUid();
                    currentProfileRef.updateChildren(userData);
                    dismiss();
                }
            }
        });
        btnTestimonial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Review click
                dismiss(); // Dismiss the bottom sheet
                // Add your logic to open the Review screen
            }
        });
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Review click
                dismiss(); // Dismiss the bottom sheet
                // Add your logic to open the Review screen
            }
        });
//
        return view;
    }
    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked Yes, perform logout
                        FirebaseAuth.getInstance().signOut();
                        GoogleSignIn.getClient(requireContext(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                                .signOut();
                        Intent i = new Intent(requireContext(),Login.class);
                        // Add this line to attach the bundle
                        startActivity(i);
                        dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked No, dismiss the dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }


}