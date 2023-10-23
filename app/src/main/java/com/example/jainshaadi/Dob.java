package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Dob extends AppCompatActivity {
    TextView date;
    LinearLayout nextlay;
    LinearLayout layout;
    LinearLayout layout1;

    TextView nexttext;
    String dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dob);
        date=findViewById(R.id.date);
        getSupportActionBar().hide();
        nextlay=findViewById(R.id.Nextlay);
        nexttext=findViewById(R.id.Nexttext);
        layout=findViewById(R.id.layout);
        layout1=findViewById(R.id.layout1);

        layout.setVisibility(View.INVISIBLE);
        layout1.setVisibility(View.INVISIBLE);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Dob.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar=Calendar.getInstance();
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,day);
                        String dates= DateFormat.getDateInstance().format(calendar.getTime());



                        //Showing the picked value in the textView
                            date.setText(dates);
                        layout1.setVisibility(View.VISIBLE);

                        layout.setVisibility(View.VISIBLE);
                    }
                }, 2000, 01, 20);

                datePickerDialog.show();

            }
        });
        Spinner spinner=findViewById(R.id.spinner1);
        ArrayList<String>arrayAdapter=new ArrayList<>();

        arrayAdapter.add("4 Feet");
        arrayAdapter.add("5 Feet");

        arrayAdapter.add("6 Feet");

        arrayAdapter.add("7 Feet");

        arrayAdapter.add("8 Feet");
        ArrayAdapter<String>arrayAdapter5=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayAdapter);
            arrayAdapter5.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            spinner.setAdapter(arrayAdapter5);
        Spinner spin=findViewById(R.id.spinner2);
        ArrayList<String>arrayAdapter1=new ArrayList<>();
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



        ArrayAdapter<String>arrayAdapter6=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayAdapter1);
        arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin.setAdapter(arrayAdapter6);


        nextlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the username from the Bundle
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String username = extras.getString("username");
                    // Retrieve the date
                    String dates = date.getText().toString();

                    // Retrieve selected values from spinners
                    Spinner spinner1 = findViewById(R.id.spinner1);
                    String spinnerValue1 = spinner1.getSelectedItem().toString();

                    Spinner spinner2 = findViewById(R.id.spinner2);
                    String spinnerValue2 = spinner2.getSelectedItem().toString();

                    // Create a new Bundle to pass username, dates, and spinner values
                    Bundle newBundle = new Bundle();
                    newBundle.putString("username", username);
                    newBundle.putString("dates", dates);
                    newBundle.putString("spinnerValue1", spinnerValue1);
                    newBundle.putString("spinnerValue2", spinnerValue2);

                    // Create an Intent and add the new Bundle
                    Intent i = new Intent(getApplicationContext(), Cast.class);
                    i.putExtras(newBundle);

                    startActivity(i);
                }
            }
        });


    }
}


