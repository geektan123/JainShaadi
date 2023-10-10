package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {
Spinner spin;
LinearLayout NextLay;
TextView Nexttext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_location);
        spin=findViewById(R.id.spinner1);
        ArrayList<String> arrayAdapter1=new ArrayList<>();
        arrayAdapter1.add("Madhya Pradesh");
        arrayAdapter1.add("Uttar Pradesh");
        arrayAdapter1.add("Uttarakand");
        arrayAdapter1.add("New Delhi");
        arrayAdapter1.add("Rajasthan");
        arrayAdapter1.add("Hariyana");



        ArrayAdapter<String> arrayAdapter6=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayAdapter1);
        arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin.setAdapter(arrayAdapter6);
        NextLay=findViewById(R.id.Nextlay);
        Nexttext=findViewById(R.id.Nexttext);
        NextLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NextLay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));

                Nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                Intent i = new Intent(getApplicationContext(), Interest.class);
                startActivity(i);

            }

        });
    }
}