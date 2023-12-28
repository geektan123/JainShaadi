package com.example.jainshaadi;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import android.util.Log;

public class Profile extends AppCompatActivity {

    private LinearLayout selectedLinearLayout = null;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    private boolean gender = true; // Initialize with a default value (true for Male, false for Female)
     String POST_NOTIFICATIONS_PERMISSION;
    private int REQUEST_CODE = 11;


    TextView myself;
    TextView myson;
    TextView mybro;
    TextView mydaughter;
    TextView mysister;
    TextView myrelative;
    TextView male;
    TextView female;
    TextView next;
    LinearLayout layout;
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    LinearLayout layout4;
    LinearLayout layout5;
    LinearLayout layoutgayab;
    LinearLayout layoutmale;
    LinearLayout layoutfemale;
    LinearLayout linearnext;
    String isMale = "None"; // Initialize the gender flag
    boolean isNextLayoutChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        HashMap<String, Object> userData = new HashMap<>();
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        this.setTitle("Create Profile For");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            POST_NOTIFICATIONS_PERMISSION = Manifest.permission.POST_NOTIFICATIONS;
        }else {
            POST_NOTIFICATIONS_PERMISSION = Manifest.permission.ACCESS_NOTIFICATION_POLICY;

        }
        showPermissionDialog();

        myself = (TextView) findViewById(R.id.myself);
        myson = findViewById(R.id.Son);
        mybro = findViewById(R.id.Brother);
        mydaughter = findViewById(R.id.Daughter);
        mysister = findViewById(R.id.Sister);
        myrelative = findViewById(R.id.Relative);
        male = findViewById(R.id.male);
        female = findViewById(R.id.Female);
        layout = findViewById(R.id.layout);
        layoutgayab = findViewById(R.id.Layoutgayab);
        layout1 = findViewById(R.id.Layout1);
        layout2 = findViewById(R.id.Layout2);
        layout3 = findViewById(R.id.Layout3);
        layout4 = findViewById(R.id.Layout4);
        layout5 = findViewById(R.id.Layout5);
        layoutmale = findViewById(R.id.layoutmale);
        layoutfemale = findViewById(R.id.layoutfemale);
        next = findViewById(R.id.Next);
        linearnext = findViewById(R.id.Layoutnext);
        layoutgayab.setVisibility(View.INVISIBLE);
        Bundle bundle = new Bundle();
        FirebaseMessaging.getInstance().subscribeToTopic("AllUsers")
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("tag","User is subscribed");
                }
                else {

                }
            });
        FirebaseMessaging.getInstance().subscribeToTopic("IncompleteRegistration")
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("tag","User is subscribed");
                }
                else {

                }
            });


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout == selectedLinearLayout) {
                    layout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    linearnext.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
                    layoutgayab.setVisibility(View.INVISIBLE);
                    layoutfemale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    female.setTextColor(Color.parseColor("#756568"));
                    layoutmale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    male.setTextColor(Color.parseColor("#756568"));
                    isMale = "None";
                    myself.setTextColor(Color.parseColor("#756568"));
                    selectedLinearLayout = null;
                    userData.clear();
                } else {
                    if (selectedLinearLayout != null) {
                        selectedLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                        TextView selectedTextView = (TextView) selectedLinearLayout.getChildAt(0);
                        selectedTextView.setTextColor(Color.parseColor("#756568"));
                    }
                    layoutgayab.setVisibility(View.VISIBLE);
                    layout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    myself.setTextColor(Color.parseColor("#FFFFFF"));
                    selectedLinearLayout = layout;
                    String selectedText = myself.getText().toString();
                    userData.put("Account Managed for", selectedText);
                    userData.put("Account_Managed_for", selectedText);

                    layoutmale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!(isMale.equals("Male"))) {
                                linearnext.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                next.setTextColor(Color.parseColor("#FFFFFF"));
                                layoutmale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                male.setTextColor(Color.parseColor("#FFFFFF"));
                                layoutfemale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                                female.setTextColor(Color.parseColor("#756568"));
                                String selectedText = myself.getText().toString();
                                String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID
                                 isNextLayoutChanged = true;

                                userData.put("Account Managed for", selectedText);
                                userData.put("Account_Managed_for", selectedText);
                                String gender = "Male";

                                userData.put("Gender", gender);
                                String tag = "Looking for bride";
                                userData.put("status","Authenticated");
                                userData.put("tag", tag);

//                                databaseReference.child(userKey).setValue(userData);

                                isMale = "Male"; // Set gender flag to true
                            }
                        }
                    });

                    layoutfemale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("MyTag", "This is an error log message");
                            if (!(isMale.equals("Female"))) {
                                linearnext.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                next.setTextColor(Color.parseColor("#FFFFFF"));
                                layoutfemale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                female.setTextColor(Color.parseColor("#FFFFFF"));
                                layoutmale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                                male.setTextColor(Color.parseColor("#756568"));
                                String selectedText = myself.getText().toString();

                                String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID

                                userData.put("Account Managed for", selectedText);
                                String gender = "Female";
                                isNextLayoutChanged = true;

                                userData.put("Gender", gender);
                                String tag = "Looking for groom";
                                userData.put("status","Authenticated");
                                userData.put("tag", tag);


//                                databaseReference.child(userKey).setValue(userData);

                                isMale = "Female"; // Set gender flag to false
                            }
                        }
                    });




                }

            }
        });


        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearnext.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                next.setTextColor(Color.parseColor("#FFFFFF"));
                if (layout1 == selectedLinearLayout && !false) {
                    layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    myson.setTextColor(Color.parseColor("#756568"));
                    selectedLinearLayout = null;
                    userData.clear();
                } else {
                    if (selectedLinearLayout != null) {
                        selectedLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                        TextView selectedTextView = (TextView) selectedLinearLayout.getChildAt(0);
                        selectedTextView.setTextColor(Color.parseColor("#756568"));
                    }
                    layoutgayab.setVisibility(View.INVISIBLE);
                    isNextLayoutChanged = true;

                    layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    myson.setTextColor(Color.parseColor("#FFFFFF"));
                    selectedLinearLayout = layout1;

                }
                String selectedText = myson.getText().toString();

                String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID

                userData.put("Account Managed for", selectedText);
                userData.put("Account_Managed_for", selectedText);
                String gender = "Male";

                userData.put("Gender", gender);
                String tag = "Looking for bride";

                userData.put("tag", tag);
                userData.put("status","Authenticated");
