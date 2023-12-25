package com.example.jainshaadi;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.base.Verify;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class otp extends AppCompatActivity {
    private static final String INDIAN_PHONE_NUMBER_PATTERN = "^[789]\\d{9}$";
    private boolean isPhoneNumberValid = false; // Add this variable

    LinearLayout Verify;
    private FirebaseAuth mAuth;
    private TextInputLayout mobileNumberLayout;
    private TextInputEditText mobileNumberEditText;
    String number;
    private TextInputLayout otpLayout;
    private TextInputEditText otpEditText;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    TextView Send;
    ProgressBar progressBar;
    LinearLayout didnot;
    LinearLayout Request;
    String enteredotp;
    String Codesent;
    String temp = "";
    private LinearLayout firstLinearLayout;
    private ImageView image1;
    private View firstView;

    private LinearLayout secondLinearLayout;
    private ImageView secondImage;
    private View secondView;

    private LinearLayout thirdLinearLayout;
    private ImageView thirdImage;
    private TextView Timer;
    /*DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");*/
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_otp);
        getSupportActionBar().hide();
        firstLinearLayout = findViewById(R.id.firstLinearLayout);
        image1 = findViewById(R.id.image1);
        firstView = findViewById(R.id.firstView);
        Timer = findViewById(R.id.Timer);
        secondLinearLayout = findViewById(R.id.secondLinearLayout);
        secondImage = findViewById(R.id.secondImage);
        secondView = findViewById(R.id.secondView);

        thirdLinearLayout = findViewById(R.id.thirdLinearLayout);
        thirdImage = findViewById(R.id.thirdImage);
        mobileNumberLayout = findViewById(R.id.mobileNumberLayout);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        Send = findViewById(R.id.Send);
        otpLayout = findViewById(R.id.otpLayout);
        otpEditText = findViewById(R.id.otpEditText);
        Verify = findViewById(R.id.Verify);
        mAuth = FirebaseAuth.getInstance();
        didnot = findViewById(R.id.didnot);
        Request = findViewById(R.id.request);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        otpLayout.setVisibility(View.INVISIBLE);
        didnot.setVisibility(View.INVISIBLE);
        Request.setVisibility(View.INVISIBLE);

// ...

        mobileNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used, but required for implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used, but required for implementation
            }

            @Override
            public void afterTextChanged(Editable s) {
                String number1 = s.toString();

                if (number1.length() == 10 && isValidIndianPhoneNumber(number1)) {
                    Verify.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Send.setTextColor(Color.parseColor("#FFFFFF"));
                    isPhoneNumberValid = true; // Set the flag to true when the phone number is valid
                    String userKey = FirebaseAuth.getInstance().getUid();
                    DatabaseReference userRef = databaseReference.child(userKey);
                    Map<String, Object> updateData = new HashMap<>();

                    updateData.put("Phone Number", number1);
                    userRef.updateChildren(updateData);

                    // Hide the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mobileNumberEditText.getWindowToken(), 0);
                    Toast.makeText(getApplicationContext(), "Verified", Toast.LENGTH_SHORT).show();
                } else if (number1.length() == 10 && !isValidIndianPhoneNumber(number1)) {
                    // The phone number is 10 digits but not valid
                    Verify.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
                    Send.setTextColor(Color.parseColor("#FFFFFF"));
                    isPhoneNumberValid = false; // Set the flag to false when the phone number is not valid

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mobileNumberEditText.getWindowToken(), 0);
                    Toast.makeText(getApplicationContext(), "Please enter a valid Indian number", Toast.LENGTH_SHORT).show();
                } else {
                    // The phone number is not 10 digits
                    isPhoneNumberValid = false; // Set the flag to false when the phone number is not valid

                    Verify.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
                    Send.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

        });
        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check the flag to determine whether the phone number is valid
                if (isPhoneNumberValid) {
                    // Change the intent
                    Intent intent = new Intent(otp.this, educational_information_form.class); // Replace NextActivity with your desired activity
                    startActivity(intent);
                    // Add any other logic you want to perform after changing the intent
                } else {
                    // Show toast if the phone number is not valid
                    Toast.makeText(getApplicationContext(), "Please enter a valid 10-Digit Indian number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    // Function to validate Indian phone number
    private static boolean isValidIndianPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(INDIAN_PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}