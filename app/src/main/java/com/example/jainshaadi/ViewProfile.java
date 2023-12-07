package com.example.jainshaadi;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.view.ViewGroup;

public class ViewProfile extends AppCompatActivity {
    private String currentUserId;
    private String profileId;
    private TextView profileName, profileProfession, tag, profileAge, profileCast, profileIncome, profileCity;
    private TextView profileEducation, profileFather, profileMother, profileMembers, profileFamilyType, profileResidence;
    private TextView profileInterest01, profileInterest02, profileInterest03, profileInterest04, profileInterest05, profileInterest06;
    private ViewPager2 viewPager;
    private ImagePagerAdapter adapter;
    private List<String> imageList;
    private LinearLayout dotIndicators;
    RelativeLayout Load_profile;
    ProgressBar progress;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");

    // Get the reference to the current profile

        Button button;
String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);
        getSupportActionBar().hide();
        Load_profile = findViewById(R.id.load_profile);
        button=findViewById(R.id.overlay_button);
        progress = findViewById(R.id.progressBar);
        Load_profile.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        currentUserId = intent.getStringExtra("curentUserId");
        profileId = intent.getStringExtra("profileId");
        DatabaseReference currentProfileRef = databaseRef.child(profileId);
        name=intent.getStringExtra("Name");
        viewPager = findViewById(R.id.viewPager);


        imageList = new ArrayList<>();
