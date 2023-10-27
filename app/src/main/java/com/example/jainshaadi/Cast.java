package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cast extends AppCompatActivity {
    LinearLayout Layout1;
    LinearLayout Layout2;
    LinearLayout Layout3;
    LinearLayout Layout4;
    LinearLayout Layout5;
    TextView nexttext;
    LinearLayout nextlay;
    TextView Digamber;
    TextView Svetamber;
    String category;
    String subcategory;
    String username;
    String dates;
    String Height;

    String spinners;
    private String selectedText;
    private String Gender;
    private String Age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_cast);
        Digamber = findViewById(R.id.Digamber);
        Layout1 = findViewById(R.id.Layout1);
        Layout2 = findViewById(R.id.Layout2);
        Layout3 = findViewById(R.id.layout3);
        Layout4 = findViewById(R.id.layout4);
        Layout5 = findViewById(R.id.layout5);
        Svetamber = findViewById(R.id.Swetamber);
        nextlay = findViewById(R.id.Nextlay);
        nexttext = findViewById(R.id.Next);
        Layout3.setVisibility(View.INVISIBLE);
        Layout4.setVisibility(View.INVISIBLE);
        Layout5.setVisibility(View.INVISIBLE);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Digamber";
                String userKey = FirebaseAuth.getInstance().getUid();

                DatabaseReference userRef = databaseReference.child(userKey);
                Map<String, Object> updateData = new HashMap<>();
                updateData.put("Category", category);

                userRef.updateChildren(updateData);
                Layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Digamber.setTextColor(Color.parseColor("#FFFFFF"));
                Layout3.setVisibility(View.VISIBLE);
                Layout4.setVisibility(View.VISIBLE);
                Layout5.setVisibility(View.VISIBLE);
            }
        });

        Layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subcategory = "Shwetamber";
                String userKey = FirebaseAuth.getInstance().getUid();

                DatabaseReference userRef = databaseReference.child(userKey);
                Map<String, Object> updateData = new HashMap<>();
                updateData.put("Category", subcategory);

                userRef.updateChildren(updateData);
                Layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Svetamber.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        Spinner spin = findViewById(R.id.spinner1);
        ArrayList<String> arrayAdapter1 = new ArrayList<>();
        arrayAdapter1.add("Digamber - Agrawal");
        arrayAdapter1.add("Svetamber - Agrawal");
        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayAdapter1);
        arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin.setAdapter(arrayAdapter6);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Get the selected value from the spinner
                spinners = arrayAdapter1.get(position);
                nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                String userKey = FirebaseAuth.getInstance().getUid();

                DatabaseReference userRef = databaseReference.child(userKey);
                Map<String, Object> updateData = new HashMap<>();
                updateData.put("Subcategory", spinners);
                userRef.updateChildren(updateData);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle when nothing is selected
            }
        });


        nextlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Create a Bundle to pass data to the next activity
                Bundle bundle = new Bundle();

                bundle.putString("below category", spinners);

                bundle.putString("category", category);
                bundle.putString("subcategory", subcategory);
                bundle.putString("username", username);
                bundle.putString("Account managed by", selectedText);
                bundle.putString("Gender", Gender);
                bundle.putString("Height", Height);
                bundle.putString("DOB", dates);
                bundle.putString("Age", Age);

                // Create an Intent and add the Bundle
                Intent i = new Intent(getApplicationContext(), LocationActivity.class);
                i.putExtras(bundle);

                startActivity(i);
            }
        });

        // Retrieve data from the previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            dates = extras.getString("dates");
            Height = extras.getString("Height");
           Age= extras.getString("Age");

            selectedText=extras.getString("selectedText");

            Gender=extras.getString("Gender");
        }
    }
}
