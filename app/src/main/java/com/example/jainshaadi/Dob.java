package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Dob extends AppCompatActivity {
    int age;
    TextView date;
    LinearLayout nextlay;
    LinearLayout layout;
    LinearLayout layout1;
    TextView Question;

    TextView nexttext;
    String dates;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dob);

        date = findViewById(R.id.date);
        getSupportActionBar().hide();
        nextlay = findViewById(R.id.Nextlay);
        nexttext = findViewById(R.id.Nexttext);
        layout = findViewById(R.id.layout);
        layout1 = findViewById(R.id.layout1);

        layout.setVisibility(View.INVISIBLE);
        layout1.setVisibility(View.INVISIBLE);

        String userKey = FirebaseAuth.getInstance().getUid(); // Replace with actual user ID
        DatabaseReference userRef = databaseReference.child(userKey);
        Log.e("tag","not myself1");
        Question = findViewById(R.id.Question);
        Intent intent = getIntent();
        String gen = intent.getStringExtra("Gender");
        String acc = intent.getStringExtra("Account");
        if(acc.equals("1"))
        {
            Question.setText("What's Your DOB ?");
        }
        else
        {
            if(gen.equals("1"))
            {
                Question.setText("What's His DOB ?");
            }
            else if(gen.equals("2"))
            {
                Question.setText("What's Her DOB ?");
            }
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Dob.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        dates = DateFormat.getDateInstance().format(calendar.getTime());
                         age = calculateAge(year);

                        // Showing the picked value in the textView
                        date.setText(dates);
                        layout1.setVisibility(View.VISIBLE);
                        layout.setVisibility(View.VISIBLE);
                    }
                }, 2000, 01, 20);

                datePickerDialog.show();
            }
        });

        Spinner spinner = findViewById(R.id.spinner1);
        ArrayList<String> arrayAdapter = new ArrayList<>();
        arrayAdapter.add("Select");
        arrayAdapter.add("4 Feet");
        arrayAdapter.add("5 Feet");
        arrayAdapter.add("6 Feet");
        arrayAdapter.add("7 Feet");
        arrayAdapter.add("8 Feet");

        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayAdapter);
        arrayAdapter5.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(arrayAdapter5);

        Spinner spin = findViewById(R.id.spinner2);
        ArrayList<String> arrayAdapter1 = new ArrayList<>();
        arrayAdapter1.add("Select");
        arrayAdapter1.add("0 Inch");
        arrayAdapter1.add("1 Inch");
        arrayAdapter1.add("2 Inch");
        arrayAdapter1.add("3 Inch");
        arrayAdapter1.add("4 Inch");
        arrayAdapter1.add("5 Inch");
        arrayAdapter1.add("6 Inch");
        arrayAdapter1.add("7 Inch");
        arrayAdapter1.add("8 Inch");
        arrayAdapter1.add("9 Inch");
        arrayAdapter1.add("10 Inch");
        arrayAdapter1.add("11 Inch");

        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayAdapter1);
        arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin.setAdapter(arrayAdapter6);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Check if both spinners have a selected value
                if (spinner.getSelectedItemPosition() > 0 && spin.getSelectedItemPosition() > 0) {
                    nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle the case where nothing is selected
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Check if both spinners have a selected value
                if (spinner.getSelectedItemPosition() > 0 && spin.getSelectedItemPosition() > 0) {
                    nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle the case where nothing is selected
            }
        });

        nextlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String spinnerValue1 = spinner.getSelectedItem().toString();
                    String spinnerValue2 = spin.getSelectedItem().toString();

                    if (!dates.isEmpty() && !spinnerValue1.isEmpty() && !spinnerValue2.isEmpty() && !(spinnerValue1.equals("Select")) && !(spinnerValue2.equals("Select"))) {
                        if((gen.equals("1") && age >= 21) || (gen.equals("2") && age >= 18)) {
                            String userKey = FirebaseAuth.getInstance().getUid();
                            DatabaseReference userRef = databaseReference.child(userKey);
                            Map<String, Object> updateData = new HashMap<>();

                            updateData.put("DateOfBirth", dates);
                            int firstSpaceIndex = spinnerValue2.indexOf(" ");
                            updateData.put("Height", spinnerValue1.substring(0, 1) + "'" + spinnerValue2.substring(0, firstSpaceIndex) + "\"");
                            updateData.put("Age", String.valueOf(age));
                            updateData.put("AgeInt", age);// Add this line to upload age
                            userRef.updateChildren(updateData);

                            Intent i = new Intent(getApplicationContext(), Cast.class);
                            i.putExtra("Gender", gen);
                            i.putExtra("Account", acc);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(Dob.this, "Not a valid age for Marriage.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Dob.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Dob.this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private int calculateAge(int year) {
        String userKey = FirebaseAuth.getInstance().getUid();

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        return currentYear - year;


    }
}
