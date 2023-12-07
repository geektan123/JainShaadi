package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class educational_information_form extends AppCompatActivity {


    private TextInputLayout degreeTextInputLayout;
    private TextInputEditText degreeEditText;

    private TextInputLayout collegeTextInputLayout;
    private TextInputEditText collegeEditText;

    private TextInputLayout yearTextInputLayout;
    private TextInputEditText yearEditText;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    LinearLayout Verify;
TextView Ver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.educational_information_form);
        getSupportActionBar().hide();

        // Initialize Firebase Database

        // Find views
        degreeTextInputLayout = findViewById(R.id.degreeTextInputLayout);
        degreeEditText = findViewById(R.id.degreeEditText);

        collegeTextInputLayout = findViewById(R.id.collegeTextInputLayout);
        collegeEditText = findViewById(R.id.collegeEditText);

        yearTextInputLayout = findViewById(R.id.yearTextInputLayout);
        yearEditText = findViewById(R.id.yearEditText);
        yearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 4) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(yearEditText.getWindowToken(), 0);
                    // Perform operation when the user enters a 4-digit year
                    Verify.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Ver.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }
        });
       Verify = findViewById(R.id.Verify);
       Ver=findViewById(R.id.Ver);
        Verify.setOnClickListener(view -> saveToFirebase());
    }

    private void saveToFirebase() {
        String userKey = FirebaseAuth.getInstance().getUid();
        String degree = degreeEditText.getText().toString();
        String college = collegeEditText.getText().toString();
        String year = yearEditText.getText().toString();

        if (!degree.isEmpty() && !college.isEmpty() && !year.isEmpty()) {
            HashMap<String, Object> userData = new HashMap<>();
            userData.put("Degree", degree);
            userData.put("College", college);
            userData.put("Year", year);
            databaseReference.child(userKey).updateChildren(userData);
            Intent i = new Intent(getApplicationContext(), family_information_form1.class);
            // Add this line to attach the bundle
            startActivity(i);


        } else {
            // Display an error message (you can use Toast or Snackbar)
            // For example, degreeTextInputLayout.setError("Please enter a degree");
            // Similar for college and year fields
            Toast.makeText(educational_information_form.this, "Please fill all details", Toast.LENGTH_SHORT).show();
        }
    }
}
