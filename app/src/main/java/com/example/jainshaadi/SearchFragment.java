package com.example.jainshaadi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {
    private String currentUserId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve currentUserId from arguments or Intent, depending on how you pass it
        if (getArguments() != null) {
            currentUserId = getArguments().getString("currentUserId");
        } else {
            Intent intent = getActivity().getIntent();
            if (intent != null) {
                currentUserId = intent.getStringExtra("currentUserId");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);

        LinearLayout search1 = view.findViewById(R.id.digamber);

        LinearLayout StartNow = view.findViewById(R.id.start_now);
        StartNow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                // Redirect to SavedProfilesActivity
                Intent intent = new Intent(requireContext(), MyProfile.class);
                startActivity(intent);
            }
        });
        search1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openSearchFilter("Digambar");
            }
        });

        LinearLayout search2 = view.findViewById(R.id.shwetamber);
        search2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openSearchFilter("Shvetambar");
            }
        });

        LinearLayout search3 = view.findViewById(R.id.location);
        search3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openSearchFilter("Location");
            }
        });

        LinearLayout search4 = view.findViewById(R.id.interest);
        search4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openSearchFilter("Age");
            }
        });

        return view;
    }

    private void openSearchFilter(String filterType) {
        Intent intent = new Intent(getActivity(), SearchFilter.class);
        intent.putExtra("currentUserId", currentUserId);
        intent.putExtra("filterType", filterType);
        startActivity(intent);
    }
}