//                databaseReference.child(userKey).setValue(userData);
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearnext.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                next.setTextColor(Color.parseColor("#FFFFFF"));
                if (layout2 == selectedLinearLayout && !false) {
                    layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    mybro.setTextColor(Color.parseColor("#756568"));
                    selectedLinearLayout = null;
                    userData.clear();
                } else {
                    if (selectedLinearLayout != null) {
                        selectedLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                        TextView selectedTextView = (TextView) selectedLinearLayout.getChildAt(0);
                        selectedTextView.setTextColor(Color.parseColor("#756568"));
                    }
                    layoutgayab.setVisibility(View.INVISIBLE);

                    layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    mybro.setTextColor(Color.parseColor("#FFFFFF"));
                 isNextLayoutChanged = true;
                    selectedLinearLayout = layout2;
                }
                String selectedText = mybro.getText().toString();
                String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID


                userData.put("Account Managed for", selectedText);
                userData.put("Account_Managed_for", selectedText);

                String gender = "Male";

                userData.put("Gender", gender);
                String tag = "Looking for bride";
                userData.put("status","Authenticated");
                userData.put("tag", tag);
//                databaseReference.child(userKey).setValue(userData);
            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearnext.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                next.setTextColor(Color.parseColor("#FFFFFF"));
                if (layout3 == selectedLinearLayout && !false) {
                    layout3.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    mydaughter.setTextColor(Color.parseColor("#756568"));
                    selectedLinearLayout = null;
                    userData.clear();
                } else {
                    if (selectedLinearLayout != null) {
                        selectedLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                        TextView selectedTextView = (TextView) selectedLinearLayout.getChildAt(0);
                        selectedTextView.setTextColor(Color.parseColor("#756568"));
                    }
                    layoutgayab.setVisibility(View.INVISIBLE);
                    isNextLayoutChanged = true;
                    layout3.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    mydaughter.setTextColor(Color.parseColor("#FFFFFF"));
                    selectedLinearLayout = layout3;
                }

                String selectedText = mydaughter.getText().toString();
                String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID

