package com.example.jainshaadi;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jainshaadi.ImageActivity;
import com.example.jainshaadi.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class DescribeActivity extends AppCompatActivity {
    LinearLayout Next;
    TextView nexttext;
    TextInputEditText Describe;
    TextView Count;
    boolean isNextLayoutChanged = true;

    String[] descriptions = {
            "Balancing tradition & modernity, seeking a companion for a harmonious journey of joy and love.",
            "Exploring life's wonders with an open heart, seeking a partner for laughter, love, and shared experiences.",
            "Rooted in values, embracing new horizons. Searching for a kindred spirit for a joyful journey together.",
            "Adventurous spirit, cherishing simplicity. Seeking a life companion for shared joy, love, and growth.",
            "Harmony in contrasts, seeking a partner for a laughter-filled journey of love, understanding, and joy.",
            "Celebrating life's richness, seeking a companion for shared values, love, and meaningful connections.",
            "In the dance of tradition and modern flair, seeking a kindred soul for a journey of joy and love.",
            "Crafting a life of joy and shared moments. Seeking a partner for laughter, love, and mutual growth.",
            "Exploring life's tapestry, seeking a like-minded companion for shared joy, love, and new adventures.",
            "Savoring the simple joys, seeking a partner for a harmonious journey of love, laughter, and growth.",
            "Navigating the dance of life, seeking a partner for shared laughter, love, and meaningful connections.",
            "Adventurous at heart, seeking a companion for a journey of joy, love, and discovering life's treasures.",
            "Embracing the rhythm of tradition, seeking a partner for shared values, love, and a joyful life together.",
            "In pursuit of a harmonious life, seeking a kindred spirit for shared joy, love, and growth-filled adventures.",
            "Rooted in authenticity, seeking a companion for a journey of laughter, love, and genuine connections.",
            "Crafting a tapestry of love and joy, seeking a partner for shared values, understanding, and new experiences.",
            "Journeying through the symphony of life, seeking a kindred soul for shared joy, love, and harmonious living.",
            "Finding beauty in simplicity, seeking a companion for a laughter-filled journey of love and shared moments.",
            "Exploring life's mosaic, seeking a partner for shared joy, love, and the artistry of mutual understanding.",
            "Adventurous and grounded, seeking a life companion for a journey of love, laughter, and shared growth.",
            "Balancing tradition & innovation, seeking a partner for a harmonious journey of joy, love, and new beginnings.",
            "Cultivating joy in everyday moments, seeking a kindred spirit for shared love, laughter, and meaningful connections.",
            "In the tapestry of life's journey, seeking a companion for shared joy, love, and the richness of mutual understanding.",
            "Savoring the art of connection, seeking a partner for a laughter-filled journey of love and shared experiences.",
            "Embarking on a life adventure, seeking a kindred soul for shared joy, love, and the beauty of harmonious living.",
            "In the melody of life, seeking a companion for a harmonious journey of joy, love, and shared moments.",
            "Exploring the canvas of life, seeking a partner for shared joy, love, and the artistry of mutual understanding.",
            "Balancing the yin and yang, seeking a kindred spirit for a journey of joy, love, and harmonious living.",
            "In the kaleidoscope of life's moments, seeking a companion for shared joy, love, and meaningful connections.",
            "Harvesting happiness, seeking a partner for a laughter-filled journey of love and shared experiences.",
            "Savoring life's palette, seeking a kindred soul for shared joy, love, and the richness of harmonious living.",
            "In the rhythm of life's journey, seeking a companion for shared joy, love, and the dance of understanding.",
            "Crafting a life masterpiece, seeking a partner for a laughter-filled journey of love and shared growth.",
            "Waltzing through life's chapters, seeking a kindred spirit for shared joy, love, and the art of harmonious living.",
            "Embarking on a serendipitous journey, seeking a companion for shared joy, love, and the beauty of mutual understanding.",
            "In the mosaic of life, seeking a partner for a harmonious journey of joy, love, and shared moments.",
            "Balancing life's equations, seeking a kindred soul for shared joy, love, and the simplicity of harmonious living.",
            "Exploring the poetry of life, seeking a companion for a laughter-filled journey of love and shared experiences.",
            "Navigating the labyrinths of joy, seeking a partner for shared love, laughter, and the artistry of mutual understanding.",
            "Harmonizing with life's melodies, seeking a kindred spirit for a journey of joy, love, and harmonious living.",
            "In the heart's symphony, seeking a companion for shared joy, love, and the dance of mutual understanding.",
            "Painting a canvas of happiness, seeking a partner for a laughter-filled journey of love and shared growth.",
            "Choreographing life's dance, seeking a kindred soul for shared joy, love, and the poetry of harmonious living.",
            "In the tapestry of existence, seeking a companion for shared joy, love, and the richness of mutual understanding.",
            "Balancing on life's tightrope, seeking a partner for a harmonious journey of joy, love, and shared moments.",
            "In the kaleidoscope of shared experiences, seeking a kindred spirit for joy, love, and the artistry of harmonious living.",
            "Savoring life's flavors, seeking a companion for a laughter-filled journey of love and the richness of shared growth.",
            "Embarking on a serendipitous path, seeking a partner for shared joy, love, and the beauty of mutual understanding.",
            "In the dance of life's moments, seeking a kindred soul for a journey of joy, love, and the simplicity of harmonious living.",
            "Harmonizing with the heartbeat of life, seeking a companion for shared joy, love, and the poetry of mutual understanding.",
            "Harmonizing with the heartbeat of life, seeking a companion for shared joy, love, and the poetry of mutual understanding."
    };

// Use the descriptions array as needed in your code


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.action_regestration);
        ImageView BackButton = actionBar.getCustomView().findViewById(R.id.logo);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for the logo
                onBackPressed();
            }
        });
        setContentView(R.layout.activity_describe);
        Next = findViewById(R.id.Nextlay);
        Next.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
        nexttext = findViewById(R.id.Nexttext);
        Describe = findViewById(R.id.Describe);
        Random random = new Random();
        // Generate a random number between 0 and 49 (inclusive)
        int randomNumber = random.nextInt(50);
        Describe.setFilters(new InputFilter[]{new NoNewlineInputFilter()});
        Describe.setText(descriptions[randomNumber]);
        Describe.requestFocus();
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
                   String describe = Describe.getText().toString().trim();
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
