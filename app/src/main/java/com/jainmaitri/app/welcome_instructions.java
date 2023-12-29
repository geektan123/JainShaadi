package com.jainmaitri.app;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class welcome_instructions extends AppCompatActivity {
    LinearLayout Next;
    TextView nexttext;
    public static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1;
    private static final String NOTIFICATION_PERMISSION = Manifest.permission.POST_NOTIFICATIONS;
    private int REQUEST_CODE = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_instructions);
        getSupportActionBar().hide();
// Notification permission not granted, request it


        Next = findViewById(R.id.Nextlay);
        nexttext = findViewById(R.id.Nexttext);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Next.setBackground(getResources().getDrawable(R.drawable.rounded_card_background_enabled));

                // Notification permission not granted, request it

                nexttext.setTextColor(Color.parseColor("#FFFFFF"));
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });


    }


}



