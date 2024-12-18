package com.jainmaitri.app; // Replace with your actual package name

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.Collections;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.View;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

// Import statements...
public class SearchFilter extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardItem> cardItemList;
    private DatabaseReference databaseRef;
    private String currentUserId;

    private static final int PAGE_SIZE = 2;
    private int currentPage = 1;
    private List<String> shuffledUserIds;
    private int visibleThreshold = 2;
    private boolean isLoading = false;

    private ValueEventListener valueEventListener;
    private DatabaseReference profilesRef;
    private DatabaseReference profilesRef1;
    private DatabaseReference profilesRef2;
    private Query query;
    private SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progress;
    Object currentUserGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_display);
        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerView);
        progress = findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        databaseRef = FirebaseDatabase.getInstance().getReference();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardItemList = new ArrayList<>();
        shuffledUserIds = new ArrayList<>();

        ImageView BackIcon = findViewById(R.id.back);
        BackIcon.setOnClickListener(view -> {
            // Redirect to SavedProfilesActivity
            onBackPressed();
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
        swipeRefreshLayout.setOnRefreshListener(() -> {
            cardItemList.clear();
            currentPage = 1;
            loadShuffledUserIds();
        });


        // Initialize the adapter
        cardAdapter = new CardAdapter(SearchFilter.this, cardItemList, databaseRef, currentUserId);


        // Load shuffled user IDs and profiles
        profilesRef1.child("active").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Object currentUserActive = task.getResult().getValue();

                if (currentUserActive != null && (currentUserActive.toString().equals("enabled")||currentUserActive.toString().equals("admin"))) {
                    loadShuffledUserIds();
                } else {
                    TextView Empty = findViewById(R.id.empty);
                    progress.setVisibility(View.GONE);
                    Empty.setText("Discovery Disabled \n \n To view other profiles, enable Discovery in My Profile -> Settings.\n \n Thank you.");
                    Empty.setVisibility(View.VISIBLE);
                }

            }
        });

//        GridLayout myProfile = findViewById(R.id.my_profile);
//        myProfile.setOnClickListener(view -> {
//            // Redirect to SavedProfilesActivity
//            Intent intent = new Intent(SearchFilter.this, MyProfile.class);
//            startActivity(intent);
//        });
//
//        ImageView search = findViewById(R.id.search);
//        search.setOnClickListener(view -> {
//            // Redirect to SavedProfilesActivity
//            Intent intent = new Intent(SearchFilter.this, Search.class);
//            Log.e("e", "curent3 = " + currentUserId);
//            intent.putExtra("currentUserId", currentUserId);
//            startActivity(intent);
//        });
//        GridLayout Chat = findViewById(R.id.chatlist);
//        Chat.setOnClickListener(view -> {
//            // Redirect to SavedProfilesActivity
//            Intent intent = new Intent(SearchFilter.this, Chatlist.class);
//            startActivity(intent);
//        });

        // Load the initial set of profiles


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

    private void loadShuffledUserIds() {
        recyclerView.setAdapter(cardAdapter);
        shuffledUserIds.clear();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        profilesRef1.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                currentUserGender = task.getResult().child("Gender").getValue();
                Object currentUserState = task.getResult().child("State").getValue();
                Object currentUserAge = task.getResult().child("Age").getValue();
                Intent intent = getIntent();
                String filterType = intent.getStringExtra("filterType");
                if (filterType.equals("Digambar")) {
                    query = profilesRef.orderByChild("Category").equalTo("Digambar");
                } else if (filterType.equals("Shwetambar")) {
                    query = profilesRef.orderByChild("Category").equalTo("Shwetambar");
                } else if (filterType.equals("Location")) {
                    query = profilesRef.orderByChild("State").equalTo(currentUserState.toString());
                } else if (filterType.equals("Age")) {
                    int age = Integer.parseInt((String) currentUserAge);
                    query = profilesRef.orderByChild("AgeInt").startAt(age - 2).endAt(age + 2);
                } else {
                    query = profilesRef.orderByChild("NoResult").equalTo("NoResult");
                }

//                if (currentUserGender != null && currentUserGender.toString().equals("Female")) {
//                    if(filterType.equals("Digambar")) {
//                        query = profilesRef.orderByChild("Gender_Category").equalTo("Male_Digamber");
//                    } else if (filterType.equals("Shvetambar")) {
//                        query = profilesRef.orderByChild("Gender_Category").equalTo("Male_Shvetambar");
//                    }
//                    else
//                    {
//                        query = profilesRef.orderByChild("Gender").equalTo("Male");
//                    }
//                } else {
//                    if(filterType.equals("Digambar")) {
//                        query = profilesRef.orderByChild("Category").equalTo("Digamber");
//                    } else if (filterType.equals("Shvetambar")) {
//                        query = profilesRef.orderByChild("Category").equalTo("Shvetambar");
//                    }
//                    else
//                    {
//                        query = profilesRef.orderByChild("Gender").equalTo("Female");
//                    }
//                }
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> allUserIds = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (currentUserGender != null) {
                                Object Status = snapshot.child("status").getValue();
                                Object Active = snapshot.child("active").getValue();
                                String show = "not";

                                if(Status != null && Active != null) {
                                    if ((!((snapshot.child("Gender").getValue(String.class)).equals(currentUserGender.toString())))
                                            && (((Status.toString()).equals("Registered"))
                                            || ((Status.toString()).equals("Completed"))) &&(Active.toString().equals("enabled"))) {
                                        allUserIds.add(snapshot.getKey());
                                    }
                                }
                            }
                        }
                        if (allUserIds.isEmpty()) {
                            TextView empty = findViewById(R.id.empty);
                            empty.setVisibility(View.VISIBLE);
                        }
                        // Shuffle the list of user IDs
                        Collections.shuffle(allUserIds);

                        // Use the shuffled list for paginated loading
                        shuffledUserIds.addAll(allUserIds);
                        loadNextProfiles();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error
                    }
                });
            }
        });
    }

    private void loadNextProfiles() {
        int start = (currentPage - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, shuffledUserIds.size());

        for (int i = start; i < end; i++) {
            String userId = shuffledUserIds.get(i);

            profilesRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Object Status = snapshot.child("status").getValue();
                    if(Status != null) {
                        if ((!((snapshot.child("Gender").getValue(String.class)).equals(currentUserGender.toString())))
                                && (((Status.toString()).equals("Registered"))
                                || ((Status.toString()).equals("Completed")))) {
                            CardItem cardItem = snapshot.getValue(CardItem.class);
                            if (cardItem != null) {
                                cardItemList.add(cardItem);
                                cardAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    isLoading = false;

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    isLoading = false;
                    // Handle the error
                }
            });
        }
        progress.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        currentPage++;
    }

    // ... Other existing methods ...
}
