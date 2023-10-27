package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Name extends AppCompatActivity {
LinearLayout nextlay;
TextView nexttext;
EditText editText;
Button Next;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        getSupportActionBar().hide();

        editText=findViewById(R.id.editText);
        String txt=editText.getText().toString();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        editText.requestFocus();
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        Next=findViewById(R.id.submitButton);
        Bundle bunde = getIntent().getExtras();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String username=editText.getText().toString();
                HashMap<String, Object> userData = new HashMap<>();
                userData.put("Name", username);
                String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID


                databaseReference.child(userKey).updateChildren(userData);
                Next.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));

                Next.setTextColor(Color.parseColor("#FFFFFF"));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


                String username = editText.getText().toString();
                HashMap<String, Object> userData = new HashMap<>();
                userData.put("Name", username);
                String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID


                databaseReference.child(userKey).updateChildren(userData);

                // Create an Intent and add the Bundle
                Intent i = new Intent(getApplicationContext(), Dob.class);

                startActivity(i);
            }
        });



        }
    }

