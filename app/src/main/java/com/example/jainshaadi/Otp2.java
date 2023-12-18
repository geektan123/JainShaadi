package com.example.jainshaadi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.base.Verify;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class Otp2 extends AppCompatActivity {
    private TextInputLayout otpLayout;
    private TextInputEditText otpEditText;
    private LinearLayout Verify;
    private String enteredotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.verification_bar);
        ImageView BackButton = actionBar.getCustomView().findViewById(R.id.logo);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for the logo
                onBackPressed();
            }
        });
        otpEditText = findViewById(R.id.otpEditText);
        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredotp = otpEditText.getText().toString();
                if (enteredotp.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your OTP First ", Toast.LENGTH_SHORT).show();
                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(otpEditText.getWindowToken(), 0);
                    //progressBar.setVisibility(View.VISIBLE);
                    Verify.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                 //   Send.setTextColor(Color.parseColor("#FFFFFF"));
                   // String coderecieved = Codesent;
                 //   PhoneAuthCredential credential = PhoneAuthProvider.getCredential(coderecieved, enteredotp);
                 //   signInWithPhoneAuthCredential(credential);

                }
            }
        });
    }
}