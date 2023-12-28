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

public class Otp2 extends AppCompatActivity {

    LinearLayout Verify;
    FirebaseAuth mAuth;
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
    String number1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_otp);
        getSupportActionBar().hide();
        firstLinearLayout = findViewById(R.id.firstLinearLayout);
        image1 = findViewById(R.id.image1);
        firstView = findViewById(R.id.firstView);
        secondLinearLayout = findViewById(R.id.secondLinearLayout);
        secondImage = findViewById(R.id.secondImage);
        secondView = findViewById(R.id.secondView);
        thirdLinearLayout = findViewById(R.id.thirdLinearLayout);
        thirdImage = findViewById(R.id.thirdImage);
        mobileNumberLayout = findViewById(R.id.mobileNumberLayout);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        Send = findViewById(R.id.Send);
        otpLayout = findViewById(R.id.otpLayout);
        Verify = findViewById(R.id.Verify);
        mAuth = FirebaseAuth.getInstance();

        mobileNumberLayout.setVisibility(View.INVISIBLE);
        otpLayout.setVisibility(View.VISIBLE);


        didnot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Otp2.this, otp.class);
                startActivity(intent);
            }
        });
        otpEditText.addTextChangedListener(new TextWatcher() {
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
                number1 = s.toString();
                if (number1.length() >= 6) {
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
            public void onClick(View view) {
                if(number1.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter your OTP First ",Toast.LENGTH_SHORT).show();
                }
                else

                {
                    //  mprogressbarofotpauth.setVisibility(View.VISIBLE);

                }
            }

            private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
                mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //  Log.e("yyyy", "true");

//                    mprogressbarofotpauth.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(),"Login sucess",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Otp2.this,educational_information_form.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                // mprogressbarofotpauth.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }
        });


// ...


    }


}