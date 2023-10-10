package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Interest extends AppCompatActivity {
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    LinearLayout layout4;
    LinearLayout layout5;
    LinearLayout layout6;
    LinearLayout layout7;
    LinearLayout layout8;
    LinearLayout layout9;
    LinearLayout layout10;
    LinearLayout layout11;
    LinearLayout layout12;
    LinearLayout layout13;
    LinearLayout layout14;
    LinearLayout layout15;
    LinearLayout layout16;
    LinearLayout layout17;
    LinearLayout layout18;
    LinearLayout layout19;
    TextView GYM;
    TextView YOGA;
    TextView Badminton;
    TextView Cricket;
    TextView Chess;
    TextView Football;
    TextView Athletics;
    TextView Bollywood;
    TextView Hollywood;
    TextView Music;
    TextView Comedy;
    TextView Drama;
    TextView travelling;
    TextView Reading;
    TextView Singing;
    TextView Dancing;
    TextView Cooking;
    TextView Painting;
TextView Next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_interest);
        layout1 = findViewById(R.id.L1);
        layout2 = findViewById(R.id.L2);
        layout3 = findViewById(R.id.L3);
        layout4 = findViewById(R.id.L4);
        layout5 = findViewById(R.id.L5);
        layout6 = findViewById(R.id.L6);
        layout7 = findViewById(R.id.L7);
        layout8 = findViewById(R.id.L8);
        layout9 = findViewById(R.id.L9);
        layout10 = findViewById(R.id.L10);
        layout11 = findViewById(R.id.L11);
        layout12 = findViewById(R.id.L12);
        layout13 = findViewById(R.id.L13);
        layout14 = findViewById(R.id.L14);
        layout15 = findViewById(R.id.L15);
        layout16 = findViewById(R.id.L16);
        layout17 = findViewById(R.id.L17);
        layout18=findViewById(R.id.L18);
        layout19 = findViewById(R.id.L19);
        GYM = findViewById(R.id.Gym);
        YOGA = findViewById(R.id.yoga);
        Badminton = findViewById(R.id.badminton);
        Cricket = findViewById(R.id.cricket);
        Chess = findViewById(R.id.chess);
        Football = findViewById(R.id.football);
        Athletics = findViewById(R.id.Athletic);
        Bollywood = findViewById(R.id.Bollywood);
        Music = findViewById(R.id.Music);
        Hollywood = findViewById(R.id.Hollywood);
        Comedy = findViewById(R.id.Comedy);
        Drama = findViewById(R.id.Drama);
        travelling = findViewById(R.id.travelling);
        Reading = findViewById(R.id.Reading);
        Singing = findViewById(R.id.Singing);
        Dancing = findViewById(R.id.Dancing);
        Cooking = findViewById(R.id.Cooking);
        Painting = findViewById(R.id.Painting);
        Next=findViewById(R.id.Next);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                GYM.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                YOGA.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout3.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Badminton.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout4.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Cricket.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout5.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Chess.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout6.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Football.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout7.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Athletics.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout8.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Bollywood.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout9.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Hollywood.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout10.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Music.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout11.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Comedy.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout12.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Drama.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
      layout13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout13.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                travelling.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout14.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Reading.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout15.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Singing.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout16.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Dancing.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout17.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Cooking.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
        layout18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout18.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Painting.setTextColor(Color.parseColor("#FFFFFF"));


            }
        });
 layout19.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         layout19.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
         Next.setTextColor(Color.parseColor("#FFFFFF"));
        /* Intent i = new Intent(getApplicationContext(),welcome_instructions.class);
         startActivity(i);*/


     }
 });

    }
}



