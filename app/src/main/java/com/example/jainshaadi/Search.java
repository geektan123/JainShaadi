package com.example.jainshaadi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Search extends AppCompatActivity {
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        Intent intent = getIntent();
        currentUserId = intent.getStringExtra("curentUserId");
        Log.e("e","curent1 = "+currentUserId);
        LinearLayout search1 = findViewById(R.id.digamber);
        search1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                Intent intent = new Intent(Search.this, com.example.jainshaadi.SearchFilter.class);
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
                intent.putExtra("filterType", "Shwetamber");
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
    }
}