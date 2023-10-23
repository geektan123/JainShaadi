package com.example.jainshaadi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    private LinearLayout selectedLinearLayout = null;

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
        layout = findViewById(R.id.layout);
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
        layoutgayab.setVisibility(View.INVISIBLE);
        linearnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Name.class);
                startActivity(i);
            }
        });

        setLinearLayoutOnClickListener(layout, myself, false);
        setLinearLayoutOnClickListener(layout1, myson, false);
        setLinearLayoutOnClickListener(layout2, mybro, false);
        setLinearLayoutOnClickListener(layout3, mydaughter, false);
        setLinearLayoutOnClickListener(layout4, mysister, false);
        setLinearLayoutOnClickListener(layout5, myrelative, false);

        layoutmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSelection(layoutmale, male, true);
            }
        });

        layoutfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSelection(layoutfemale, female, true);
            }
        });


    }

    private void setLinearLayoutOnClickListener(final LinearLayout linearLayout, final TextView textView, final boolean isMaleOrFemale) {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linearLayout == selectedLinearLayout && !isMaleOrFemale) {
                    // If the same layout is clicked again and it's not male or female, deselect it
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    textView.setTextColor(Color.parseColor("#756568"));
                    selectedLinearLayout = null;
                } else {
                    // Deselect the previously selected layout (if any)
                    if (selectedLinearLayout != null) {
                        if (!isMaleOrFemale) {
                            selectedLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                            TextView selectedTextView = (TextView) selectedLinearLayout.getChildAt(0);
                            selectedTextView.setTextColor(Color.parseColor("#756568"));
                        }
                    }

                    // Select the clicked layout
                    if (isMaleOrFemale) {
                        linearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                        textView.setTextColor(Color.parseColor("#FFFFFF"));
                        selectedLinearLayout = linearLayout;

                    } else {
                        linearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                        textView.setTextColor(Color.parseColor("#FFFFFF"));
                        selectedLinearLayout = linearLayout;
                    }

                    // Show/hide layoutgayab based on the selected layout
                    if (linearLayout == layout || linearLayout == layout5) {
                        layoutgayab.setVisibility(View.VISIBLE);
                    } else {
                        layoutgayab.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    private void handleSelection(LinearLayout linearLayout, TextView textView, boolean isMaleOrFemale) {
        if (linearLayout == selectedLinearLayout && !isMaleOrFemale) {
            // If the same layout is clicked again and it's not male or female, deselect it
            linearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
            textView.setTextColor(Color.parseColor("#756568"));
            selectedLinearLayout = null;
        } else {
            // Deselect the previously selected layout (if any)
            if (selectedLinearLayout != null) {
                if (!isMaleOrFemale) {
                    selectedLinearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    TextView selectedTextView = (TextView) selectedLinearLayout.getChildAt(0);
                    selectedTextView.setTextColor(Color.parseColor("#756568"));
                }

                // Select the clicked layout
                if (isMaleOrFemale) {
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    selectedLinearLayout = linearLayout;
                } else {
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    selectedLinearLayout = linearLayout;
                }

            }

        }


    }
}

