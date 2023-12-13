package com.example.jainshaadi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.common.value.qual.StringVal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Chatlist extends Fragment {

    private RecyclerView recyclerView;
    private ChatListAdapter chatListAdapter;
    private List<DataSnapshot> chatSnapshots;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference usersReference;
    private FirebaseUser currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.chatlist, container, false);

        recyclerView = root.findViewById(R.id.recyclerviewofspecific);
        chatSnapshots = new ArrayList<>();
        chatListAdapter = new ChatListAdapter(chatSnapshots);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatListAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            usersReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUser.getUid());
        }

        // Query to get the list of profile IDs and timestamps
        Query chatListQuery = usersReference.child("invite");

        chatListQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatSnapshots.clear();

                for (DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                    if(chatSnapshot.exists()) {
                        chatSnapshots.add(chatSnapshot);
                    }
                }

                Collections.sort(chatSnapshots, (snapshot1, snapshot2) -> {
                    long timestamp1 = snapshot1.child("timestamp").getValue(Long.class);
                    long timestamp2 = snapshot2.child("timestamp").getValue(Long.class);
                    return Long.compare(timestamp2, timestamp1);
                });

                // Update the RecyclerView
                chatListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        return root;
    }

    public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {
        private List<DataSnapshot> chatSnapshots;

        public ChatListAdapter(List<DataSnapshot> chatSnapshots) {
            this.chatSnapshots = chatSnapshots;
        }

        @NonNull
        @Override
        public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat, parent, false);
            return new ChatListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
            DataSnapshot chatSnapshot = chatSnapshots.get(position);

            String profileId = chatSnapshot.getKey();
            long timestamp = chatSnapshot.child("timestamp").getValue(Long.class);
            Date date = new Date(timestamp);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm");

            DatabaseReference profileDataRef = FirebaseDatabase.getInstance().getReference().child("users").child(profileId);
            profileDataRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Retrieve data from the dataSnapshot
                        String name = dataSnapshot.child("Name").getValue(String.class);
                        String profilePic = dataSnapshot.child("image01").getValue(String.class);
                        if (isActivityValid()) {
                            Glide.with(holder.itemView.getContext())
                                    .load(profilePic)
                                    .into(holder.profileImageView);
                        }
                        holder.nameTextView.setText(name);
                        holder.timestampTextView.setText(convertTimestampToString(timestamp));
                        holder.OpenChat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(requireContext(), SpecificChatActivity.class);
                                intent.putExtra("profileId", profileId);
                                intent.putExtra("Name", name);
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error
                }
            });

        }
        private boolean isActivityValid() {
            return getActivity() != null && !getActivity().isDestroyed() && !getActivity().isFinishing();
        }
        public  String convertTimestampToString(long timestamp) {
                // Get the current time in milliseconds
                long currentTime = System.currentTimeMillis();

                // Calculate the time difference in milliseconds
                long timeDifference = currentTime - timestamp;

                // Calculate time units
                long seconds = timeDifference / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;
                long months = days / 30;
                long years = days / 365;

                // Choose the appropriate format based on the time difference
                if (years > 0) {
                    return years + "y";
                } else if (months > 0) {
                    return months + "mo";
                } else if (days > 0) {
                    return days + "d";
                } else if (hours > 0) {
                    return hours + "h";
                } else if (minutes > 0) {
                    return minutes + "min";
                } else {
                    return "now";
                }
            }

        @Override
        public int getItemCount() {
            return chatSnapshots.size();
        }

        public class ChatListViewHolder extends RecyclerView.ViewHolder {
            ImageView profileImageView;
            TextView nameTextView;
            TextView timestampTextView;
            LinearLayout OpenChat;

            public ChatListViewHolder(@NonNull View itemView) {
                super(itemView);
                profileImageView = itemView.findViewById(R.id.profileImg);
                nameTextView = itemView.findViewById(R.id.name);
                timestampTextView = itemView.findViewById(R.id.description);
                OpenChat = itemView.findViewById(R.id.open_chat);
            }
        }
    }
}
