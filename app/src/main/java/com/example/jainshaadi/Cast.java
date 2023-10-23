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

import java.util.ArrayList;

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
    String spinnerValue1;
    String spinnerValue2;
    String spinners;


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

        Layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = "Digamber";
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
                Layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Svetamber.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        Spinner spin = findViewById(R.id.spinner1);
        ArrayList<String> arrayAdapter1 = new ArrayList<>();
        arrayAdapter1.add("Digamber - Agrawal");
        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayAdapter1);
        arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin.setAdapter(arrayAdapter6);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Get the selected value from the spinner
                spinners = arrayAdapter1.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle when nothing is selected
            }
        });


        nextlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                nexttext.setTextColor(Color.parseColor("#FFFFFF"));

                // Create a Bundle to pass data to the next activity
                Bundle dataBundle = new Bundle();

                dataBundle.putString("below category", spinners);

                dataBundle.putString("category", category);
                dataBundle.putString("subcategory", subcategory);
                dataBundle.putString("username", username);
                dataBundle.putString("dates", dates);
                dataBundle.putString("spinnerValue1", spinnerValue1);
                dataBundle.putString("spinnerValue2", spinnerValue2);

                // Create an Intent and add the Bundle
                Intent i = new Intent(getApplicationContext(), LocationActivity.class);
                i.putExtras(dataBundle);

                startActivity(i);
            }
        });

        // Retrieve data from the previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            dates = extras.getString("dates");
            spinnerValue1 = extras.getString("spinnerValue1");
            spinnerValue2 = extras.getString("spinnerValue2");
        }
    }
}