//                HashMap<String, Object> userData = new HashMap<>();
                userData.put("Account Managed for", selectedText);
                userData.put("Account_Managed_for", selectedText);
                String gender = "Female";

                userData.put("Gender", gender);
                String tag = "Looking for groom";
                userData.put("status","Authenticated");
                userData.put("tag", tag);

//                databaseReference.child(userKey).setValue(userData);
            }
        });

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearnext.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                next.setTextColor(Color.parseColor("#FFFFFF"));
                if (layout4 == selectedLinearLayout && !false) {
                    layout4.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    mysister.setTextColor(Color.parseColor("#756568"));
                    selectedLinearLayout = null;
                    userData.clear();
                } else {
                    if (selectedLinearLayout != null) {
                        selectedLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                        TextView selectedTextView = (TextView) selectedLinearLayout.getChildAt(0);
                        selectedTextView.setTextColor(Color.parseColor("#756568"));
                    }
                    layoutgayab.setVisibility(View.INVISIBLE);
                  isNextLayoutChanged = true;
                    layout4.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    mysister.setTextColor(Color.parseColor("#FFFFFF"));
                    selectedLinearLayout = layout4;
                }
                String selectedText = mysister.getText().toString();
                // Assuming you have a reference to your Firebase Database
                String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID

                userData.put("Account Managed for", selectedText);
                userData.put("Account_Managed_for", selectedText);
                String gender = "Female";
                userData.put("Gender", gender);
                String tag = "Looking for groom";
                userData.put("status","Authenticated");
                userData.put("tag", tag);

