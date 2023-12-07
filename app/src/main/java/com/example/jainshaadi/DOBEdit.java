package com.example.jainshaadi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DOBEdit extends DialogFragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_dob, container, false);

        date = view.findViewById(R.id.date);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        nextlay = view.findViewById(R.id.Nextlay);
        nexttext = view.findViewById(R.id.Nexttext);
        layout = view.findViewById(R.id.layout);
        layout1 = view.findViewById(R.id.layout1);

        layout.setVisibility(View.INVISIBLE);
        layout1.setVisibility(View.INVISIBLE);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        dates = DateFormat.getDateInstance().format(calendar.getTime());
                        age = calculateAge(year);

                        date.setText(dates);
                        layout1.setVisibility(View.VISIBLE);
                        layout.setVisibility(View.VISIBLE);
                    }
                }, 2000, 01, 20);

                datePickerDialog.show();
            }
        });

        Spinner spinner = view.findViewById(R.id.spinner1);
        ArrayList<String> arrayAdapter = new ArrayList<>();
        arrayAdapter.add("4 Feet");
        arrayAdapter.add("5 Feet");
        arrayAdapter.add("6 Feet");
        arrayAdapter.add("7 Feet");
        arrayAdapter.add("8 Feet");

        ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, arrayAdapter);
        arrayAdapter5.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(arrayAdapter5);

        Spinner spin = view.findViewById(R.id.spinner2);
        ArrayList<String> arrayAdapter1 = new ArrayList<>();
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

        ArrayAdapter<String> arrayAdapter6 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, arrayAdapter1);
        arrayAdapter6.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin.setAdapter(arrayAdapter6);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItemPosition() > 0 && spin.getSelectedItemPosition() > 0) {
                    nextlay.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    nexttext.setTextColor(requireContext().getColor(R.color.white));
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

                    if (dates != null && !spinnerValue1.isEmpty() && !spinnerValue2.isEmpty()) {
                        String userKey = FirebaseAuth.getInstance().getUid();
                        DatabaseReference userRef = databaseReference.child(userKey);
                        Map<String, Object> updateData = new HashMap<>();

                        updateData.put("DateOfBirth", dates);
                        updateData.put("Height", spinnerValue1.substring(0, 1) + "'" + spinnerValue2.substring(0, 1) + "\"");
                        updateData.put("Age", String.valueOf(age));
                        updateData.put("AgeInt", age);

                        userRef.updateChildren(updateData);

                        dismiss();
                    } else {
                        Toast.makeText(requireContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private int calculateAge(int year) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        return currentYear - year;
    }
}
