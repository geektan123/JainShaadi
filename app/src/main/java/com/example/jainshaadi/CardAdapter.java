package com.example.jainshaadi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ValueEventListener;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<CardItem> cardItemList;
    private Context context;
    private DatabaseReference databaseRef;
    private String currentUserId;

    public CardAdapter(Context context, List<CardItem> cardItemList, DatabaseReference databaseRef, String currentUserId) {
        this.context = context;
        this.cardItemList = cardItemList;
        this.databaseRef = databaseRef;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardItem cardItem = cardItemList.get(position);

        holder.postedByTextView.setText(cardItem.getPostedBy());
        holder.profileImageView.setImageResource(R.drawable.profile_picture);
        holder.profileNameTextView.setText(cardItem.getProfileName());
        holder.profileWorkTextView.setText(cardItem.getProfileWork());
        holder.profileBioTextView.setText(cardItem.getProfileBio());
        holder.profileBirthDateTextView.setText(cardItem.getProfileBirthDate());
        holder.profileCommunityTextView.setText(cardItem.getProfileCommunity());
        holder.profileIncomeTextView.setText(cardItem.getProfileIncome());
        holder.profileLocationTextView.setText(cardItem.getProfileLocation());
        holder.Saved = false;
        DatabaseReference savedProfilesRef = FirebaseDatabase.getInstance().getReference("profile_details").child(currentUserId).child("savedProfiles");
        savedProfilesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot profileSnapshot : dataSnapshot.getChildren()) {
//                    Log.e("error",cardItem.getProfileId() + "&" + profileSnapshot.getKey() + "&" + (cardItem.getProfileId().equals(profileSnapshot.getKey())));
                    if(cardItem.getProfileId().equals(profileSnapshot.getKey()))
                    {
                        holder.Saved = true;
//                        Log.e("error","true1");
                    }
                }
                if(holder.Saved)
                {
                    holder.savedButtonText.setText("Unsave");
                }
                else
                {
                    Log.e("error","false");
                }
            }

            public void onCancelled(DatabaseError error) {
                // Handle errors
            }
        });



        holder.savedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    String profileId = cardItemList.get(clickedPosition).getProfileId();
                    if(holder.Saved)
                    {
                        deleteProfileFromFirebase(profileId);
                        holder.savedButtonText.setText("Save");
                        holder.Saved = false;
                    }
                    else {
                        saveProfileToFirebase(profileId);
                        holder.savedButtonText.setText("Unsave");
                        holder.Saved = true;
                    }
                }
            }
        });

        holder.viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    String profileId = cardItemList.get(clickedPosition).getProfileId();
                    Intent intent = new Intent(context, ViewProfile.class);
                    intent.putExtra("currentUserId", currentUserId);
                    intent.putExtra("profileId", profileId);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView postedByTextView;
        ImageView profileImageView;
        TextView profileNameTextView;
        TextView profileWorkTextView;
        TextView profileBioTextView;
        TextView profileBirthDateTextView;
        TextView profileCommunityTextView;
        TextView profileIncomeTextView;
        TextView profileLocationTextView;
        LinearLayout savedButton;
        boolean Saved;
        TextView savedButtonText;
        LinearLayout viewProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            postedByTextView = itemView.findViewById(R.id.postedBy);
            profileImageView = itemView.findViewById(R.id.profileImg);
            profileNameTextView = itemView.findViewById(R.id.profileName);
            profileWorkTextView = itemView.findViewById(R.id.profileWork);
            profileBioTextView = itemView.findViewById(R.id.profileBio);
            profileBirthDateTextView = itemView.findViewById(R.id.profileBirthDate);
            profileCommunityTextView = itemView.findViewById(R.id.profileCommunity);
            profileIncomeTextView = itemView.findViewById(R.id.profileIncome);
            profileLocationTextView = itemView.findViewById(R.id.profileLocation);
            savedButton = itemView.findViewById(R.id.save);
            savedButtonText = itemView.findViewById(R.id.saveButtonText);
            viewProfile = itemView.findViewById(R.id.viewProfile);
        }
    }

    private void saveProfileToFirebase(String profileId) {
        DatabaseReference cardItem1Ref = databaseRef.child("profile_details");
        cardItem1Ref.child(currentUserId).child("savedProfiles").child(profileId).setValue(true);
//        Log.e("click",profileId);
    }
    private void deleteProfileFromFirebase(String profileId) {
        Log.e("error",currentUserId + "/" + profileId );
        DatabaseReference cardItem1Ref = databaseRef.child("profile_details");
        cardItem1Ref.child(currentUserId).child("savedProfiles").child(profileId).removeValue();
//        Log.e("click",profileId);
    }
}