//                databaseReference.child(userKey).setValue(userData);

            }
        });

        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout5 == selectedLinearLayout && !false) {
                    layout5.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    myrelative.setTextColor(Color.parseColor("#756568"));
                    selectedLinearLayout = null;
                    userData.clear();
                    linearnext.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
                    layoutgayab.setVisibility(View.INVISIBLE);
                    layoutfemale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    female.setTextColor(Color.parseColor("#756568"));
                    layoutmale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    male.setTextColor(Color.parseColor("#756568"));
                    isMale = "None";
                } else {
                    if (selectedLinearLayout != null) {
                        selectedLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                        TextView selectedTextView = (TextView) selectedLinearLayout.getChildAt(0);
                        selectedTextView.setTextColor(Color.parseColor("#756568"));
                    }
                    layout5.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    myrelative.setTextColor(Color.parseColor("#FFFFFF"));
                    selectedLinearLayout = layout5;
                    String selectedText = myrelative.getText().toString();
                    userData.put("Account Managed for", selectedText);
                    userData.put("Account_Managed_for", selectedText);
                    layoutgayab.setVisibility(View.VISIBLE);
                    layoutmale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!(isMale.equals("Male"))) {
                                linearnext.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                next.setTextColor(Color.parseColor("#FFFFFF"));
                                layoutmale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                male.setTextColor(Color.parseColor("#FFFFFF"));
                                layoutfemale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                                female.setTextColor(Color.parseColor("#756568"));
                                String selectedText = myrelative.getText().toString();
                                String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID

                                userData.put("Account Managed for", selectedText);
                                userData.put("Account_Managed_for", selectedText);
                                String gender = "Male";
                                isNextLayoutChanged = true;
                                userData.put("Gender", gender);
                                String tag = "Looking for bride";

                                userData.put("tag", tag);
                                userData.put("status","Authenticated");
//                                databaseReference.child(userKey).setValue(userData);

                                isMale = "Male"; // Set gender flag to true
                            }
                        }
                    });

                    layoutfemale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!(isMale.equals("Female"))) {
                                linearnext.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                next.setTextColor(Color.parseColor("#FFFFFF"));
                                layoutfemale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                female.setTextColor(Color.parseColor("#FFFFFF"));
                                layoutmale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                                male.setTextColor(Color.parseColor("#756568"));
                                String selectedText = myrelative.getText().toString();

                                String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID

//                                HashMap<String, Object> userData = new HashMap<>();
                                userData.put("Account Managed for", selectedText);
                                userData.put("Account_Managed_for", selectedText);
                                String gender = "Female";
                                 isNextLayoutChanged = true;
                                String tag = "Looking for groom";

                                userData.put("tag", tag);
                                userData.put("status","Authenticated");
                                userData.put("Gender", gender);
                                userData.put("status","Authenticated");
//                                databaseReference.child(userKey).setValue(userData);

                                isMale = "Female"; // Set gender flag to false
                            }
                        }
                    });


                }
                String selectedText = myrelative.getText().toString();
                bundle.putString("SelectedText", selectedText);
            }
        });
      /*  layoutmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutmale == selectedLinearLayout) {
                    layoutmale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    male.setTextColor(Color.parseColor("#756568"));
                    selectedLinearLayout = null;
                } else {
                    if (selectedLinearLayout != null) {
                        selectedLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                        TextView selectedTextView = (TextView) selectedLinearLayout.getChildAt(0);
                        selectedTextView.setTextColor(Color.parseColor("#756568"));
                    }
                    layoutmale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    male.setTextColor(Color.parseColor("#FFFFFF"));
                    selectedLinearLayout = layoutmale;
                }
                String gender = "Male";
                bundle.putString("Gender", gender);
            }
        });

        layoutfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutfemale == selectedLinearLayout) {
                    layoutfemale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    female.setTextColor(Color.parseColor("#756568"));
                    selectedLinearLayout = null;
                } else {
                    if (selectedLinearLayout != null) {
                        selectedLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                        TextView selectedTextView = (TextView) selectedLinearLayout.getChildAt(0);
                        selectedTextView.setTextColor(Color.parseColor("#756568"));
                    }
                    layoutfemale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    female.setTextColor(Color.parseColor("#FFFFFF"));
                    selectedLinearLayout = layoutfemale;
                }
                String gender = "Female";
                bundle.putString("Gender", gender);

            }
        });*/
        // ... (Your existing code)

        linearnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNextLayoutChanged == true) {

                    if (!(userData.isEmpty())) {
                        String gen = "";
                        String acc = "";
                        if (userData.get("Gender").equals("Male")) {
                            gen = "1";
                        } else if (userData.get("Gender").equals("Female")) {
                            gen = "2";
                        }
                        if (userData.get("Account_Managed_for").equals("Myself")) {
                            acc = "1";
                        } else if (userData.get("Account_Managed_for").equals("My Son")) {
                            acc = "2";
                        } else if (userData.get("Account_Managed_for").equals("My Brother")) {
                            acc = "3";
                        } else if (userData.get("Account_Managed_for").equals("My Daughter")) {
                            acc = "4";
                        } else if (userData.get("Account_Managed_for").equals("My Sister")) {
                            acc = "5";
                        } else if (userData.get("Account_Managed_for").equals("My Relative")) {
                            acc = "6";
                        }

                        String userKey = FirebaseAuth.getInstance().getUid();
                        userData.put("active", "enabled");
                        userData.put("status", "Authenticated");
                        databaseReference.child(userKey).setValue(userData);
                        Intent i = new Intent(getApplicationContext(), Name.class);
                        i.putExtra("Gender", gen);
                        i.putExtra("Account", acc);
                        // Add this line to attach the bundle
                        startActivity(i);
                    } else {
                        Toast.makeText(Profile.this, "Please Complete the values", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Profile.this, "Please Complete the values", Toast.LENGTH_SHORT).show();
                }
            }
        });

// Close onCreate method and the class


    }

    private void showPermissionDialog() {

        if (ContextCompat.checkSelfPermission(this,POST_NOTIFICATIONS_PERMISSION)  == PackageManager.PERMISSION_GRANTED
        ){

            Toast.makeText(this, "Permission accepted", Toast.LENGTH_SHORT).show();

        }else {
            ActivityCompat.requestPermissions(this, new String[]{ POST_NOTIFICATIONS_PERMISSION },REQUEST_CODE);
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                ){

                }
                else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }

        }else {
            showPermissionDialog();
        }

    }
}
