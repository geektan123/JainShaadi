package com.example.jainshaadi;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class WorkEdit extends DialogFragment {

    boolean isNextLayoutChanged = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_income, container, false);

        LinearLayout Next = view.findViewById(R.id.Nextlay);
        EditText Role = view.findViewById(R.id.Role);
        EditText Company = view.findViewById(R.id.Company);
        TextView nexttext = view.findViewById(R.id.Nexttext);
        Spinner spin = view.findViewById(R.id.spinner1);
        Spinner spin1 = view.findViewById(R.id.spinner2);
        Role.setFilters(new InputFilter[]{new NoNewlineInputFilter()});
        Company.setFilters(new InputFilter[]{new NoNewlineInputFilter()});

        ArrayList<String> arrayAdapter1 = new ArrayList<>();
        arrayAdapter1.add("-Select-");
        arrayAdapter1.add("Private Company");
        arrayAdapter1.add("Government Service");
        arrayAdapter1.add("Business");
        arrayAdapter1.add("Self Employed");
        arrayAdapter1.add("NA");
        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, arrayAdapter1);
        arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin.setAdapter(arrayAdapter6);

        ArrayList<String> arrayAdapter2 = new ArrayList<>();
        arrayAdapter2.add("-Select-");
        arrayAdapter2.add("INR 0-3 Lakh per annum");
        arrayAdapter2.add("INR 3-5 Lakh per annum");
        arrayAdapter2.add("INR 5-10 Lakh per annum");
        arrayAdapter2.add("INR 10-15 Lakh per annum");
        arrayAdapter2.add("INR 15-25 Lakh per annum");
        arrayAdapter2.add("INR 25-40 Lakh per annum");
        arrayAdapter2.add("INR 40+ Lakh per annum");
        arrayAdapter2.add("NA");
        ArrayAdapter<String> arrayAdapter7 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, arrayAdapter2);
        arrayAdapter7.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin1.setAdapter(arrayAdapter7);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spin1.getSelectedItemPosition() > 0) {
                    Next.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                    isNextLayoutChanged = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when nothing is selected
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = Role.getText().toString().trim();
                String text1 = Company.getText().toString().trim();
                String spinner1Value = spin.getSelectedItem().toString();
                String spinner2Value = spin1.getSelectedItem().toString();

                if (!text.isEmpty() && !text1.isEmpty() && spin.getSelectedItemPosition() > 0 && spin1.getSelectedItemPosition() > 0 && isNextLayoutChanged) {
                    Next.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                    HashMap<String, Object> incomeData = new HashMap<>();
                    incomeData.put("IncomeType", spinner1Value);
                    incomeData.put("IncomeRange", spinner2Value);
                    incomeData.put("Role", text);
                    incomeData.put("Company", text1);
                    String userKey = FirebaseAuth.getInstance().getUid();

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                    databaseReference.child(userKey).updateChildren(incomeData);

                    dismiss();
                } else {
                    Toast.makeText(requireContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
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
