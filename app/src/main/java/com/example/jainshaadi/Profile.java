package com.example.jainshaadi;

import static androidx.core.view.ViewCompat.setBackground;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Profile extends AppCompatActivity {

    boolean clickedmyself = false;
    boolean clickedmyson = false;
    boolean clickedmybrother = false;
    boolean clickedmydaughter = false;


    TextView myself;
    TextView myson;
    TextView mybro;
    TextView mydaughter;
    TextView mysister;
    TextView myrelative;
    TextView male;
    TextView female;
    TextView next;
    LinearLayout layout;
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    LinearLayout layout4;
    LinearLayout layout5;
    LinearLayout layoutgayab;
    LinearLayout layoutmale;
    LinearLayout layoutfemale;
    LinearLayout linearnext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_profile);
        this.setTitle("Create Profile For");
        myself = (TextView) findViewById(R.id.myself);
        myson = findViewById(R.id.Son);
        mybro = findViewById(R.id.Brother);
        mydaughter = findViewById(R.id.Daughter);
        mysister = findViewById(R.id.Sister);
        myrelative = findViewById(R.id.Relative);
        male = findViewById(R.id.male);
        female = findViewById(R.id.Female);
        layout = (LinearLayout) findViewById(R.id.layout);
        layoutgayab = findViewById(R.id.Layoutgayab);
        layout1 = findViewById(R.id.Layout1);
        layout2 = findViewById(R.id.Layout2);
        layout3 = findViewById(R.id.Layout3);
        layout4 = findViewById(R.id.Layout4);
        layout5 = findViewById(R.id.Layout5);
        layoutmale = findViewById(R.id.layoutmale);
        layoutfemale = findViewById(R.id.layoutfemale);
        next = findViewById(R.id.Next);
        linearnext = findViewById(R.id.Layoutnext);
        //my button clic

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //change boolean value

                layout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                myself.setTextColor(Color.parseColor("#FFFFFF"));
                layoutgayab.setVisibility(v.VISIBLE);


            }
        });

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                myson.setTextColor(Color.parseColor("#FFFFFF"));
                layoutgayab.setVisibility(view.INVISIBLE);

            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                mybro.setTextColor(Color.parseColor("#FFFFFF"));
                layoutgayab.setVisibility(view.INVISIBLE);



            }
        });
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout3.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                mydaughter.setTextColor(Color.parseColor("#FFFFFF"));
                layoutgayab.setVisibility(view.INVISIBLE);




            }
        });
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout4.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                mysister.setTextColor(Color.parseColor("#FFFFFF"));
                layoutgayab.setVisibility(view.INVISIBLE);




            }
        });
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout5.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                myrelative.setTextColor(Color.parseColor("#FFFFFF"));
                layoutgayab.setVisibility(view.VISIBLE);


            }
        });
        layoutmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutmale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                male.setTextColor(Color.parseColor("#FFFFFF"));



            }
        });
        layoutfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutfemale.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                female.setTextColor(Color.parseColor("#FFFFFF"));




            }
        });
        linearnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearnext.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                next.setTextColor(Color.parseColor("#FFFFFF"));
                Intent i = new Intent(getApplicationContext(), Name.class);
                startActivity(i);

            }
        });

    }
}