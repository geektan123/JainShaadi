package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cast extends AppCompatActivity {
    LinearLayout Layout1;
    LinearLayout Layout2;
    LinearLayout Layout3;
    LinearLayout Layout4;
    LinearLayout Layout5;
    LinearLayout Layout6;
    TextView nexttext;
    LinearLayout nextlay;
    TextView Digamber;
    TextView Svetamber;
    String category = "None";
//    String subcategory;
    String username;
    String dates;
    String Height;

    String spinners;
    private String selectedText;
    private String Gender;
    private String Age;
    boolean isNextLayoutChanged = false;
    TextView Question;
    EditText editText;

    String subCategory;


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
        Layout6 = findViewById(R.id.layout6);
        Svetamber = findViewById(R.id.Swetamber);
        nextlay = findViewById(R.id.Nextlay);
        nexttext = findViewById(R.id.Next);
        Layout3.setVisibility(View.INVISIBLE);
        Layout4.setVisibility(View.INVISIBLE);
        Layout5.setVisibility(View.INVISIBLE);
        editText = findViewById(R.id.others);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID
        DatabaseReference userRef = databaseReference.child(userKey);
        Log.e("tag","not myself1");
        Question = findViewById(R.id.Question);
        Intent intent = getIntent();
        String gen = intent.getStringExtra("Gender");
        String acc = intent.getStringExtra("Account");
        if(acc.equals("1"))
        {
            Question.setText("Please Select Your Sub-Community");
        }
        else
        {
            if(gen.equals("1"))
            {
                Question.setText("Please Select His Sub-Community");
            }
            else if(gen.equals("2"))
            {
                Question.setText("Please Select Her Sub-Community");
            }
        }

        Layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String userKey = FirebaseAuth.getInstance().getUid();
//                DatabaseReference userRef = databaseReference.child(userKey);
//                Map<String, Object> updateData = new HashMap<>();
//                updateData.put("Category", category);
//
//                userRef.updateChildren(updateData);
                nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
//                nexttext.setTextColor(Color.parseColor("#756568"));
                isNextLayoutChanged = false;
                Layout6.setVisibility(view.INVISIBLE);
                if(category.equals("Shvetambar"))
                {
                    Layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    Svetamber.setTextColor(Color.parseColor("#756568"));
                    Layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Digamber.setTextColor(Color.parseColor("#FFFFFF"));

                }
                else if(category.equals("Digambar"))
                {
                    Layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Digamber.setTextColor(Color.parseColor("#FFFFFF"));
                }
                else
                {
                    Layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Digamber.setTextColor(Color.parseColor("#FFFFFF"));

                }
                Layout3.setVisibility(View.VISIBLE);
                Layout4.setVisibility(View.VISIBLE);
                Layout5.setVisibility(View.VISIBLE);
                category = "Digambar";
                Spinner spin = findViewById(R.id.spinner1);
                ArrayList<String> arrayAdapter1 = new ArrayList<>();
                arrayAdapter1.add("-Select-");
                arrayAdapter1.add("Digambar-Agrawal");
                arrayAdapter1.add("Digambar-Khandelwal");
                arrayAdapter1.add("Digambar-Porwal");
                arrayAdapter1.add("Digambar-Kasliwal");
                arrayAdapter1.add("Digambar-Golapurab");
                arrayAdapter1.add("Other");
                ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(Cast.this, android.R.layout.simple_spinner_item, arrayAdapter1);
                arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                spin.setAdapter(arrayAdapter6);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        if (spin.getSelectedItemPosition() > -1) {
//                            isNextLayoutChanged = true;
                            spinners = arrayAdapter1.get(position);
                            subCategory = spinners;
                            if(spinners.equals("Other"))
                            {
                                Layout6.setVisibility(view.VISIBLE);

                                subCategory = "";
                                editText.setFilters(new InputFilter[]{new NoNewlineInputFilter()});

                                editText.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                                        // This method is called to notify you that characters within `start` and `start + before` are about to be replaced with new text with a length of `after`.
                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                                        // This method is called to notify you that somewhere within `start` and `start + before`, the characters in `charSequence` have been replaced with new text with a length of `count`.
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                        // This method is called to notify you that the characters within `Editable` have been changed.
                                        String enteredText = editable.toString().trim();
                                        subCategory = enteredText;
                                        Log.e("edit text = ", enteredText);
                                        if(subCategory!=null)
                                        {
                                            nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                            nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                                            isNextLayoutChanged = true;
                                        }
                                        else
                                        {
                                            nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
                                            nexttext.setTextColor(Color.parseColor("#756568"));
                                            isNextLayoutChanged = false;
                                        }
                                    }
                                });
                            }
                            else
                            {
                                Layout6.setVisibility(view.INVISIBLE);
                                nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                                isNextLayoutChanged = true;
                            }

                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        // Handle case when nothing is selected
                    }
                });
                }
            });
