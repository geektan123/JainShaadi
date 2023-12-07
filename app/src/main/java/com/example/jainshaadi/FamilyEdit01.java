package com.example.jainshaadi;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FamilyEdit01 extends DialogFragment {

    private TextInputEditText fatherNameEditText;
    private TextInputEditText fatherOccupationEditText;
    private TextInputEditText motherNameEditText;
    private TextInputEditText motherOccupationEditText;
    private LinearLayout Verify;
    private TextView ver;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_family, container, false);

        fatherNameEditText = view.findViewById(R.id.father_name_edit_text);
        fatherOccupationEditText = view.findViewById(R.id.father_occupation_edit_text);
        motherNameEditText = view.findViewById(R.id.mother_name_edit_text);
        motherOccupationEditText = view.findViewById(R.id.mother_occupation_edit_text);
        Verify = view.findViewById(R.id.Verify);
        ver = view.findViewById(R.id.Ver);

        motherOccupationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    Verify.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    ver.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    Verify.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
                    ver.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });

        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirebase();
            }
        });

        return view;
    }

    private void saveToFirebase() {
        String fatherName = fatherNameEditText.getText().toString();
        String fatherOccupation = fatherOccupationEditText.getText().toString();
        String motherName = motherNameEditText.getText().toString();
        String motherOccupation = motherOccupationEditText.getText().toString();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        String userKey = FirebaseAuth.getInstance().getUid();

        if (fatherName.isEmpty() || fatherOccupation.isEmpty() || motherName.isEmpty() || motherOccupation.isEmpty()) {
            Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> userData = new HashMap<>();
            userData.put("FatherName", fatherName);
            userData.put("FatherOccupation", fatherOccupation);
            userData.put("MotherName", motherName);
            userData.put("MotherOccupation", motherOccupation);

            databaseReference.child(userKey).updateChildren(userData);
            dismiss();
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
