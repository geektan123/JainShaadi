package com.example.jainshaadi;

import static java.lang.Math.log;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    String POST_NOTIFICATIONS_PERMISSION;
    private int REQUEST_CODE = 11;
    private ActivityResultLauncher<IntentSenderRequest> activityResultLauncher;
    AppUpdateManager appUpdateManager;

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
        checkforupdates();
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

    private void checkforupdates() {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE

                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {

                // Request the update.
                appUpdateManager.startUpdateFlowForResult(
                        // Pass the intent that is returned by 'getAppUpdateInfo()'.
                        appUpdateInfo,
                        // an activity result launcher registered via registerForActivityResult
                        activityResultLauncher,
                        // Or pass 'AppUpdateType.FLEXIBLE' to newBuilder() for
                        // flexible updates.
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE)
                                .setAllowAssetPackDeletion(true)
                                .build());
            }
        });
        registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // handle callback
                        if (result.getResultCode() != RESULT_OK) {
                            // If the update is canceled or fails,
                            // you can request to start the update again.
                        }
                    }
                });

        // Create a listener to track request state updates.
        InstallStateUpdatedListener listener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                // After the update is downloaded, show a notification
                // and request user confirmation to restart the app.
                popupSnackbarForCompleteUpdate();
            }

        };

// Displays the snackbar notification and call to action.


// Before starting an update, register a listener for updates.
        appUpdateManager.registerListener(listener);

// Start an update.

// When status updates are no longer needed, unregister the listener.
        appUpdateManager.unregisterListener(listener);


    }
    @Override
    protected void onStop()
    {
        super.onStop();
    }

    private void popupSnackbarForCompleteUpdate() {
        Snackbar snackbar =
                Snackbar.make(
                        findViewById(android.R.id.content),
                        "An update has just been downloaded.",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(
                getResources().getColor(android.R.color.holo_blue_bright));
        snackbar.show();
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
