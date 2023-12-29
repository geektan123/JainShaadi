package com.jainmaitri.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Search extends AppCompatActivity {
    private String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        currentUserId = intent.getStringExtra("curentUserId");
        Log.e("e","curent1 = "+currentUserId);
        LinearLayout search1 = findViewById(R.id.digamber);
        LinearLayout StartNow = findViewById(R.id.start_now);
        StartNow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                Intent intent = new Intent(Search.this, MyProfile.class);
                startActivity(intent);
            }
        });
        search1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                Intent intent = new Intent(Search.this, SearchFilter.class);
                intent.putExtra("currentUserId", currentUserId);
                intent.putExtra("filterType", "Digambar");
                startActivity(intent);
            }
        });
        LinearLayout search2 = findViewById(R.id.shwetamber);
        search2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                Intent intent = new Intent(Search.this,SearchFilter.class);
                intent.putExtra("currentUserId", currentUserId);
                intent.putExtra("filterType", "Shwetambar");
                startActivity(intent);
            }
        });
        LinearLayout search3 = findViewById(R.id.location);
        search3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                Intent intent = new Intent(Search.this, SearchFilter.class);
                intent.putExtra("currentUserId", currentUserId);
                intent.putExtra("filterType", "Location");
                startActivity(intent);
            }
        });
        LinearLayout search4 = findViewById(R.id.interest);
        search4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                Intent intent = new Intent(Search.this, SearchFilter.class);
                intent.putExtra("currentUserId", currentUserId);
                intent.putExtra("filterType", "Age");
                startActivity(intent);
            }
        });

    }
}