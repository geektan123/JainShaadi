package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class family_information_form2 extends AppCompatActivity {
    LinearLayout NextLay;
    TextView Nexttext;
    Spinner spinner1;
    LinearLayout layout;
    int isNextLayoutChanged = 0;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");


    private Spinner spinner2;
    private Spinner stateSpinner, districtSpinner;
    private String selectedDistrict = "", selectedState = "",Family = "",number ="";                 //vars to hold the values of selected State and District
    //Spinners
    private ArrayAdapter<CharSequence> stateAdapter, districtAdapter;   //declare adapters for the spinners


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_information_form2);
        getSupportActionBar().hide();
        //layout = findViewById(R.id.layout1);

        spinner1 = findViewById(R.id.spinner1);
        ArrayList<String> arrayAdapter2 = new ArrayList<>();

        arrayAdapter2.add("-Select-");
        arrayAdapter2.add("Nuclear Family");
        arrayAdapter2.add("Single-Parent Family");
        arrayAdapter2.add("Joint Family");

        ArrayAdapter<String> arrayAdapter7 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayAdapter2);
        arrayAdapter7.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner1.setAdapter(arrayAdapter7);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Family = spinner1.getSelectedItem().toString();
                if(selectedState.isEmpty()||selectedDistrict.isEmpty()||selectedState.equals("Select Your State") || selectedDistrict.equals("Select Your District") || Family.isEmpty() || Family.equals("-Select-") ||number.equals("-Select-")|| number.isEmpty())
                {
//                                Toast.makeText(family_information_form2.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    NextLay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when nothing is selected
            }
        });
        spinner2 = findViewById(R.id.spinner2);
        ArrayList<String> arrayAdapter3 = new ArrayList<>();
        arrayAdapter3.add("-Select-");
        arrayAdapter3.add("1");
        arrayAdapter3.add("2");
        arrayAdapter3.add("3");
        arrayAdapter3.add("4");
        arrayAdapter3.add("5");
        arrayAdapter3.add("6");
        arrayAdapter3.add("7");
        arrayAdapter3.add("8");
        arrayAdapter3.add("8+");

        ArrayAdapter<String> arrayAdapter8 = new ArrayAdapter<>(family_information_form2.this, android.R.layout.simple_spinner_item, arrayAdapter3);
        arrayAdapter8.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinner2.setAdapter(arrayAdapter8);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                number = spinner2.getSelectedItem().toString();
                if(selectedState.isEmpty()||selectedDistrict.isEmpty()||selectedState.equals("Select Your State") || selectedDistrict.equals("Select Your District") || Family.isEmpty() || Family.equals("-Select-") ||number.equals("-Select-")|| number.isEmpty())
                {
//                                Toast.makeText(family_information_form2.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    NextLay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when nothing is selected
            }
        });
        stateSpinner = findViewById(R.id.spinner_indian_states);    //Finds a view that was identified by the android:id attribute in xml

        //Populate ArrayAdapter using string array and a spinner layout that we will define
        stateAdapter = ArrayAdapter.createFromResource(this, R.array.array_indian_states, R.layout.spinner_layout);

        // Specify the layout to use when the list of choices appear
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(stateAdapter);            //Set the adapter to the spinner to populate the State Spinner

        //When any item of the stateSpinner uis selected
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Define City Spinner but we will populate the options through the selected state
                districtSpinner = findViewById(R.id.spinner_indian_districts);

                selectedState = stateSpinner.getSelectedItem().toString();      //Obtain the selected State

                int parentID = parent.getId();
                if (parentID == R.id.spinner_indian_states) {
                    switch (selectedState) {
                        case "Select Your State":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_default_districts, R.layout.spinner_layout);
                            break;
                        case "Andhra Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_andhra_pradesh_districts, R.layout.spinner_layout);
                            break;
                        case "Arunachal Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_arunachal_pradesh_districts, R.layout.spinner_layout);
                            break;
                        case "Assam":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_assam_districts, R.layout.spinner_layout);
                            break;
                        case "Bihar":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_bihar_districts, R.layout.spinner_layout);
                            break;
                        case "Chhattisgarh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_chhattisgarh_districts, R.layout.spinner_layout);
                            break;
                        case "Goa":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_goa_districts, R.layout.spinner_layout);
                            break;
                        case "Gujarat":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_gujarat_districts, R.layout.spinner_layout);
                            break;
                        case "Haryana":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_haryana_districts, R.layout.spinner_layout);
                            break;
                        case "Himachal Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_himachal_pradesh_districts, R.layout.spinner_layout);
                            break;
                        case "Jharkhand":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_jharkhand_districts, R.layout.spinner_layout);
                            break;
                        case "Karnataka":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_karnataka_districts, R.layout.spinner_layout);
                            break;
                        case "Kerala":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_kerala_districts, R.layout.spinner_layout);
                            break;
                        case "Madhya Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_madhya_pradesh_districts, R.layout.spinner_layout);
                            break;
                        case "Maharashtra":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_maharashtra_districts, R.layout.spinner_layout);
                            break;
                        case "Manipur":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_manipur_districts, R.layout.spinner_layout);
                            break;
                        case "Meghalaya":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_meghalaya_districts, R.layout.spinner_layout);
                            break;
                        case "Mizoram":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_mizoram_districts, R.layout.spinner_layout);
                            break;
                        case "Nagaland":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_nagaland_districts, R.layout.spinner_layout);
                            break;
                        case "Odisha":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_odisha_districts, R.layout.spinner_layout);
                            break;
                        case "Punjab":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_punjab_districts, R.layout.spinner_layout);
                            break;
                        case "Rajasthan":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_rajasthan_districts, R.layout.spinner_layout);
                            break;
                        case "Sikkim":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_sikkim_districts, R.layout.spinner_layout);
                            break;
                        case "Tamil Nadu":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_tamil_nadu_districts, R.layout.spinner_layout);
                            break;
                        case "Telangana":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_telangana_districts, R.layout.spinner_layout);
                            break;
                        case "Tripura":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_tripura_districts, R.layout.spinner_layout);
                            break;
                        case "Uttar Pradesh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_uttar_pradesh_districts, R.layout.spinner_layout);
                            break;
                        case "Uttarakhand":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_uttarakhand_districts, R.layout.spinner_layout);
                            break;
                        case "West Bengal":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_west_bengal_districts, R.layout.spinner_layout);
                            break;
                        case "Andaman and Nicobar Islands":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_andaman_nicobar_districts, R.layout.spinner_layout);
                            break;
                        case "Chandigarh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_chandigarh_districts, R.layout.spinner_layout);
                            break;
                        case "Dadra and Nagar Haveli":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_dadra_nagar_haveli_districts, R.layout.spinner_layout);
                            break;
                        case "Daman and Diu":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_daman_diu_districts, R.layout.spinner_layout);
                            break;
                        case "Delhi":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_delhi_districts, R.layout.spinner_layout);
                            break;
                        case "Jammu and Kashmir":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_jammu_kashmir_districts, R.layout.spinner_layout);
                            break;
                        case "Lakshadweep":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_lakshadweep_districts, R.layout.spinner_layout);
                            break;
                        case "Ladakh":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_ladakh_districts, R.layout.spinner_layout);
                            break;
                        case "Puducherry":
                            districtAdapter = ArrayAdapter.createFromResource(parent.getContext(),
                                    R.array.array_puducherry_districts, R.layout.spinner_layout);
                            break;
                        default:
                            break;
                    }
                    districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     // Specify the layout to use when the list of choices appears
                    districtSpinner.setAdapter(districtAdapter);        //Populate the list of Districts in respect of the State selected

                    //To obtain the selected District from the spinner

                    districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedDistrict = districtSpinner.getSelectedItem().toString();
                            if(selectedState.isEmpty()||selectedDistrict.isEmpty()||selectedState.equals("Select Your State") || selectedDistrict.equals("Select Your District") || Family.isEmpty() || Family.equals("-Select-") ||number.equals("-Select-")|| number.isEmpty())
                            {
//                                Toast.makeText(family_information_form2.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                NextLay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                                Nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                            }
//                            else
//                                Toast.makeText(family_information_form2.this, "Please Complete the values", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Handle when nothing is selected
                        }
                    });
                }

                NextLay = findViewById(R.id.Next);
                Nexttext = findViewById(R.id.Nexttext);


                NextLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            String userKey = FirebaseAuth.getInstance().getUid();
                            DatabaseReference userRef = databaseReference.child(userKey);
                            Map<String, Object> updateData = new HashMap<>();

                            if(selectedState.equals("Select Your State") || selectedDistrict.equals("Select Your District") || Family.isEmpty() || Family.equals("-Select-") ||number.equals("-Select-")|| number.isEmpty())
                            {
                                Toast.makeText(family_information_form2.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                updateData.put("ParentState", selectedState);
                                updateData.put("ParentCity", selectedDistrict);
                                updateData.put("Familytype", Family);
                                updateData.put("FamilyMembers", number);
//                                updateData.put("status","Completed");
                                userRef.updateChildren(updateData);
                                Intent i = new Intent(getApplicationContext(), Completeform_Image_01.class);
                                startActivity(i);
                            }



//                            NextLay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
//                            Nexttext.setTextColor(Color.parseColor("#FFFFFF"));


                            // Display a toast message


                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when nothing is selected
            }
        });
    }
}