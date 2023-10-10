package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Name extends AppCompatActivity {
LinearLayout nextlay;
TextView nexttext;
EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        getSupportActionBar().hide();
        nextlay=findViewById(R.id.Nextlay);
        nexttext=findViewById(R.id.Nexttext);
        editText=findViewById(R.id.editText);
        String txt=editText.getText().toString();

        nextlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));

                nexttext.setTextColor(Color.parseColor("#FFFFFF"));

                Intent i = new Intent(getApplicationContext(), Dob.class);
                startActivity(i);

            }
        });
    }
}