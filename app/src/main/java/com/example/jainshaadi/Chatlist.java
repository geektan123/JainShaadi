package com.example.jainshaadi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Chatlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlist);
        getSupportActionBar().hide();

    }
}