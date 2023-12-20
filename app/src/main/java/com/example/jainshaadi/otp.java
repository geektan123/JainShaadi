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
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class otp extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_otp);
        getSupportActionBar().hide();
       mAuth = FirebaseAuth.getInstance();
// set this to remove reCaptcha web
        mAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
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
        // progressBar = findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.INVISIBLE);
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
                if (number1.length() >= 10) {
                    Verify.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));
                    Send.setTextColor(Color.parseColor("#FFFFFF"));
                    //  Send.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));

                    // Hide the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mobileNumberEditText.getWindowToken(), 0);
                } else {
                    Verify.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
                    Send.setTextColor(Color.parseColor("#FFFFFF"));

                }
            }
        });


        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Replace "7587680487" with the actual phone number
                String number1 = mobileNumberEditText.getText().toString();
                // Log.e("hunda", "error :" + number1);

                number = ("+91".concat(number1)).trim();
                Log.e("hunda", "error :" + number);
                //    progressBar.setVisibility(View.VISIBLE);


                if (number1.isEmpty()) {
                    //    progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(), "Please Enter YOur number", Toast.LENGTH_SHORT).show();
                } else if (number1.length() < 10 || number1.length() > 10) {
                    //   progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(), "Please Enter correct number", Toast.LENGTH_SHORT).show();
                } else {

                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(number)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(otp.this)
                            .setCallbacks(mCallbacks)
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // How to automatically fetch code here
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // Handle verification failure
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                // Handle code sent
                Toast.makeText(getApplicationContext(), "OTP is Sent", Toast.LENGTH_SHORT).show();
               /* mobileNumberLayout.setVisibility(View.INVISIBLE);
               // progressBar.setVisibility(View.INVISIBLE);
                otpLayout.setVisibility(View.VISIBLE);
                Verify.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_next_disabled));
                Send.setTextColor(Color.parseColor("#FFFFFF"));
                Request.setVisibility(View.VISIBLE);
                Send.setText("Verify");
                temp="Verify";*/


                // Delay visibility by 60 seconds (60 * 1000 milliseconds)

                // 60 seconds
                new CountDownTimer(60000, 1000) { // 60 seconds, tick every 1 second
                    public void onTick(long millisUntilFinished) {
                        long secondsLeft = millisUntilFinished / 1000;
                        Timer.setText("You may request the new OTP in " + secondsLeft + " seconds");
                    }

                    public void onFinish() {
                        // When the countdown is finished, make your layout visible
                        didnot.setVisibility(View.VISIBLE);
                    }
                }.start();
                Codesent = s;

                Intent intent = new Intent(otp.this, Otp2.class);
                intent.putExtra("otp", Codesent);
                startActivity(intent);
            }
        };
        Log.e("gggg", "error :" + temp);
    }
}