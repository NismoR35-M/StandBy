package com.blankScreen.standby.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.blankScreen.standby.R;
import com.blankScreen.standby.permissions.AccessibilityServiceNotEnabledDialog;
import com.blankScreen.standby.permissions.AccessibilityServiceNotRunningDialog;
import com.blankScreen.standby.permissions.OverlayPermissionRequiredDialog;
import com.blankScreen.standby.permissions.Permission;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //enabling standby accessibility
        MaterialCardView enable_stand = findViewById(R.id.enable_switch);
        enable_stand.setOnClickListener(v -> {
            /*Check conditions are met*/
            if (!checkConditions()){
                return;
            }
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, "Installed Apps -> StandBy", Toast.LENGTH_SHORT).show();
        });

    }

    private boolean checkConditions() {
        //if accessibility servide is ON
        if (!Permission.isAccessibilityServiceRunning(this)){
            if (!Permission.checkAccessibilityServiceEnabled(this)) {
                Log.i(getClass().getName(), "Service is not enabled. Prompting the user...");
                DialogFragment CASE = new AccessibilityServiceNotEnabledDialog();
                CASE.show(getSupportFragmentManager(), "accessibility_service_not_enabled");
                return false;
            }
            Log.i(getClass().getName(), "Service is not running. Prompting the user...");
            DialogFragment CASR = new AccessibilityServiceNotRunningDialog();
            CASR.show(getSupportFragmentManager(), "accessibility_service_not_running");
            return false;
        }
        if (!Permission.checkPermissionOverlay(this)) {
            Log.i(getClass().getName(), "No Overlay permission. Prompting the user...");
            DialogFragment CPO = new OverlayPermissionRequiredDialog();
            CPO.show(getSupportFragmentManager(), "overlay_permission_required");
            return false;
        }
        Log.i(getClass().getName(), "Everything is fine. Overlay can be launched.");
        return true;
    }
}