package com.example.jainshaadi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jainshaadi.ImageActivity;
import com.example.jainshaadi.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DescribeActivity extends AppCompatActivity {
    LinearLayout Next;
    TextView nexttext;
    TextInputEditText Describe;
    TextView Count;
    boolean isNextLayoutChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_describe);

        Next = findViewById(R.id.Nextlay);
        nexttext = findViewById(R.id.Nexttext);
        Describe = findViewById(R.id.Describe);
        Count = findViewById(R.id.Count);

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

                // Disable Next button if character count exceeds 500
                if (charCount > 500) {
                    Toast.makeText(DescribeActivity.this, "Not allowed to enter more than 500 characters", Toast.LENGTH_SHORT).show();

                    // Trim the string to 500 characters
                    Describe.setText(s.subSequence(0, 500));
                    Describe.setSelection(500); // Set cursor position to the end

                    // Enable Next button after trimming
                } else {
                    Next.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    nexttext.setTextColor(Color.parseColor("#FFFFFF"));
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
               if(isNextLayoutChanged==true) {
                   String describe = Describe.getText().toString();
                   HashMap<String, Object> Describes = new HashMap<>();
                   Describes.put("Description", describe);
                   String userKey = FirebaseAuth.getInstance().getUid();
                   DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

                   // Update user data in Firebase
                   databaseReference.child(userKey).updateChildren(Describes);

                   Intent i = new Intent(getApplicationContext(), ImageActivity.class);
                   startActivity(i);
               }
               else
                   Toast.makeText(DescribeActivity.this, "All the field are mandatory", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
