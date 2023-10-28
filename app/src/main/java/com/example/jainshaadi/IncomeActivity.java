package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class IncomeActivity extends AppCompatActivity {
    Spinner spin;
    Spinner spin1;
    boolean isNextLayoutChanged = false;

    LinearLayout Next;
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    LinearLayout layout4;
    LinearLayout layout5;
    LinearLayout layout6;
    LinearLayout layout7;
    EditText Role;
    EditText Company;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    TextView nexttext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_income);
        spin = findViewById(R.id.spinner1);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        layout3 = findViewById(R.id.layout3);
        layout4 = findViewById(R.id.layout4);
        layout5 = findViewById(R.id.layout5);
        layout6 = findViewById(R.id.layout6);
        layout7 = findViewById(R.id.layout7);
        Role = findViewById(R.id.Role);
        Company = findViewById(R.id.Company);
nexttext= findViewById(R.id.Nexttext);
     /*   layout1.setVisibility(View.INVISIBLE);
        layout2.setVisibility(View.INVISIBLE);
        layout3.setVisibility(View.INVISIBLE);
        layout4.setVisibility(View.INVISIBLE);
        layout5.setVisibility(View.INVISIBLE);
        layout6.setVisibility(View.INVISIBLE);
        layout7.setVisibility(View.INVISIBLE);*/
     /* spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.VISIBLE);
                layout3.setVisibility(View.VISIBLE);
                layout4.setVisibility(View.VISIBLE);
                layout5.setVisibility(View.VISIBLE);
                layout6.setVisibility(View.VISIBLE);
                layout7.setVisibility(View.VISIBLE);
            }
        });*/

        ArrayList<String> arrayAdapter1 = new ArrayList<>();
        arrayAdapter1.add("Private Company");
        arrayAdapter1.add("Government Company");
        arrayAdapter1.add("Startup");
        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayAdapter1);
        arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin.setAdapter(arrayAdapter6);


        spin1 = findViewById(R.id.spinner2);
        ArrayList<String> arrayAdapter2 = new ArrayList<>();
        arrayAdapter2.add("Below 1 Lakh");
        arrayAdapter2.add("1 Lakh to 3 Lakh");
        arrayAdapter2.add("3 Lakhs to 6 Lakh");
        arrayAdapter2.add("6 Lakhs to 9 Lakh");
        arrayAdapter2.add("9 Lakhs to 12 Lakh");


        ArrayAdapter<String> arrayAdapter7 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayAdapter2);
        arrayAdapter7.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin1.setAdapter(arrayAdapter7);
       /* spin1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Next.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));

                nexttext.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });*/

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spin1.getSelectedItemPosition() > 0) {
                    Next.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                    isNextLayoutChanged = true;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when nothing is selected
            }
        });
        Next = findViewById(R.id.Nextlay);
        nexttext = findViewById(R.id.Nexttext);


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text;
                String text1;

                String spinner1Value = spin.getSelectedItem().toString();
                String spinner2Value = spin1.getSelectedItem().toString();
                text = Role.getText().toString(); // This will fetch the text from the EditText as a String
                text1 = Company.getText().toString(); // This will fetch the text from the EditText as a String

                // Check if Role and Company are not empty
                if (!text.isEmpty() && !text1.isEmpty()&&spin.getSelectedItemPosition()> 0&&spin1.getSelectedItemPosition()> 0&&isNextLayoutChanged==true) {
                    Next.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                    HashMap<String, Object> incomeData = new HashMap<>();
                    incomeData.put("IncomeType", spinner1Value);
                    incomeData.put("IncomeRange", spinner2Value);
                    incomeData.put("Role", text);
                    incomeData.put("Company", text1);
                    String userKey = FirebaseAuth.getInstance().getUid();

                    // Update user data in Firebase
                    databaseReference.child(userKey).updateChildren(incomeData);

                    Intent i = new Intent(getApplicationContext(), DescribeActivity.class);
                    startActivity(i);
                } else {
                    // Show a Toast or error message indicating that Role and Company are mandatory
                    Toast.makeText(IncomeActivity.this, "All the field are mandatory", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}