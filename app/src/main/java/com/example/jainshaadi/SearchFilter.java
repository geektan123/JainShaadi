package com.example.jainshaadi;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.jainshaadi.CardAdapter;
import com.example.jainshaadi.CardItem;
import com.example.jainshaadi.MyProfile;
import com.example.jainshaadi.R;
import com.example.jainshaadi.SaveProfileDisplay;
import com.example.jainshaadi.Search;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SearchFilter extends AppCompatActivity {
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
    private String filterType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardItemList = new ArrayList<>();

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Set the current user's ID (replace with your own logic)
        Intent intent = getIntent();
        currentUserId = intent.getStringExtra("currentUserId");
        filterType = intent.getStringExtra("filterType");
        currentUserId = FirebaseAuth.getInstance().getUid();
        Log.e("ehjjj","current = "+currentUserId);
        Log.e("ekkkka","filter = "+filterType);

        // Profile Reference
        profilesRef = database.getReference("users");

        // Query
        if (filterType.equals("Digambar")) {
            Log.e("errorfff","digambar"+currentUserId);
            query = profilesRef
                    .orderByChild("Category")
                    .equalTo("Digambar");
        } else if (filterType.equals("Shwetamber")) {
            query = profilesRef
                    .orderByChild("Category")
                    .equalTo("Shwetamber");
        } else if (filterType.equals("Location")) {
            DatabaseReference userLocationRef = FirebaseDatabase.getInstance().getReference("users").child(currentUserId).child("State");

            userLocationRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String currentLocation = dataSnapshot.getValue(String.class);
                    // Do something with the current user's location (currentLocation)
                    if (currentLocation != null) {
                        query = profilesRef
                                .orderByChild("State")
                                .equalTo(currentLocation);
                        loadCardItems();
                    } else {
                        query = profilesRef
                                .orderByChild("Gender")
                                .equalTo("Female");
                    }
                    loadCardItems(); // Load card items after getting the location
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Handle any errors here
                }
            });
        }

        if(query == null)
        {
            query = profilesRef
                    .orderByChild("profileGender")
                    .equalTo("Female");
        }
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
                Intent intent = new Intent(com.example.jainshaadi.SearchFilter.this, SaveProfileDisplay.class);
                startActivity(intent);
            }
        });

        GridLayout myProfile = findViewById(R.id.my_profile);
        myProfile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                Intent intent = new Intent(com.example.jainshaadi.SearchFilter.this, MyProfile.class);
                startActivity(intent);
            }
        });

        ImageView search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                Intent intent = new Intent(com.example.jainshaadi.SearchFilter.this, Search.class);
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
                cardAdapter = new CardAdapter(com.example.jainshaadi.SearchFilter.this, cardItemList, databaseRef, currentUserId);

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
        });
    }
}