package com.jainmaitri.app;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class EducationEdit extends DialogFragment {

    private TextInputLayout degreeTextInputLayout;
    private TextInputEditText degreeEditText;
    private TextInputLayout collegeTextInputLayout;
    private TextInputEditText collegeEditText;
    private TextInputLayout yearTextInputLayout;
    private TextInputEditText yearEditText;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_education, container, false);

        degreeTextInputLayout = view.findViewById(R.id.degreeTextInputLayout);
        degreeEditText = view.findViewById(R.id.degreeEditText);
        collegeTextInputLayout = view.findViewById(R.id.collegeTextInputLayout);
        collegeEditText = view.findViewById(R.id.collegeEditText);
        yearTextInputLayout = view.findViewById(R.id.yearTextInputLayout);
        yearEditText = view.findViewById(R.id.yearEditText);
        LinearLayout verifyLayout = view.findViewById(R.id.Verify);
        TextView verifyText = view.findViewById(R.id.Ver);


        yearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 4) {
                    InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(yearEditText.getWindowToken(), 0);
                    // Perform operation when the user enters a 4-digit year
                    verifyLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    verifyText.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });



        verifyLayout.setOnClickListener(v -> saveToFirebase());

        return view;
    }

    private void saveToFirebase() {
        String userKey = FirebaseAuth.getInstance().getUid();
        String degree = degreeEditText.getText().toString().trim();
        String college = collegeEditText.getText().toString().trim();
        String year = yearEditText.getText().toString().trim();

        if (!degree.isEmpty() && !college.isEmpty() && !year.isEmpty()) {
            HashMap<String, Object> userData = new HashMap<>();
            userData.put("Degree", degree);
            userData.put("College", college);
            userData.put("Year", year);

            databaseReference = FirebaseDatabase.getInstance().getReference("users");
            databaseReference.child(userKey).updateChildren(userData);
            dismiss();
        } else {
            Toast.makeText(requireContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
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
