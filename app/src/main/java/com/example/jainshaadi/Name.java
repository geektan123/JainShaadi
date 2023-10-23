package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Name extends AppCompatActivity {
LinearLayout nextlay;
TextView nexttext;
EditText editText;
Button Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        getSupportActionBar().hide();

        editText=findViewById(R.id.editText);
        String txt=editText.getText().toString();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        editText.requestFocus();
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        Next=findViewById(R.id.submitButton);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String username=editText.getText().toString();

                Next.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));

                Next.setTextColor(Color.parseColor("#FFFFFF"));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                String username = editText.getText().toString();

                // Create a Bundle to pass data
                Bundle bundle = new Bundle();
                bundle.putString("username", username);

                // Create an Intent and add the Bundle
                Intent i = new Intent(getApplicationContext(), Dob.class);
                i.putExtras(bundle);

                startActivity(i);
            }
        });
    }
}