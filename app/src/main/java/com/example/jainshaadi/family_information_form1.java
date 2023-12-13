package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class family_information_form1 extends AppCompatActivity {

    private TextInputEditText fatherNameEditText;
    private TextInputEditText fatherOccupationEditText;
    private TextInputEditText motherNameEditText;
    private TextInputEditText motherOccupationEditText;
    LinearLayout Verify;
    String fatherName = "";
    String fatherOccupation = "";
    String motherName = "";
    String motherOccupation = "";
TextView ver;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_information_form1);
        getSupportActionBar().hide();

        fatherNameEditText = findViewById(R.id.father_name_edit_text);
        fatherOccupationEditText = findViewById(R.id.father_occupation_edit_text);
        motherNameEditText = findViewById(R.id.mother_name_edit_text);
        motherOccupationEditText = findViewById(R.id.mother_occupation_edit_text);
        Verify=findViewById(R.id.Verify);
        ver=findViewById(R.id.Ver);
        fatherNameEditText.setFilters(new InputFilter[]{new NoNewlineInputFilter()});
        fatherOccupationEditText.setFilters(new InputFilter[]{new NoNewlineInputFilter()});
        motherNameEditText.setFilters(new InputFilter[]{new NoNewlineInputFilter()});
        motherOccupationEditText.setFilters(new InputFilter[]{new NoNewlineInputFilter()});

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
                }
                else
                {
                    Verify.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
                    ver.setTextColor(Color.parseColor("#FFFFFF"));
                }

            }
        });
        findViewById(R.id.Verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fatherName = fatherNameEditText.getText().toString().trim();
                fatherOccupation = fatherOccupationEditText.getText().toString().trim();
                motherName = motherNameEditText.getText().toString().trim();
                motherOccupation = motherOccupationEditText.getText().toString().trim();
                HashMap<String, Object> userData = new HashMap<>();
                userData.put("FatherName", fatherName);
                userData.put("FatherOccupation", fatherOccupation);
                userData.put("MotherName", motherName);
                userData.put("MotherOccupation", motherOccupation);
                String userKey = FirebaseAuth.getInstance().getUid();





                if (fatherName.isEmpty() || fatherOccupation.isEmpty() || motherName.isEmpty() || motherOccupation.isEmpty()) {
                    // At least one field is empty, show an error message or take appropriate action
                    // For example, display a toast message:

                    Toast.makeText(family_information_form1.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    // All fields are filled, proceed with verification
                    // Add your verification logic here
                    databaseReference.child(userKey).updateChildren(userData);
                    Intent i = new Intent(getApplicationContext(), family_information_form2.class);
                    // Add this line to attach the bundle
                    startActivity(i);
                }
            }
        });



    }
}
