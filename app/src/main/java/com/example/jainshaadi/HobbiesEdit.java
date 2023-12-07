package com.example.jainshaadi;


import android.app.Dialog;
import android.content.Intent;
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
    private static final int MAX_SELECTED_INTERESTS = 6;
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
        submitLinearLayout = view.findViewById(R.id.L19);
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
        addInterestLayout(R.id.L1, "Gym");
        addInterestLayout(R.id.L2, "Yoga");
        addInterestLayout(R.id.L3, "Badminton");
        addInterestLayout(R.id.L4, "Cricket");
        addInterestLayout(R.id.L5, "Chess");
        addInterestLayout(R.id.L6, "Football");
        addInterestLayout(R.id.L7, "Athletics");
        addInterestLayout(R.id.L8, "Bollywood");
        addInterestLayout(R.id.L9, "Hollywood");
        addInterestLayout(R.id.L10, "Music");
        addInterestLayout(R.id.L11, "Comedy");
        addInterestLayout(R.id.L12, "Drama");
        addInterestLayout(R.id.L13, "Traveling");
        addInterestLayout(R.id.L14, "Reading");
        addInterestLayout(R.id.L15, "Singing");
        addInterestLayout(R.id.L16, "Dancing");
        addInterestLayout(R.id.L17, "Cooking");
        addInterestLayout(R.id.L18, "Painting");
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
            submitLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
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
                Toast.makeText(requireContext(), "Select only 6 interests", Toast.LENGTH_SHORT).show();
                return;
            }
            if(getSelectedInterestCount() == (MAX_SELECTED_INTERESTS))
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
        if (getSelectedInterestCount() == MAX_SELECTED_INTERESTS) {
            int i = 1;
            // Save selected interests to the database
            for (LinearLayout layout : interestLayouts) {
                if (layout.isSelected()) {
                    String interest = interestMap.get(layout);
                    interestData.put("Interest" + i, interest);
                    i++;
                    // Save interest in the database
                }
            }
            databaseReference.child(userKey).updateChildren(interestData);
            dismiss();
            // Display a success message or navigate to the next screen
        } else {
            // Display toast for not selecting exactly 6 interests
            Toast.makeText(requireContext(), "Select exactly 6 interests", Toast.LENGTH_SHORT).show();
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