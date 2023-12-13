package com.example.jainshaadi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.action_home) {
                    selectedFragment = new HomeFragment();
                } else if (item.getItemId() == R.id.action_search) {
                    selectedFragment = new SearchFragment();
                } else if (item.getItemId() == R.id.action_chat) {
                    selectedFragment = new Chatlist();
                }
                else if(item.getItemId() == R.id.action_profile) {
                    startActivity(new Intent(MainActivity.this, MyProfile.class));
                    return false;
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, selectedFragment)
                            .commit();
                }

                return true;
            }
        });


        if (getIntent() != null && getIntent().getAction() != null) {
            handleIntent(getIntent());
        } else {
            // Set the default fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new HomeFragment())
                    .commit();
        }

    }
    private void handleIntent(Intent intent) {
        // Handle the intent and navigate to the appropriate fragment
        String action = intent.getAction();
        if (action != null && action.equals("ACTION_SHOW_SEARCH_FRAGMENT")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new SearchFragment())
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.action_search);
        }
    }
}
