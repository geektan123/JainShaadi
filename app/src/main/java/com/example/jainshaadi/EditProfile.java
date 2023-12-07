package com.example.jainshaadi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.example.jainshaadi.ImagePagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.view.ViewGroup;

public class EditProfile extends AppCompatActivity {
    //    private String currentUserId = "FZO96RFnQJagYyxRaVZ3ygir5Vn1";
    private TextView Name , Bio , DOB , Community , Location , Work , Income , Education , Family , Hobbies;
    private ImageView NameEdit , BioEdit , DOBEdit , CommunityEdit  , LocationEdit  , WorkEdit  , IncomeEdit  , EducationEdit  , FamilyEdit  , HobbiesEdit ;
    private ImageView Image01 , Image02 , Image03 , Image01Edit , Image02Edit , Image03Edit;
    private List<String> imageList;
    RelativeLayout Load_profile;
    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
    ProgressBar progress;

    // Get the reference to the current profile
    DatabaseReference currentProfileRef = databaseRef.child(FirebaseAuth.getInstance().getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();
        Name = findViewById(R.id.Name);
        Bio = findViewById(R.id.Bio);
        DOB = findViewById(R.id.DOB);
        Community = findViewById(R.id.Community);
        Location = findViewById(R.id.Location);
        Work = findViewById(R.id.Work);
        Income = findViewById(R.id.Family02);
        Education = findViewById(R.id.Education);
        Family = findViewById(R.id.Family);
        Hobbies = findViewById(R.id.Hobbies);
        NameEdit = findViewById(R.id.NameEdit);
        BioEdit = findViewById(R.id.BioEdit);
        DOBEdit = findViewById(R.id.DOBEdit);
        CommunityEdit = findViewById(R.id.CommunityEdit);
        LocationEdit = findViewById(R.id.LocationEdit);
        WorkEdit = findViewById(R.id.WorkEdit);
        IncomeEdit = findViewById(R.id.FamilyEdit02);
        EducationEdit = findViewById(R.id.EducationEdit);
        FamilyEdit = findViewById(R.id.FamilyEdit);
        HobbiesEdit = findViewById(R.id.HobbiesEdit);
        Image01 = findViewById(R.id.Image01);
        Image02 = findViewById(R.id.Image02);
        Image03 = findViewById(R.id.Image03);
        Image01Edit = findViewById(R.id.Image01Edit);
        Image02Edit = findViewById(R.id.Image02Edit);
        Image03Edit = findViewById(R.id.Image03Edit);



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

                    // Load and display images using Glide
                    if (imageRef1 != null && !imageRef1.isEmpty()) {
                        Glide.with(EditProfile.this).load(imageRef1).into(Image01);
                    }

                    if (imageRef2 != null && !imageRef2.isEmpty()) {
                        Glide.with(EditProfile.this).load(imageRef2).into(Image02);
                    }

                    if (imageRef3 != null && !imageRef3.isEmpty()) {
                        Glide.with(EditProfile.this).load(imageRef3).into(Image03);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });
        NameEdit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                NameEdit dialogFragment = new NameEdit();
                dialogFragment.show(getSupportFragmentManager(), "NameEdit");

            }
        });
        BioEdit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                BioEdit dialogFragment = new BioEdit();
                dialogFragment.show(getSupportFragmentManager(), "BioEdit");

            }
        });
        DOBEdit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                DOBEdit dialogFragment = new DOBEdit();
                dialogFragment.show(getSupportFragmentManager(), "DOBEdit");

            }
        });
        CommunityEdit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                CommunityEdit dialogFragment = new CommunityEdit();
                dialogFragment.show(getSupportFragmentManager(), "CommunityEdit");

            }
        });

        LocationEdit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                LocationEdit dialogFragment = new LocationEdit();
                dialogFragment.show(getSupportFragmentManager(), "LocationEdit");

            }
        });

        WorkEdit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                WorkEdit dialogFragment = new WorkEdit();
                dialogFragment.show(getSupportFragmentManager(), "WorkEdit");

            }
        });

        EducationEdit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                EducationEdit dialogFragment = new EducationEdit();
                dialogFragment.show(getSupportFragmentManager(), "EducationEdit");

            }
        });

        FamilyEdit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                FamilyEdit01 dialogFragment = new FamilyEdit01();
                dialogFragment.show(getSupportFragmentManager(), "FamilyEdit01");

            }
        });

        IncomeEdit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                FamilyEdit02 dialogFragment = new FamilyEdit02();
                dialogFragment.show(getSupportFragmentManager(), "FamilyEdit02");

            }
        });

        HobbiesEdit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                HobbiesEdit dialogFragment = new HobbiesEdit();
                dialogFragment.show(getSupportFragmentManager(), "HobbiesEdit");

            }
        });

        Image01Edit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                ImageEdit01 dialogFragment = new ImageEdit01();
                dialogFragment.show(getSupportFragmentManager(), "ImageEdit01");

            }
        });
        Image02Edit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                ImageEdit02 dialogFragment = new ImageEdit02();
                dialogFragment.show(getSupportFragmentManager(), "ImageEdit02");

            }
        });
        Image03Edit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                ImageEdit02 dialogFragment = new ImageEdit02();
                dialogFragment.show(getSupportFragmentManager(), "ImageEdit02");

            }
        });

        // Fetch and display profile data
        fetchAndDisplayProfileData();
    }

    public void onBackPressed() {
        // Do nothing or show a message, preventing the user from going back
        Intent i = new Intent(getApplicationContext(), MyProfile.class);
        // Add this line to attach the bundle
        startActivity(i);
    }



    private void fetchAndDisplayProfileData() {
        // Create a reference to the Firebase database


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
                    String Degree = dataSnapshot.child("Degree").getValue(String.class);
                    String College = dataSnapshot.child("College").getValue(String.class);
                    String FatherName = dataSnapshot.child("FatherName").getValue(String.class);
                    String FatherOccupation = dataSnapshot.child("FatherOccupation").getValue(String.class);
                    String MotherName = dataSnapshot.child("MotherName").getValue(String.class);
                    String MotherOccupation = dataSnapshot.child("MotherOccupation").getValue(String.class);
                    String  FamilyMembers= dataSnapshot.child("FamilyMembers").getValue(String.class);
                    String Familytype = dataSnapshot.child("Familytype").getValue(String.class);
                    String ParentState = dataSnapshot.child("ParentState").getValue(String.class);
                    String ParentCity = dataSnapshot.child("ParentCity").getValue(String.class);
                    String Year = dataSnapshot.child("Year").getValue(String.class);
                    String status = dataSnapshot.child("status").getValue(String.class);
                    String description = dataSnapshot.child("Description").getValue(String.class);
                    String phone = dataSnapshot.child("Year").getValue(String.class);
                    String Dob =  dataSnapshot.child("DateOfBirth").getValue(String.class);
                    String Company =  dataSnapshot.child("Company").getValue(String.class);
                    String interest01 = dataSnapshot.child("Interest1").getValue(String.class);
                    String interest02 = dataSnapshot.child("Interest2").getValue(String.class);
                    String interest03 = dataSnapshot.child("Interest3").getValue(String.class);
                    String interest04 = dataSnapshot.child("Interest4").getValue(String.class);
                    String interest05 = dataSnapshot.child("Interest5").getValue(String.class);
                    String interest06 = dataSnapshot.child("Interest6").getValue(String.class);
                    Log.e("e","1 = "+name);
                    Log.e("e","2 = "+description);
                    Log.e("e","3 = "+phone);
                    Log.e("e","4 = "+Dob);
                    Log.e("e","5 = "+cast);
                    Log.e("e","6 = "+state+" - "+city);
                    Log.e("e","7 = "+profession+ " at "+Company);
                    Log.e("e","8 = "+income);
                    Log.e("e","9 = "+Degree + ", "+College+" ("+Year+")");
                    Log.e("e","10 = "+Familytype+" ("+FamilyMembers+")"+"\n"+FatherName+" ("+FatherOccupation+")"+"\n" +MotherName+" ("+MotherOccupation+")");
                    Log.e("e","11 = "+interest01+", "+interest02+", "+interest03+", "+interest04+", "+interest05+", "+interest06);
                    Name.setText(name);
                    Bio.setText(description);
                    DOB.setText(Dob);
                    Community.setText(cast);
                    Location.setText(state+" - "+city);
                    Work.setText(profession+ " at "+Company);
                    Income.setText(income);
                    Education.setText(Degree + ", "+College+" ("+Year+")");
                    Family.setText(Familytype+" ("+FamilyMembers+")"+"&#xA;"+FatherName+" ("+FatherOccupation+")"+"&#xA;" +MotherName+" ("+MotherOccupation+")");
                    Hobbies.setText(interest01+", "+interest02+", "+interest03+", "+interest04+", "+interest05+", "+interest06);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }
}