//        String placeholderUrl = "https://firebasestorage.googleapis.com/v0/b/jainshaadi-38ab5.appspot.com/o/1701002224460.jpg?alt=media&token=522fbe59-b67c-424b-9e30-fae067e3a1e3";
//        imageList.add(placeholderUrl);
//        imageList.add(placeholderUrl);
//        imageList.add(placeholderUrl);
        Context context = this;
        int color = ContextCompat.getColor(this, R.color.button_enabled);
        currentProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Add Firebase Storage references to your images
                    // Make sure to replace "images/your_image1.jpg", "images/your_image2.jpg", etc.,
                    // with the actual paths to your images in Firebase Storage
                    String imageRef1 = dataSnapshot.child("image01").getValue(String.class);
                    String imageRef2 = dataSnapshot.child("image02").getValue(String.class);
                    String imageRef3 = dataSnapshot.child("image03").getValue(String.class);
                    imageList.add(imageRef1);
                    imageList.add(imageRef2);
                    imageList.add(imageRef3);


                    viewPager = findViewById(R.id.viewPager);

                    adapter = new ImagePagerAdapter(context, imageList);
                    viewPager.setAdapter(adapter);

                    dotIndicators = findViewById(R.id.dotIndicators);

                    setupDotIndicators(imageList.size());

                    viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);

                            // Update the dot indicators
                            for (int i = 0; i < dotIndicators.getChildCount(); i++) {
                                ImageView dot = (ImageView) dotIndicators.getChildAt(i);
                                dot.setImageResource(i == position ? R.drawable.dot_selected : R.drawable.dot_unselected);
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });



        // Initialize TextViews
        profileName = findViewById(R.id.profile_name);
        profileProfession = findViewById(R.id.profile_profession);
        tag = findViewById(R.id.tag);
        profileAge = findViewById(R.id.profile_age);
        profileCast = findViewById(R.id.profile_cast);
        profileIncome = findViewById(R.id.profile_income);
        profileCity = findViewById(R.id.profile_city);
        profileEducation = findViewById(R.id.profile_education);
        profileFather = findViewById(R.id.profile_father);
        profileMother = findViewById(R.id.profile_mother);
        profileMembers = findViewById(R.id.profile_members);
        profileFamilyType = findViewById(R.id.profile_family_type);
        profileResidence = findViewById(R.id.profile_residence);
        profileInterest01 = findViewById(R.id.profile_interest01);
        profileInterest02 = findViewById(R.id.profile_interest02);
        profileInterest03 = findViewById(R.id.profile_interest03);
        profileInterest04 = findViewById(R.id.profile_interest04);
        profileInterest05 = findViewById(R.id.profile_interest05);
        profileInterest06 = findViewById(R.id.profile_interest06);
        // Fetch and display profile data
        fetchAndDisplayProfileData();

    }

    private void setupDotIndicators(int count) {
        // Initialize your dot indicator layout
        dotIndicators.removeAllViews();

        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageResource(R.drawable.dot_unselected); // Set unselected dot image
            dot.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            dotIndicators.addView(dot);
        }

        // Set the first dot as selected (you can change this based on your initial position)
        if (dotIndicators.getChildCount() > 0) {
            ((ImageView) dotIndicators.getChildAt(0)).setImageResource(R.drawable.dot_selected);
        }
    }

    private void fetchAndDisplayProfileData() {
        // Create a reference to the Firebase database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");

        // Get the reference to the current profile
        DatabaseReference currentProfileRef = databaseRef.child(profileId);

        currentProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve profile data
                    String name = dataSnapshot.child("Name").getValue(String.class);
                    String profession = dataSnapshot.child("Role").getValue(String.class);
                    String tagValue = dataSnapshot.child("tag").getValue(String.class);
                    String age = dataSnapshot.child("Age").getValue(String.class);
                    String cast = dataSnapshot.child("Subcategory").getValue(String.class);
                    String income = dataSnapshot.child("IncomeRange").getValue(String.class);
                    String city = dataSnapshot.child("City").getValue(String.class);
                    String state = dataSnapshot.child("State").getValue(String.class);
                    String Height = dataSnapshot.child("Height").getValue(String.class);
                    String status = dataSnapshot.child("status").getValue(String.class);
                    LinearLayout LL10 , LL11 , LL12 , LL13 , LL14 , LL15 , LL16;
                    ImageView I1;
                    LL10 = findViewById(R.id.layer10);
                    LL11 = findViewById(R.id.layer11);
                    LL12 = findViewById(R.id.layer12);
                    LL13 = findViewById(R.id.layer13);
                    LL14 = findViewById(R.id.layer14);
                    LL15 = findViewById(R.id.layer15);
                    LL16 = findViewById(R.id.layer16);
                    I1 = findViewById(R.id.verified);
                    Load_profile.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    progress.setVisibility(View.INVISIBLE);
                    if(!(status.equals("Completed")))
                    {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT, 0);
                        LL10.setVisibility(View.GONE);
                        LL11.setVisibility(View.GONE);
                        LL12.setVisibility(View.GONE);
                        LL13.setVisibility(View.GONE);
                        LL14.setVisibility(View.GONE);
                        LL15.setVisibility(View.GONE);
                        LL16.setVisibility(View.GONE);
                        I1.setVisibility(View.GONE);

                    }

                    // Set the data in TextViews
                    profileName.setText(name);
                    profileProfession.setText(profession);
                    tag.setText(tagValue);
                    profileAge.setText(age + "," + Height);
                    profileCast.setText(cast);
                    profileIncome.setText(income);
                    profileCity.setText(city + "," + state);

                    // Retrieve and set other profile data in TextViews simil
                        // Data exists, set the TextViews

                        String Degree = dataSnapshot.child("Degree").getValue(String.class);
                        String College = dataSnapshot.child("College").getValue(String.class);
                       String FatherName = dataSnapshot.child("FatherName").getValue(String.class);
                       String FatherOccupation = dataSnapshot.child("FatherOccupation").getValue(String.class);
                        String MotherName = dataSnapshot.child("MotherName").getValue(String.class);
                        String MotherOccupation = dataSnapshot.child("MotherOccupation").getValue(String.class);
                      String  FamilyMembers = dataSnapshot.child("FamilyMembers").getValue(String.class);
                        String Familytype = dataSnapshot.child("Familytype").getValue(String.class);
                        String ParentState = dataSnapshot.child("ParentState").getValue(String.class);
                        String ParentCity = dataSnapshot.child("ParentCity").getValue(String.class);
                       String Year = dataSnapshot.child("Year").getValue(String.class);

                        profileName.setText(name);
                        profileProfession.setText(profession);
                        tag.setText(tagValue);
                        profileAge.setText(age + "," + Height);
                        profileCast.setText(cast);
                        profileIncome.setText(income);
                        profileCity.setText(city + "," + state);
                        profileEducation.setText(Degree + "\n" + College + ", " + Year);
                        profileFather.setText(FatherName + "(" + FatherOccupation + ")");
                        profileMother.setText(MotherName + "(" + MotherOccupation + ")");
                        profileMembers.setText(FamilyMembers);
                        profileFamilyType.setText(Familytype);
                        profileResidence.setText(ParentCity + "," + ParentState);
                        profileInterest01.setText(dataSnapshot.child("Interest1").getValue(String.class));
                        profileInterest02.setText(dataSnapshot.child("Interest2").getValue(String.class));
                        profileInterest03.setText(dataSnapshot.child("Interest3").getValue(String.class));
                        profileInterest04.setText(dataSnapshot.child("Interest4").getValue(String.class));
                        profileInterest05.setText(dataSnapshot.child("Interest5").getValue(String.class));
                        profileInterest06.setText(dataSnapshot.child("Interest6").getValue(String.class));
                    }
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), specificchat.class);
                intent.putExtra("profileId", profileId);
                intent.putExtra("Name", name);
                startActivity(intent);
            }
        });
    }
}