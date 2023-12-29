package com.jainmaitri.app;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HobbiesEdit extends DialogFragment {
    private static final int MAX_SELECTED_INTERESTS = 12;
    private static final int MIN_SELECTED_INTEREST = 6;
    private ArrayList<LinearLayout> interestLayouts = new ArrayList<>();

    private Map<LinearLayout, String> interestMap = new HashMap<>();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    String userKey = FirebaseAuth.getInstance().getUid();
    HashMap<String, Object> interestData = new HashMap<>();
    LinearLayout submitLinearLayout;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_edit_interest , container, false);
        initializeInterestLayouts();
        submitLinearLayout = view.findViewById(R.id.submit);
        submitLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call your onSubmit method or perform any action you want here
                onSubmit(v);
            }
        });
        return view;
    }
    private void initializeInterestLayouts() {
        // Add all interest layouts to the list and map
        // Religious & Lifestyle
        addInterestLayout(R.id.L1, "Spiritualist");
        addInterestLayout(R.id.L2, "Traditionalist");
        addInterestLayout(R.id.L3, "ModernJain");
        addInterestLayout(R.id.L4, "Astrology");
        addInterestLayout(R.id.L5, "CommunityLeader");
        addInterestLayout(R.id.L6, "FamilyFirst");
        addInterestLayout(R.id.L7, "ValuesDriven");
        addInterestLayout(R.id.L8, "NomadicLifestyle");
        addInterestLayout(R.id.L9, "TempleGoer");
        addInterestLayout(R.id.L10, "JainDiet");

// Cultural & Art
        addInterestLayout(R.id.L11, "HistoryEnthusiast");
        addInterestLayout(R.id.L12, "MusicLover");
        addInterestLayout(R.id.L13, "Film");
        addInterestLayout(R.id.L14, "Bollywood");
        addInterestLayout(R.id.L15, "Hollywood");
        addInterestLayout(R.id.L16, "Tollywood");
        addInterestLayout(R.id.L17, "Theater");

// Literary & Learning
        addInterestLayout(R.id.L18, "SanskritAdmirer");
        addInterestLayout(R.id.L19, "Bookworm");
        addInterestLayout(R.id.L20, "HistoricalReader");
        addInterestLayout(R.id.L21, "InquisitiveMind");
        addInterestLayout(R.id.L22, "Linguaphile");

// Health & Wellness
        addInterestLayout(R.id.L23, "WellnessFan");
        addInterestLayout(R.id.L24, "FitnessFreak");
        addInterestLayout(R.id.L25, "Yoga");
        addInterestLayout(R.id.L26, "NatureLover");
        addInterestLayout(R.id.L27, "HealthConscious");
        addInterestLayout(R.id.L28, "Meditator");

// Technology & Trends
        addInterestLayout(R.id.L29, "TechSavvy");
        addInterestLayout(R.id.L30, "Instagram");
        addInterestLayout(R.id.L31, "Reels");
        addInterestLayout(R.id.L32, "GadgetGeek");

// Hobbies & Interests
        addInterestLayout(R.id.L33, "Foodie");
        addInterestLayout(R.id.L34, "TeaLover");
        addInterestLayout(R.id.L35, "Traveler");
        addInterestLayout(R.id.L36, "Photography");
        addInterestLayout(R.id.L37, "AnimalLover");
        addInterestLayout(R.id.L38, "DIYer");
        addInterestLayout(R.id.L39, "Gamer");
        addInterestLayout(R.id.L40, "CreativeMind");
        addInterestLayout(R.id.L41, "NaturePhotographer");
        addInterestLayout(R.id.L42, "ArtisanalCrafts");
        addInterestLayout(R.id.L43, "CoffeeAficionado");
        addInterestLayout(R.id.L44, "Astrophotography");
        addInterestLayout(R.id.L45, "Melomaniac");

// Sports & Activities
        addInterestLayout(R.id.L46, "Cricket");
        addInterestLayout(R.id.L47, "Football");
        addInterestLayout(R.id.L48, "Badminton");
        addInterestLayout(R.id.L49, "Tennis");
        addInterestLayout(R.id.L50, "Basketball");
        addInterestLayout(R.id.L51, "Hiking");
        addInterestLayout(R.id.L52, "Swimming");
        addInterestLayout(R.id.L53, "Running");
        addInterestLayout(R.id.L54, "Yoga");
        addInterestLayout(R.id.L55, "Cycling");
        addInterestLayout(R.id.L56, "Golf");
        addInterestLayout(R.id.L57, "TableTennis");
        addInterestLayout(R.id.L58, "Chess");
        addInterestLayout(R.id.L59, "Volleyball");
        addInterestLayout(R.id.L60, "Kabaddi");
        addInterestLayout(R.id.L61, "Wrestling");
        addInterestLayout(R.id.L62, "Boxing");
        addInterestLayout(R.id.L63, "MartialArts");

// Fashion & Lifestyle
        addInterestLayout(R.id.L64, "Fashionista");
        addInterestLayout(R.id.L65, "SimpleLiving");
        addInterestLayout(R.id.L66, "Minimalist");
        addInterestLayout(R.id.L67, "Trendsetter");

// Career & Education
        addInterestLayout(R.id.L68, "Scholarly");
        addInterestLayout(R.id.L69, "CareerFocused");
        addInterestLayout(R.id.L70, "Entrepreneurial");
        addInterestLayout(R.id.L71, "Innovator");
        addInterestLayout(R.id.L72, "DigitalArtist");
        addInterestLayout(R.id.L73, "Counselor");

// Family & Relationship
        addInterestLayout(R.id.L74, "FamilyOriented");
        addInterestLayout(R.id.L75, "Parenting");
        addInterestLayout(R.id.L76, "Romantic");

// Values & Mindfulness
        addInterestLayout(R.id.L77, "Generous");
        addInterestLayout(R.id.L78, "Ethical");
        addInterestLayout(R.id.L79, "Mindful");
        addInterestLayout(R.id.L80, "Peaceful");
        addInterestLayout(R.id.L81, "ValuesFirst");
        addInterestLayout(R.id.L82, "SpiritualSeeker");
        addInterestLayout(R.id.L83, "Progressive");

// Social & Philanthropy
        addInterestLayout(R.id.L84, "Socialite");
        addInterestLayout(R.id.L85, "Philanthropist");
        addInterestLayout(R.id.L86, "EnvironmentalActivist");
        addInterestLayout(R.id.L87, "CommunityContributor");
        addInterestLayout(R.id.L88, "SocialEntrepreneur");
        // Add the rest of the interest layouts

        // Add click listeners to interest layouts
        for (LinearLayout layout : interestLayouts) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onInterestSelected(view);
                }
            });
        }
    }



    private void addInterestLayout(int layoutId, String interest ) {
        LinearLayout interestLayout = view.findViewById(layoutId);
        interestLayouts.add(interestLayout);
        interestMap.put(interestLayout, interest);
    }

    public void onInterestSelected(View view) {
        LinearLayout interestLayout = (LinearLayout) view;

        if (interestLayout.isSelected()) {
            // Deselect interest
            interestLayout.setSelected(false);
            interestLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
            TextView textView = (TextView) interestLayout.getChildAt(0);
            textView.setTextColor(Color.parseColor("#756568"));
            if(getSelectedInterestCount() < MIN_SELECTED_INTEREST) {
                submitLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
            }
//            nexttext.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            // Select interest
            if (getSelectedInterestCount() < MAX_SELECTED_INTERESTS) {
                interestLayout.setSelected(true);
                interestLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                TextView textView = (TextView) interestLayout.getChildAt(0);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                // Display toast for selecting more than 6 interests
                Toast.makeText(requireContext(), "Select Between 6 to 12 Interest", Toast.LENGTH_SHORT).show();
                return;
            }
            if(getSelectedInterestCount() == (MIN_SELECTED_INTEREST))
            {
                submitLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
//                nexttext.setTextColor(Color.parseColor("#FFFFFF"));
            }
        }
    }

    private int getSelectedInterestCount() {
        int count = 0;
        for (LinearLayout layout : interestLayouts) {
            if (layout.isSelected()) {
                count++;
            }
        }
        return count;
    }

    public void onSubmit(View view) {
        ArrayList<String> more = new ArrayList<>();
        if (getSelectedInterestCount() <= MAX_SELECTED_INTERESTS && getSelectedInterestCount()>= MIN_SELECTED_INTEREST) {
            int i = 1;
            // Save selected interests to the database
            for (LinearLayout layout : interestLayouts) {
                if (layout.isSelected()) {
                    if(i <= 6) {
                        String interest = interestMap.get(layout);
                        interestData.put("Interest" + i, interest);
                        i++;
                    }
                    else {
                        String interest = interestMap.get(layout);
                        more.add(interest);
                    }
                    // Save interest in the database
                }
            }
            interestData.put("More_Interest" , more);
            databaseReference.child(userKey).updateChildren(interestData)
                    .addOnSuccessListener(aVoid -> {
                        // Success
                        dismiss();
                    })
                    .addOnFailureListener(e -> {
                        // Handle failure
                        Toast.makeText(requireContext(), "Failed to update interests", Toast.LENGTH_SHORT).show();
                    });
            // Display a success message or navigate to the next screen
        } else {
            // Display toast for not selecting exactly 6 interests
            Toast.makeText(requireContext(), "Select Between 6 to 12 interests", Toast.LENGTH_SHORT).show();
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);


        // Set the size and margins of the dialog
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;


        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setAttributes(params);


        return dialog;
    }
}