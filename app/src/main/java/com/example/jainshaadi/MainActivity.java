package com.example.jainshaadi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    String POST_NOTIFICATIONS_PERMISSION;
    private int REQUEST_CODE = 11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            POST_NOTIFICATIONS_PERMISSION = android.Manifest.permission.POST_NOTIFICATIONS;
        }else {
            POST_NOTIFICATIONS_PERMISSION = Manifest.permission.ACCESS_NOTIFICATION_POLICY;

        }
        showPermissionDialog();

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

    private void showPermissionDialog() {

        if (ContextCompat.checkSelfPermission(this,POST_NOTIFICATIONS_PERMISSION)  == PackageManager.PERMISSION_GRANTED
        ){

            Toast.makeText(this, "Permission accepted", Toast.LENGTH_SHORT).show();

        }else {
            ActivityCompat.requestPermissions(this, new String[]{ POST_NOTIFICATIONS_PERMISSION },REQUEST_CODE);
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                ){

                }
                else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }

        }else {
            showPermissionDialog();
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
