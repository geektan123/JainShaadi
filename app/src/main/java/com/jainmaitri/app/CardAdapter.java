package com.jainmaitri.app;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

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
        holder.postedByTextView.setText(cardItem.getAccount_Managed_for());
        Log.e("ac","acc = "+ cardItem.getAccount_Managed_for());

        Glide.with(holder.itemView.getContext()).load(cardItem.getImageUrl1()).into(holder.profileImageView);
       // Log.e("ProfileIdLogging", "ProfileId at position " + position + ": " + value);

          //holder.profileImageView.setImageResource(cardItem.getImageUrl1());


        holder.profileNameTextView.setText(cardItem.getName());
        holder.profileWorkTextView.setText(cardItem.getRole());
        holder.profileBioTextView.setText(cardItem.getDescription());
        holder.profileBirthDateTextView.setText(cardItem.getAge()+" Years Old,"+cardItem.getHeight());
        holder.profileCommunityTextView.setText(cardItem.getSubcategory());
        holder.profileIncomeTextView.setText(cardItem.getIncomeRange());
        holder.profileLocationTextView.setText(cardItem.getCity()+","+cardItem.getState());
        holder.Interest1.setText(cardItem.getInterest1());
        holder.Interest2.setText(cardItem.getInterest2());
        holder.Interest3.setText(cardItem.getInterest3());
        holder.Interest4.setText(cardItem.getInterest4());
        holder.Interest5.setText(cardItem.getInterest5());
        holder.Interest6.setText(cardItem.getInterest6());


        //holder.Saved = false;

        //Assuming you have a reference to the Firebase database

       // int clickedPosition = holder.getAdapterPosition();
        String profileId = cardItem.getProfileId();
        Log.e("sasssaa","ssaasa = "+ profileId);

        DatabaseReference savedProfilesRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId).child("savedProfiles");



        savedProfilesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.hasChild(profileId)) {
                    // The profile is saved
                    holder.savedButtonText.setText("Unsave");
                  holder.savedButtonText.setTextColor(ContextCompat.getColor(context, R.color.hex));
                   holder.imagesave.setImageResource(R.drawable.unsave);
                    holder.Saved = true;

                    // Additional actions if the profile is saved
                } else {
                    // The profile is not saved
                    holder.savedButtonText.setText("Save");
                    holder.savedButtonText.setTextColor(ContextCompat.getColor(context, R.color.red));
                    holder.imagesave.setImageResource(R.drawable.save_icon_01);

                    holder.Saved = false;

                    // Additional actions if the profile is not saved
                }
            }

            @Override
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
                        holder.savedButtonText.setTextColor(ContextCompat.getColor(context, R.color.red));
                        holder.imagesave.setImageResource(R.drawable.save_icon_01);
                    }
                    else {
                        saveProfileToFirebase(profileId);
                        holder.savedButtonText.setText("Unsave");

                        holder.Saved = true;
                        holder.savedButtonText.setTextColor(ContextCompat.getColor(context, R.color.hex));
                        holder.imagesave.setImageResource(R.drawable.unsave);
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
                    String name=cardItemList.get(clickedPosition).getName();
                    Intent intent = new Intent(context, ViewProfile.class);
                    intent.putExtra("currentUserId", currentUserId);
                    intent.putExtra("profileId", profileId);
                    intent.putExtra("Name", name);

                    context.startActivity(intent);
                }
            }
        });
    }

    private boolean isActivityValid() {
        return context instanceof Activity && !((Activity) context).isDestroyed() && !((Activity) context).isFinishing();
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
        ImageView imagesave;
        LinearLayout viewProfile;
        TextView Interest1;
        TextView Interest2;
        TextView Interest3;
        TextView Interest4;
        TextView Interest5;
        TextView Interest6;
 ShapeableImageView profilepic;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Saved=false;

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
             Interest1 = itemView.findViewById(R.id.profile_interest01);
            Interest2=itemView.findViewById(R.id.profile_interest02);
            Interest3=itemView.findViewById(R.id.profile_interest03);
            Interest4=itemView.findViewById(R.id.profile_interest04);
            Interest5=itemView.findViewById(R.id.profile_interest05);
            Interest6=itemView.findViewById(R.id.profile_interest06);
            profilepic=itemView.findViewById(R.id.profileImg);
            imagesave=itemView.findViewById(R.id.save_image);


        }
    }

    private void saveProfileToFirebase(String profileId) {
        DatabaseReference cardItem1Ref = databaseRef.child("users");
        cardItem1Ref.child(currentUserId).child("savedProfiles").child(profileId).setValue(true);
//        Log.e("click",profileId);
    }
    private void deleteProfileFromFirebase(String profileId) {
        Log.e("error",currentUserId + "/" + profileId );
        DatabaseReference cardItem1Ref = databaseRef.child("users");
        cardItem1Ref.child(currentUserId).child("savedProfiles").child(profileId).removeValue();
//        Log.e("click",profileId);
    }
}
