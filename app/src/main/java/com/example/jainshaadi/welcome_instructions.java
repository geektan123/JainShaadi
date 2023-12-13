package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class welcome_instructions extends AppCompatActivity {
      LinearLayout Next;
      TextView nexttext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_instructions);
        getSupportActionBar().hide();

        Next=findViewById(R.id.Nextlay);
        nexttext=findViewById(R.id.Nexttext);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Next.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));

                nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}