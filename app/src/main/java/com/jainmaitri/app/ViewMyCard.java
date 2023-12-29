package com.jainmaitri.app; // Replace with your actual package name

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

// Import statements...
public class ViewMyCard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyCardAdapter cardAdapter;
    private List<CardItem> cardItemList;
    private DatabaseReference databaseRef;
    private String currentUserId;

    private static final int PAGE_SIZE = 5;
    private int currentPage = 1;
    private List<String> shuffledUserIds;
    private int visibleThreshold = 5;
    private boolean isLoading = false;

    private ValueEventListener valueEventListener;
    private DatabaseReference profilesRef;
    private DatabaseReference profilesRef1;
    private DatabaseReference profilesRef2;
    private Query query;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_my_card);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_regestration);
        ImageView BackButton = actionBar.getCustomView().findViewById(R.id.logo);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for the logo
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        progress = findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);
        databaseRef = FirebaseDatabase.getInstance().getReference();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardItemList = new ArrayList<>();
        shuffledUserIds = new ArrayList<>();

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Set the current user's ID (replace with your own logic)
        currentUserId = FirebaseAuth.getInstance().getUid();

        // Profile Reference
        profilesRef = database.getReference("users");
        profilesRef1 = profilesRef.child(currentUserId);
        profilesRef2 = profilesRef1.child("Gender");

        // Set up SwipeRefreshLayout


        // Initialize the adapter
        cardAdapter = new MyCardAdapter(ViewMyCard.this, cardItemList, databaseRef, currentUserId);


        // Load shuffled user IDs and profiles
        loadShuffledUserIds();
//


        // Set up RecyclerView scrolling listener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
            }
        });
    }

    private void loadShuffledUserIds() {
        recyclerView.setAdapter(cardAdapter);
        shuffledUserIds.clear();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Object Status = snapshot.child("status").getValue();
                    Object Active = snapshot.child("active").getValue();
                    if (Active != null && Status != null) {
                        CardItem cardItem = snapshot.getValue(CardItem.class);
                        if (cardItem != null) {
                            cardItemList.add(cardItem);
                            cardAdapter.notifyDataSetChanged();
                        }
                    }
                }
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle the error
                    }
                });
        progress.setVisibility(View.INVISIBLE);
    }

    // ... Other existing methods ...
}
