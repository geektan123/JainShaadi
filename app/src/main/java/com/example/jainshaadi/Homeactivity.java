package com.example.jainshaadi;

 // Replace with your actual package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import android.util.Log;
import android.os.Handler;
import java.util.Collections;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.View;
import android.content.Intent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

public class Homeactivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardItem> cardItemList;
    private DatabaseReference databaseRef;
    private String currentUserId;
    private static final int PAGE_SIZE = 3;
    private int currentPage = 1;
    private Query query;
    private ValueEventListener valueEventListener;
    private DatabaseReference profilesRef;
    private final Handler handler = new Handler();
    private int check = 0;
    private int val = 1;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
/*
       recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardItemList = new ArrayList<>();

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Set the current user's ID (replace with your own logic)
        currentUserId = "profile1";
        Log.e("e","curent2 = "+currentUserId);
        // Profile Reference
        profilesRef = database.getReference("cardItems");

        // Query
        query = profilesRef
                .orderByChild("profileGender")
                .equalTo("Female");

        // Set up SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Load the data again when the user swipes to refresh
                loadCardItems();
            }
        });

        // Load the initial data
        loadCardItems();
        LinearLayout saveIcon = findViewById(R.id.saved);
        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                Intent intent = new Intent(Homeactivity.this, SaveProfileDisplay.class);
                startActivity(intent);
            }
        });

        GridLayout myProfile = findViewById(R.id.navigation_profile);
        myProfile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                Intent intent = new Intent(Homeactivity.this, MyProfile.class);
                startActivity(intent);
            }
        });

        ImageView search = findViewById(R.id.navigation_search);
        search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                Intent intent = new Intent(Homeactivity.this, Search.class);
                Log.e("e","curent3 = "+currentUserId);
                intent.putExtra("currentUserId", currentUserId);
                startActivity(intent);
            }
        });

    }

    private void loadCardItems() {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cardItemList.clear(); // Clear the list before adding new data

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Parse data and create CardItem objects
                    CardItem cardItem = snapshot.getValue(CardItem.class);
                    if (cardItem != null) {
//                        cardItem.setProfileId(snapshot.getKey()); // Set the profile ID
                        cardItemList.add(cardItem);
                    }
                }

                Collections.shuffle(cardItemList);

                // Initialize your CardAdapter with database reference and current user ID
                databaseRef = FirebaseDatabase.getInstance().getReference();
                cardAdapter = new CardAdapter(Homeactivity.this, cardItemList, databaseRef, currentUserId);

                recyclerView.setAdapter(cardAdapter);

                // Stop the swipe-to-refresh animation
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("data error", "error :" + error);

                // Stop the swipe-to-refresh animation
                swipeRefreshLayout.setRefreshing(false);
            }
        });*/
    }
    }
