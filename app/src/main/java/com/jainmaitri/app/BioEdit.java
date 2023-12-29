package com.jainmaitri.app;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BioEdit extends DialogFragment {
    LinearLayout Next;
    TextView nexttext;
    TextInputEditText Describe;
    TextView Count;
    boolean isNextLayoutChanged = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_describe, container, false);

        Next = view.findViewById(R.id.Nextlay);
        nexttext = view.findViewById(R.id.Nexttext);
        Describe = view.findViewById(R.id.Describe);
        Count = view.findViewById(R.id.Count);
        Describe.setFilters(new InputFilter[]{new NoNewlineInputFilter()});

        Describe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No need to implement
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Calculate character count
                int charCount = s.length();
                Count.setText(String.valueOf(charCount));

                // Disable Next button if character count exceeds 150
                if (charCount > 150) {
                    Toast.makeText(requireContext(), "Not allowed to enter more than 150 characters", Toast.LENGTH_SHORT).show();

                    // Trim the string to 150 characters
                    Describe.setText(s.subSequence(0, 150));
                    Describe.setSelection(150); // Set cursor position to the end

                    // Enable Next button after trimming
                } else {
                    Next.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    nexttext.setTextColor(requireContext().getColor(R.color.white));
                    isNextLayoutChanged = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No need to implement
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNextLayoutChanged) {
                    String describe = Describe.getText().toString().trim();
                    if(!(describe.isEmpty())) {
                        HashMap<String, Object> Describes = new HashMap<>();
                        Describes.put("Description", describe);
                        String userKey = FirebaseAuth.getInstance().getUid();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

                        // Update user data in Firebase
                        databaseReference.child(userKey).updateChildren(Describes);

                        // Close the dialog
                        dismiss();
                    }
                    else
                    {
                        Toast.makeText(requireContext(), "All the field are mandatory", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "All the field are mandatory", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
