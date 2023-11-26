package com.example.jainshaadi;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.jainshaadi.CardAdapter;
import com.example.jainshaadi.CardItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SaveProfileDisplay extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardItem> cardItemList;
    private SwipeRefreshLayout swipeRefreshLayout;

    // Initialize your current user ID
    private String currentUserId = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_profile_display);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardItemList = new ArrayList<>();

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference(); // Reference to the root of the database

        // Initialize your CardAdapter with an empty list
        cardAdapter = new CardAdapter(this, cardItemList, databaseRef, currentUserId);
        recyclerView.setAdapter(cardAdapter);

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
    }

    private void loadCardItems() {
        DatabaseReference savedProfilesRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId).child("savedProfiles");
        savedProfilesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cardItemList.clear();

                for (DataSnapshot profileSnapshot : dataSnapshot.getChildren()) {
                    String profileId = profileSnapshot.getKey();
                    DatabaseReference profileRef = FirebaseDatabase.getInstance().getReference("users").child(profileId);
                    profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot profileDataSnapshot) {
                            if (profileDataSnapshot.exists()) {
                                CardItem cardItem = profileDataSnapshot.getValue(CardItem.class);
                                if (cardItem != null) {
                                    cardItemList.add(cardItem);
                                    cardAdapter.notifyDataSetChanged();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Handle errors
                        }
                    });
                }

                // Stop the swipe-to-refresh animation
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle errors
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}