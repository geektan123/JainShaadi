package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Interest extends AppCompatActivity {
    int i = 0;
    String interest1;
    String interest2;
    String interest3;
    String interest4;
    String interest5;
    String interest6;
    String interest7;
    String interest8;
    String interest9;
    String interest10;
    String interest11;
    String interest12;
    String interest13;
    String interest14;
    String interest15;
    String interest16;
    String interest17;
    String interest18;
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
    private String username;
    private String dates;
    private String spinners;
    private String category;
    private String subcategory;
    private String Height;
    private String Age;
    private String selectedText;
    private String Gender;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    HashMap<String, Object> interestData = new HashMap<>();

    int selectedCount = 0;

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
        layout18 = findViewById(R.id.L18);
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
        Next = findViewById(R.id.Next);
        Bundle bundle = new Bundle();

        // ... (previous code)

        // ... (previous code)

        // Add this variable at the beginning of your class

// ...

// For each layout, add the following click listener:

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    GYM.setTextColor(Color.parseColor("#FFFFFF"));
                    interest1 = GYM.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest1);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Add this variable at the beginning of your class

// ...

// For layout2
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    YOGA.setTextColor(Color.parseColor("#FFFFFF"));
                    interest2 = YOGA.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest2);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// For layout3
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout3.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Badminton.setTextColor(Color.parseColor("#FFFFFF"));
                    interest3 = Badminton.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest3);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// Repeat this block for layout4 to layout18

// For layout4
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout4.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Cricket.setTextColor(Color.parseColor("#FFFFFF"));
                    interest4 = Cricket.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest4);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// Continue this pattern for layout5 to layout18...

// For layout5
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout5.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Chess.setTextColor(Color.parseColor("#FFFFFF"));
                    interest5 = Chess.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest5);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// For layout6
        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout6.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Football.setTextColor(Color.parseColor("#FFFFFF"));
                    interest6 = Football.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest6);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// Continue this pattern for layout7 to layout18...

// For layout7
        layout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout7.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Athletics.setTextColor(Color.parseColor("#FFFFFF"));
                    interest7 = Athletics.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest7);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// For layout8
        layout8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout8.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Bollywood.setTextColor(Color.parseColor("#FFFFFF"));
                    interest8 = Bollywood.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest8);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// Continue this pattern for layout9 to layout18...

// For layout9
        layout9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout9.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Hollywood.setTextColor(Color.parseColor("#FFFFFF"));
                    interest9 = Hollywood.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest9);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// For layout10
        layout10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout10.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Music.setTextColor(Color.parseColor("#FFFFFF"));
                    interest10 = Music.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest10);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// Continue this pattern for layout11 to layout18...

// For layout11
        layout11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout11.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Comedy.setTextColor(Color.parseColor("#FFFFFF"));
                    interest11 = Comedy.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest11);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// For layout12
        layout12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout12.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Drama.setTextColor(Color.parseColor("#FFFFFF"));
                    interest12 = Drama.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest12);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// For layout13
        layout13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout13.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    travelling.setTextColor(Color.parseColor("#FFFFFF"));
                    interest13 = travelling.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest13);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// For layout14
        layout14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout14.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Reading.setTextColor(Color.parseColor("#FFFFFF"));
                    interest14 = Reading.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest14);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// For layout15
        layout15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout15.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Singing.setTextColor(Color.parseColor("#FFFFFF"));
                    interest15 = Singing.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest15);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// For layout16
        layout16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout16.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Dancing.setTextColor(Color.parseColor("#FFFFFF"));
                    interest16 = Dancing.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest16);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// For layout17
        layout17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout17.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Cooking.setTextColor(Color.parseColor("#FFFFFF"));
                    interest17 = Cooking.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest17);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });

// For layout18
        layout18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCount < 6) {
                    layout18.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Painting.setTextColor(Color.parseColor("#FFFFFF"));
                    interest18 = Painting.getText().toString();

                    String userKey = FirebaseAuth.getInstance().getUid();
                    i++;
                    interestData.put("Interest" + i, interest18);

                    databaseReference.child(userKey).updateChildren(interestData);

                    selectedCount++;
                } else {
                    Toast.makeText(Interest.this, "You can only select up to 6 interests.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            dates = extras.getString("dates");

            spinners = extras.getString("spinners");
            category = extras.getString("category");
            subcategory = extras.getString("subcategory");
            Height = extras.getString("Height");
            Age = extras.getString("Age");

            selectedText = extras.getString("selectedText");

            Gender = extras.getString("Gender");
            // Assuming 'extras' contains your intent extras


        }
        layout19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout19.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                Next.setTextColor(Color.parseColor("#FFFFFF"));

                bundle.putString("below category", spinners);
                bundle.putString("category", category);
                bundle.putString("subcategory", subcategory);
                bundle.putString("username", username);
                bundle.putString("Account managed by", selectedText);
                bundle.putString("Gender", Gender);
                bundle.putString("Height", Height);
                bundle.putString("DOB", dates);
                bundle.putString("Age", Age);

                Intent i = new Intent(getApplicationContext(), IncomeActivity.class);
                i.putExtras(bundle);

                startActivity(i);
            }
        });
        }
    }

