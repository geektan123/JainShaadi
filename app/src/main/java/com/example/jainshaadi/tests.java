//package com.example.jainshaadi; // Replace with your actual package name
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.DialogInterface;
//import android.os.Bundle;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.cardview.widget.CardView;
//import java.util.ArrayList;
//import java.util.List;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.GridLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import java.util.ArrayList;
//import java.util.List;
//import androidx.annotation.NonNull;
//import android.util.Log;
//import android.os.Handler;
//import java.util.Collections;
//import java.util.Map;
//
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import android.view.View;
//import android.content.Intent;
//import android.widget.Toast;
//
//import com.example.jainshaadi.CardAdapter;
//import com.example.jainshaadi.CardItem;
//import com.example.jainshaadi.MyProfile;
//import com.example.jainshaadi.R;
//import com.example.jainshaadi.SaveProfileDisplay;
//import com.example.jainshaadi.Search;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.database.Query;
//
//public class Homeactivity extends AppCompatActivity {
//    private RecyclerView recyclerView;
//    private CardAdapter cardAdapter;
//    private List<CardItem> cardItemList;
//    private DatabaseReference databaseRef;
//    private String currentUserId;
//
//    private static final int PAGE_SIZE = 3;
//    private int currentPage = 1;
//
//    Query query;
//    String s1;
//    String s2;
//
//    private ValueEventListener valueEventListener;
//    private DatabaseReference profilesRef;
//    private DatabaseReference profilesRef1;
//    private DatabaseReference profilesRef2;
//    String getPro;
//    String getid;
//
//    private final Handler handler = new Handler();
//    private int check = 0;
//    private int val = 1;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    Query maleQuery;
//    Query femaleQuery;
//    String currentGender;
//    ArrayList<String> arrayList = new ArrayList<>();
//    Map map;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.home);
//        getSupportActionBar().hide();
//
//        recyclerView = findViewById(R.id.recyclerView);
//        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        cardItemList = new ArrayList<>();
//
//        // Initialize Firebase Database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        // Set the current user's ID (replace with your own logic)
//        currentUserId = FirebaseAuth.getInstance().getUid();
//        Log.e("e", "curent2 = " + currentUserId);
//        // Profile Reference
//        profilesRef = database.getReference("users");
//        profilesRef1 = profilesRef.child(currentUserId);
//        profilesRef2 = profilesRef1.child("Gender");
//
//        String currentUserId = FirebaseAuth.getInstance().getUid();
//
//        // Set up SwipeRefreshLayout
//        swipeRefreshLayout.setOnRefreshListener(() -> loadCardItems());
//
//        // Load the initial data
//        loadCardItems();
//
//        LinearLayout saveIcon = findViewById(R.id.saved);
//        saveIcon.setOnClickListener(view -> {
//            // Redirect to SavedProfilesActivity
//            Intent intent = new Intent(Homeactivity.this, SaveProfileDisplay.class);
//            startActivity(intent);
//        });
//
//        GridLayout myProfile = findViewById(R.id.my_profile);
//        myProfile.setOnClickListener(view -> {
//            // Redirect to SavedProfilesActivity
//            Intent intent = new Intent(Homeactivity.this, MyProfile.class);
//            startActivity(intent);
//        });
//
//        ImageView search = findViewById(R.id.search);
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
//
//
//
//
//    }
//
//    public void onBackPressed() {
//        // Do nothing or show a message, preventing the user from going back
//        finish();
//    }
//
//    private void loadCardItems() {
//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
//
//        userRef.child("Gender").get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Object currentUserGender = task.getResult().getValue();
//
//                if (currentUserGender != null && currentUserGender.toString().equals("Female")) {
//                    query = profilesRef.orderByChild("Gender").equalTo("Male");
//                } else {
//                    query = profilesRef.orderByChild("Gender").equalTo("Female");
//                }
//
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        cardItemList.clear(); // Clear the list before adding new data
//
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            // Parse data and create CardItem objects
//                            CardItem cardItem = snapshot.getValue(CardItem.class);
//                            if (cardItem != null) {
//                                cardItemList.add(cardItem);
//                            }
//                        }
//
//                        Collections.shuffle(cardItemList);
//
//                        databaseRef = FirebaseDatabase.getInstance().getReference();
//                        cardAdapter = new CardAdapter(Homeactivity.this, cardItemList, databaseRef, currentUserId);
//
//                        recyclerView.setAdapter(cardAdapter);
//
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError error) {
//                        Log.e("data error", "error :" + error);
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                });
//            } else {
//                // Handle the case where task is not successful
//            }
//        });
//    }
//}
//
//
