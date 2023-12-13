package com.example.jainshaadi; // Replace with your actual package name

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import android.util.Log;
import android.os.Handler;
import java.util.Collections;
import java.util.Map;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.View;
import android.content.Intent;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jainshaadi.CardAdapter;
import com.example.jainshaadi.CardItem;
import com.example.jainshaadi.MyProfile;
import com.example.jainshaadi.R;
import com.example.jainshaadi.SaveProfileDisplay;
import com.example.jainshaadi.Search;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

// Import statements...
public class Homeactivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
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
    private SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
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
        cardAdapter = new CardAdapter(Homeactivity.this, cardItemList, databaseRef, currentUserId);


        // Load shuffled user IDs and profiles
        loadShuffledUserIds();

        LinearLayout saveIcon = findViewById(R.id.saved);
        saveIcon.setOnClickListener(view -> {
            // Redirect to SavedProfilesActivity
            Intent intent = new Intent(Homeactivity.this, SaveProfileDisplay.class);
            startActivity(intent);
        });
//
//        GridLayout myProfile = findViewById(R.id.my_profile);
//        myProfile.setOnClickListener(view -> {
//            // Redirect to SavedProfilesActivity
//            Intent intent = new Intent(Homeactivity.this, MyProfile.class);
//            startActivity(intent);
//        });
//
//        ImageView search = findViewById(R.id.back);
//        search.setOnClickListener(view -> {
//            // Redirect to SavedProfilesActivity
//            Intent intent = new Intent(Homeactivity.this, Search.class);
//            Log.e("e", "curent3 = " + currentUserId);
//            intent.putExtra("currentUserId", currentUserId);
//            startActivity(intent);
//        });
//        GridLayout Chat = findViewById(R.id.chatlist);
//        Chat.setOnClickListener(view -> {
//            // Redirect to SavedProfilesActivity
//            Intent intent = new Intent(Homeactivity.this, Chatlist.class);
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

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // Load more profiles when reaching the end
                    loadNextProfiles();
                    isLoading = true;
                }
            }
        });
    }

    private void loadShuffledUserIds() {
        recyclerView.setAdapter(cardAdapter);
        shuffledUserIds.clear();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        profilesRef1.child("Gender").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Object currentUserGender = task.getResult().getValue();

                        if (currentUserGender != null && currentUserGender.toString().equals("Female")) {
                            query = profilesRef.orderByChild("Gender").equalTo("Male");
                        } else {
                            query = profilesRef.orderByChild("Gender").equalTo("Female");
                        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> allUserIds = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.e("a","val0 = "+ (snapshot.child("active").getValue(String.class)));
                    Object Status = snapshot.child("status").getValue();
                    Object Active = snapshot.child("active").getValue();
                    if(Active != null && Status != null) {
                        if (((Active.toString()).equals("enabled"))
                                && (((Status.toString()).equals("Registered"))
                                || ((Status.toString()).equals("Completed")))
                        ) {
                            allUserIds.add(snapshot.getKey());
                        }
                    }
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
                    Object Active = snapshot.child("active").getValue();
                    if(Active != null && Status != null) {
                        if (((Active.toString()).equals("enabled"))
                                && (((Status.toString()).equals("Registered"))
                                || ((Status.toString()).equals("Completed")))
                        ) {
                            CardItem cardItem = snapshot.getValue(CardItem.class);
                            if (cardItem != null) {
                                cardItemList.add(cardItem);
                                cardAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the error
                }
            });
        }
        progress.setVisibility(View.INVISIBLE);
        isLoading = false;
        swipeRefreshLayout.setRefreshing(false);
        currentPage++;
    }

    // ... Other existing methods ...
}
