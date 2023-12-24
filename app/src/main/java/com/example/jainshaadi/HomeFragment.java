package com.example.jainshaadi; // Replace with your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import androidx.lifecycle.ViewModelProvider;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<CardItem> cardItemList;
    private DatabaseReference databaseRef;
    private String currentUserId;
    TextView Empty;

    private static final int PAGE_SIZE = 5;
    private int currentPage = 1;
    private List<String> shuffledUserIds;
    private int visibleThreshold = 5;
    private boolean isLoading = false;

    private SwipeRefreshLayout swipeRefreshLayout;
    private DatabaseReference profilesRef;
    private DatabaseReference profilesRef1;
    private DatabaseReference profilesRef2;
    private Query query;
    ProgressBar progress;
    SharedViewModel sharedViewModel;
    private SwipeRefreshLayout.OnRefreshListener refreshListener , disableListner;
    boolean isRefreshed = false;


    List<CardItem> existingCardItemList;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);


        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        databaseRef = FirebaseDatabase.getInstance().getReference();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progress = view.findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);
        Empty = view.findViewById(R.id.empty);

        cardItemList = new ArrayList<>();
        shuffledUserIds = new ArrayList<>();
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    String token = task.getResult();
                    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
                    DatabaseReference currentProfileRef = databaseRef.child(FirebaseAuth.getInstance().getUid());
                    Map<String, Object> updateData = new HashMap<>();
                    updateData.put("FCMToken" , token);
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
            loadShuffledUserIds();
        };

        swipeRefreshLayout.setOnRefreshListener(refreshListener);


        ImageView saveIcon = view.findViewById(R.id.saved);
        saveIcon.setOnClickListener(v -> {
            // Redirect to SavedProfilesActivity
            Intent intent = new Intent(requireContext(), SaveProfileDisplay.class);
            startActivity(intent);
        });

        // Initialize the adapter
        cardAdapter = new CardAdapter(getContext(), cardItemList, databaseRef, currentUserId);

        existingCardItemList = sharedViewModel.getCardItemList();

            // Load data as usual
        profilesRef1.child("active").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                    Object currentUserActive = task.getResult().getValue();

                    if (currentUserActive != null && (currentUserActive.toString().equals("enabled")||currentUserActive.toString().equals("admin"))) {
                        if (!existingCardItemList.isEmpty()) {
                            // Use the existing data
                            cardItemList.addAll(existingCardItemList);
                            Log.e("e","v0 = "+existingCardItemList);
                            cardAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(cardAdapter);
                            progress.setVisibility(View.INVISIBLE);
                        } else {
                            loadShuffledUserIds();
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

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // Load more profiles when reaching the end
                    loadNextProfiles();
                    isLoading = true;
                }
            }
        });

        return view;
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
                            Log.e("a", "val0 = " + (snapshot.child("active").getValue(String.class)));
                            Object Status = snapshot.child("status").getValue();
                            Object Active = snapshot.child("active").getValue();
                            if (Active != null && Status != null) {
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
                                Log.e("e","c0 = "+cardItemList);
                                sharedViewModel.setCardItemList(cardItemList);
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
        isLoading = false;
        swipeRefreshLayout.setRefreshing(false);
        progress.setVisibility(View.INVISIBLE);
        currentPage++;
    }
    public void onStart() {
        super.onStart();
        profilesRef1.child("active").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Object currentUserActive = task.getResult().getValue();

                if (currentUserActive != null && (currentUserActive.toString().equals("enabled")||currentUserActive.toString().equals("admin"))) {
//                    Empty.setVisibility(View.GONE);
                    if(cardItemList.isEmpty())
                    {
                        Empty.setText("Refresh to Load Profiles");

                    }
                    swipeRefreshLayout.setOnRefreshListener(refreshListener);
                } else {
                    disableListner = () -> {
                        swipeRefreshLayout.setRefreshing(false);
                    };
                    swipeRefreshLayout.setOnRefreshListener(disableListner);
                    cardItemList.clear();
                    existingCardItemList.clear();
                    cardAdapter.notifyDataSetChanged();
                    progress.setVisibility(View.GONE);
                    Empty.setText("Discovery Disabled \n \n To view other profiles, enable Discovery in My Profile -> Settings.\n \n Thank you.");
                    Empty.setVisibility(View.VISIBLE);
                }

            }
        });


    }
    public void onStop() {
        super.onStop();
    }
}
