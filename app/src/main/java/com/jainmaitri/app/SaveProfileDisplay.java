package com.jainmaitri.app; // Replace with your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveProfileDisplay extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardItem> cardItemList;
    private DatabaseReference databaseRef;
    private String currentUserId;
    TextView Empty;

    private static final int PAGE_SIZE = 2;
    private int currentPage = 1;
    private List<String> shuffledProfileIds;
    private int visibleThreshold = 1;
    private boolean isLoading = false;

    private SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseReference profilesRef;
    private DatabaseReference profilesRef1;
    private DatabaseReference profilesRef2;
    private Query query;
    ProgressBar progress;
    SharedViewModel sharedViewModel;
    private SwipeRefreshLayout.OnRefreshListener refreshListener, disableListner;
    boolean isRefreshed = false;

    List<CardItem> existingCardItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_profile_display);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        databaseRef = FirebaseDatabase.getInstance().getReference();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progress = findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);
        Empty = findViewById(R.id.empty);
        ImageView Back = findViewById(R.id.back);

        cardItemList = new ArrayList<>();
        shuffledProfileIds = new ArrayList<>();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String token = task.getResult();
                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
                        DatabaseReference currentProfileRef = databaseRef.child(FirebaseAuth.getInstance().getUid());
                        Map<String, Object> updateData = new HashMap<>();
                        updateData.put("FCMToken", token);
                        currentProfileRef.updateChildren(updateData);
                    }
                });

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Set the current user's ID (replace with your own logic)
        currentUserId = FirebaseAuth.getInstance().getUid();

        // Profile Reference
        profilesRef = database.getReference("users");
        profilesRef1 = profilesRef.child(currentUserId);
        profilesRef2 = profilesRef1.child("Gender");

        // Set up SwipeRefreshLayout
        refreshListener = () -> {
            cardItemList.clear();
            currentPage = 1;
            loadShuffledProfileIds();
        };

        Back.setOnClickListener(v -> {
            // Redirect to SavedProfilesActivity
            onBackPressed();
        });

        swipeRefreshLayout.setOnRefreshListener(refreshListener);

//        ImageView saveIcon = findViewById(R.id.saved);
//        saveIcon.setOnClickListener(v -> {
//            // Redirect to SavedProfilesActivity
//            Intent intent = new Intent(this, SaveProfileDisplay.class);
//            startActivity(intent);
//        });

        // Initialize the adapter
        cardAdapter = new CardAdapter(this, cardItemList, databaseRef, currentUserId);

        existingCardItemList = sharedViewModel.getCardItemList();

        // Load data as usual
        profilesRef1.child("active").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Object currentUserActive = task.getResult().getValue();

                if (currentUserActive != null && (currentUserActive.toString().equals("enabled") || currentUserActive.toString().equals("admin"))) {
                    if (!existingCardItemList.isEmpty()) {
                        // Use the existing data
                        cardItemList.addAll(existingCardItemList);
                        Log.e("e", "v0 = " + existingCardItemList);
                        cardAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(cardAdapter);
                        progress.setVisibility(View.INVISIBLE);
                    } else {
                        loadShuffledProfileIds();
                    }
                } else {
                    progress.setVisibility(View.GONE);
                    Empty.setText("Discovery Disabled \n \n To view other profiles, enable Discovery in My Profile -> Settings.\n \n Thank you.");
                    Empty.setVisibility(View.VISIBLE);
                }

            }
        });
        // Set up RecyclerView scrolling listener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                Log.e("t","isLoading = "+isLoading + "totalItem = " + totalItemCount + "lastVisible = " + lastVisibleItem+ "visi = " + visibleThreshold);

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // Load more profiles when reaching the end
                    isLoading = true;
                    loadNextProfiles();
                }
            }
        });
    }

    private void loadShuffledProfileIds() {
        recyclerView.setAdapter(cardAdapter);
        shuffledProfileIds = new ArrayList<>();
        DatabaseReference savedProfilesRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId).child("savedProfiles");

        savedProfilesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> allProfileIds = new ArrayList<>();
                for (DataSnapshot profileSnapshot : dataSnapshot.getChildren()) {
                    allProfileIds.add(profileSnapshot.getKey());
                }
                if (allProfileIds.isEmpty()) {
                    Empty.setText("No Saved Profiles");
                    Empty.setVisibility(View.VISIBLE);
                }

                // Shuffle the list of profile IDs
//                Collections.shuffle(allProfileIds);

                // Use the shuffled list for paginated loading
                shuffledProfileIds.addAll(allProfileIds);
                loadNextProfiles();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle errors
                isLoading = false;
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadNextProfiles() {
        int start = (currentPage - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, shuffledProfileIds.size());

        for (int i = start; i < end; i++) {
            String profileId = shuffledProfileIds.get(i);

            profilesRef.child(profileId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    Object Status = snapshot.child("status").getValue();
                    Object Active = snapshot.child("active").getValue();
                    if (Active != null && Status != null) {
                        if (((Active.toString()).equals("enabled"))
                                && (((Status.toString()).equals("Registered"))
                                || ((Status.toString()).equals("Completed")))
                        ) {
                            CardItem cardItem = snapshot.getValue(CardItem.class);
                            if (cardItem != null) {
                                cardItemList.add(cardItem);
                                cardAdapter.notifyDataSetChanged();
                                Empty.setVisibility(View.GONE);
                                Log.e("e", "c0 = " + cardItemList);
                                sharedViewModel.setCardItemList(cardItemList);
                            }
                        }
                    }
                    isLoading = false;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the error
                    isLoading = false;
                }
            });
        }

        swipeRefreshLayout.setRefreshing(false);
        progress.setVisibility(View.INVISIBLE);
        currentPage++;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Empty.setVisibility(View.GONE);
        profilesRef1.child("active").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Object currentUserActive = task.getResult().getValue();

                    if (cardItemList.isEmpty()) {
                        Empty.setText("No Saved Profiles");
                        Empty.setVisibility(View.VISIBLE);
                    }
                    swipeRefreshLayout.setOnRefreshListener(refreshListener);

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}