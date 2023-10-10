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

public class Cast extends AppCompatActivity {
LinearLayout Layout1;
LinearLayout Layout2;
TextView nexttext;
LinearLayout nextlay;
TextView Digamber;
    TextView Svetamber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_cast);
        Digamber=findViewById(R.id.Digamber);
        Layout1=findViewById(R.id.Layout1);
        Layout2=findViewById(R.id.Layout2);
        Svetamber=findViewById(R.id.Swetamber);
        nextlay=findViewById(R.id.Nextlay);
        nexttext=findViewById(R.id.Next);

        Layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));

                Digamber.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        Layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));

                Svetamber.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        Spinner spin=findViewById(R.id.spinner1);
        ArrayList<String> arrayAdapter1=new ArrayList<>();
        arrayAdapter1.add("Digamber - Agrawal");
        ArrayAdapter<String> arrayAdapter6=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayAdapter1);
        arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin.setAdapter(arrayAdapter6);
        nextlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));

                nexttext.setTextColor(Color.parseColor("#FFFFFF"));

                Intent i = new Intent(getApplicationContext(), IncomeActivity.class);
                startActivity(i);

            }
        });

    }
}