//
        Layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
//                nexttext.setTextColor(Color.parseColor("#756568"));
                isNextLayoutChanged = false;
                Layout6.setVisibility(view.INVISIBLE);

                if(category.equals("Digambar"))
                {
                    Layout1.setBackground(getResources().getDrawable(R.drawable.rounded_card_background));
                    Digamber.setTextColor(Color.parseColor("#756568"));
                    Layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Svetamber.setTextColor(Color.parseColor("#FFFFFF"));

                }
                else if(category.equals("Shvetambar"))
                {
                    Layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Svetamber.setTextColor(Color.parseColor("#FFFFFF"));
                }
                else
                {
                    Layout2.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Svetamber.setTextColor(Color.parseColor("#FFFFFF"));

                }
                Layout3.setVisibility(View.VISIBLE);
                Layout4.setVisibility(View.VISIBLE);
                Layout5.setVisibility(View.VISIBLE);
                category = "Shvetambar";
                Spinner spin = findViewById(R.id.spinner1);
                ArrayList<String> arrayAdapter1 = new ArrayList<>();
                arrayAdapter1.add("-Select-");
                arrayAdapter1.add("Shvetambar-Khandelwal");
                arrayAdapter1.add("Shvetambar-Porwal");
                arrayAdapter1.add("Shvetambar-Kasliwal");
                arrayAdapter1.add("Shvetambar-Golapurab");
                arrayAdapter1.add("Other");
                ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(Cast.this, android.R.layout.simple_spinner_item, arrayAdapter1);
                arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                spin.setAdapter(arrayAdapter6);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        if (spin.getSelectedItemPosition() > -1) {
//                            isNextLayoutChanged = true;
                            spinners = arrayAdapter1.get(position);
                            subCategory = spinners;
                            if(spinners.equals("Other"))
                            {
                                Layout6.setVisibility(view.VISIBLE);
                                editText = findViewById(R.id.others);
                                subCategory = "";

                                editText.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                                        // This method is called to notify you that characters within `start` and `start + before` are about to be replaced with new text with a length of `after`.
                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                                        // This method is called to notify you that somewhere within `start` and `start + before`, the characters in `charSequence` have been replaced with new text with a length of `count`.
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                        // This method is called to notify you that the characters within `Editable` have been changed.
                                        String enteredText = editable.toString();
                                        Log.e("edit text = ", enteredText);
                                        subCategory = enteredText;
                                        if(subCategory!=null)
                                        {
                                            nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                            nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                                            isNextLayoutChanged = true;
                                        }
                                        else
                                        {
                                            nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
                                            nexttext.setTextColor(Color.parseColor("#756568"));
                                            isNextLayoutChanged = false;
                                        }
                                    }
                                });
                            }
                            else
                            {
                                Layout6.setVisibility(view.INVISIBLE);
                                nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                                isNextLayoutChanged = true;
                            }

                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        // Handle case when nothing is selected
                    }
                });
            }
        });



//
//        Spinner spin = findViewById(R.id.spinner1);
//        ArrayList<String> arrayAdapter1 = new ArrayList<>();
//        arrayAdapter1.add("Digamber - Agrawal");
//        arrayAdapter1.add("Svetamber - Agrawal");
//        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayAdapter1);
//        arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
//        spin.setAdapter(arrayAdapter6);
//
//        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                if (spin.getSelectedItemPosition() > 0) {
//                    nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
//                    nexttext.setTextColor(Color.parseColor("#FFFFFF"));
//                    isNextLayoutChanged = true;
//
//                    spinners = arrayAdapter1.get(position);
//
//                    String userKey = FirebaseAuth.getInstance().getUid();
//                    DatabaseReference userRef = databaseReference.child(userKey);
//                    Map<String, Object> updateData = new HashMap<>();
//                    updateData.put("Subcategory", spinners);
//                    userRef.updateChildren(updateData);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                // Handle when nothing is selected
//            }
//        });
//
//
        nextlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(subCategory == "-Select-" || subCategory.isEmpty())
                {
                    isNextLayoutChanged = false;
                }
                if (isNextLayoutChanged) {
                    String userKey = FirebaseAuth.getInstance().getUid();
                    DatabaseReference userRef = databaseReference.child(userKey);
                    Map<String, Object> updateData = new HashMap<>();
                    updateData.put("Category", category);
//                    Log.e("subcat",subCategory);
                    updateData.put("Subcategory", subCategory);
                    userRef.updateChildren(updateData);
                    Intent i = new Intent(getApplicationContext(), LocationActivity.class);
                    i.putExtra("Gender",gen);
                    i.putExtra("Account",acc);
                    startActivity(i);
                } else {
                    // Display a message to the user indicating that they need to change the Next layout color
                    Toast.makeText(Cast.this, "Please enter inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
