package com.example.jainshaadi;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
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
        Describe.setText("Hello! I'm on a quest for a life companion, someone who values genuine connections, shared laughter, and extraordinary adventures.");
        Count = findViewById(R.id.Count);
        TextView Question = findViewById(R.id.Question);
        Intent intent = getIntent();
        String gen = intent.getStringExtra("Gender");
        String acc = intent.getStringExtra("Account");
        if(acc.equals("1"))
        {
            Question.setText("Describe Yourself");
        }
        else
        {
            if(gen.equals("1"))
            {
                Question.setText("Describe About Him");
            }
            else if(gen.equals("2"))
            {
                Question.setText("Describe About Her");
            }
        }

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
                    Toast.makeText(DescribeActivity.this, "Not allowed to enter more than 150 characters", Toast.LENGTH_SHORT).show();

                    // Trim the string to 150 characters
                    Describe.setText(s.subSequence(0, 150));
                    Describe.setSelection(150); // Set cursor position to the end

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
                   if(!(describe.isEmpty())) {
                       HashMap<String, Object> Describes = new HashMap<>();
                       Describes.put("Description", describe);
                       String userKey = FirebaseAuth.getInstance().getUid();
                       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

                       // Update user data in Firebase
                       databaseReference.child(userKey).updateChildren(Describes);

                       Intent i = new Intent(getApplicationContext(), ImageActivity.class);
                       i.putExtra("Gender", gen);
                       i.putExtra("Account", acc);
                       startActivity(i);
                   }
                   else
                       Toast.makeText(DescribeActivity.this, "All the field are mandatory", Toast.LENGTH_SHORT).show();
               }
               else
                   Toast.makeText(DescribeActivity.this, "All the field are mandatory", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
