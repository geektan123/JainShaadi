package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;

public class VerificationComplete extends AppCompatActivity {
    LinearLayout Next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().unsubscribeFromTopic("IncompleteProfile")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("tag","User is subscribed");
                    }
                    else {
                    }
                });
        setContentView(R.layout.verification_complete);
        getSupportActionBar().hide();

        Next=findViewById(R.id.Nextlay);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}