package com.example.jainshaadi;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.view.ViewGroup;

public class ViewProfile extends AppCompatActivity {
    private String currentUserId;
    private String profileId;
    private TextView profileName, profileProfession, tag, profileAge, profileCast, profileIncome, profileCity;
    private TextView profileEducation, profileFather, profileMother, profileMembers, profileFamilyType, profileResidence;
    private TextView profileInterest01, profileInterest02, profileInterest03, profileInterest04, profileInterest05, profileInterest06;
    private ViewPager2 viewPager;
    private ImagePagerAdapter adapter;
    private List<Integer> imageList;
    private LinearLayout dotIndicators;
        Button button;
String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        currentUserId = intent.getStringExtra("curentUserId");
        profileId = intent.getStringExtra("profileId");
        name=intent.getStringExtra("Name");
        viewPager = findViewById(R.id.viewPager);
        button=findViewById(R.id.overlay_button);

        imageList = new ArrayList<>();
        imageList.add(R.drawable.profile_picture); // Replace with your image resources
        imageList.add(R.drawable.profile_picture);
        imageList.add(R.drawable.profile_picture);

        // Make sure you provide a valid context
        Context context = this; // Or, replace 'this' with the appropriate context

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