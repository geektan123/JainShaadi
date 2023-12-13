package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.style.QuoteSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Name extends AppCompatActivity {
    LinearLayout nextlay;
    TextView nexttext;
    TextView Question;
    EditText editText;
    Button Next;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    private boolean isNameEntered = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        getSupportActionBar().hide();

        String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID
        Log.e("tag","not myself1");
        Question = findViewById(R.id.Question);
        Intent intent = getIntent();
        String gen = intent.getStringExtra("Gender");
        String acc = intent.getStringExtra("Account");
        if(acc.equals("1"))
        {
            Question.setText("What's Your Name ?");
        }
        else
        {
            if(gen.equals("1"))
            {
                Question.setText("What's His Name ?");
            }
            else if(gen.equals("2"))
            {
                Question.setText("What's Her Name ?");
            }
        }
//        Profile_for = databaseReference.child(userKey).child("Gender").getValue(String.class);
        editText = findViewById(R.id.editText);
        editText.setFilters(new InputFilter[]{new NoNewlineInputFilter()});
        String txt = editText.getText().toString();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        editText.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        Next = findViewById(R.id.submitButton);
        Bundle bunde = getIntent().getExtras();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String username = editText.getText().toString();
                if (!username.isEmpty()) {
                    Next.setEnabled(true); // Enable the Next button when a name is entered
                    Next.setBackgroundResource(R.drawable.rounded_card_background_enabled);
                    Next.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    Next.setBackgroundResource(R.drawable.rounded_card_background_next_disabled);
                    Next.setEnabled(false); // Disable the Next button if the name is empty
                }
                HashMap<String, Object> userData = new HashMap<>();
                userData.put("Name", username);
                String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID

//                Next.setBackgroundResource(R.drawable.rounded_card_background_enabled);

                Next.setTextColor(Color.parseColor("#FFFFFF"));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editText.getText().toString().trim();

                if (!username.isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    HashMap<String, Object> userData = new HashMap<>();
                    userData.put("Name", username);
                    String userKey = FirebaseAuth.getInstance().getUid();
                    databaseReference.child(userKey).updateChildren(userData);

                    Intent i = new Intent(getApplicationContext(), Dob.class);
                    i.putExtra("Gender",gen);
                    i.putExtra("Account",acc);
                    startActivity(i);
                } else {
                    Toast.makeText(Name.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}