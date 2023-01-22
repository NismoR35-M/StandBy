package com.blankScreen.standby.permissions;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import androidx.appcompat.view.ContextThemeWrapper;

import com.blankScreen.standby.R;
import com.blankScreen.standby.services.OverlayService;

import java.util.List;

public class Permission {

    /*Check if accessibility service is running*/
    public static boolean isAccessibilityServiceRunning(Context c){
        boolean running = OverlayService.running;
        if (running) {
            return true;
        }else
            return false;
    }
    /*Confirm accessibility*/
    public static boolean checkPermissionOverlay(Context context) {
        Log.i("PermissionUtils", "Checking if application hast permission to draw overlays...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(context)) {
                Log.i("PermissionUtils", "Permission is given!");
                return true;
            }
        }
        Log.i("PermissionUtils", "Permission is not given!");
        return false;
    }

    /*Permission overlay*/
    public static AlertDialog getPermissionOverlayRequestAlertDialog(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme_Dialog)).create();
        alertDialog.setTitle(context.getString(R.string.accessibility_error_no_overlay_permission_title));
        alertDialog.setMessage(context.getString(R.string.accessibility_error_no_overlay_permission_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(android.R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.accessibility_error_settings_label),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        Intent i = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(i);
                    }
                });
        return alertDialog;
    }

    /*Accessibility not Enabled*/
    public static AlertDialog getAccessibilityServiceNotEnabledAlertDialog(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme_Dialog)).create();
        alertDialog.setTitle(context.getString(R.string.accessibility_error_not_enabled_title));
        alertDialog.setMessage(context.getString(R.string.accessibility_error_not_enabled_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(android.R.string.cancel),
                (dialog, which) -> dialog.dismiss());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.accessibility_error_settings_label),
                (dialog, which) -> {
                    dialog.dismiss();

                    Intent i = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(i);
                });
        return alertDialog;
    }

    public static boolean checkAccessibilityServiceEnabled(Context context) {
        Log.i("PermissionUtils", "Checking if accessibility service is enabled...");
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        for (AccessibilityServiceInfo enabledService : enabledServices) {
            ServiceInfo enabledServiceInfo = enabledService.getResolveInfo().serviceInfo;
            Log.i("PermissionUtils", "Found " + enabledServiceInfo.processName);
            if (enabledServiceInfo.packageName.equals(context.getPackageName()) && enabledServiceInfo.name.equals(OverlayService.class.getName())) {
                Log.i("PermissionUtils", "Accessibility service enabled!");
                return true;
            }
        }
        Log.e("PermissionUtils", "Accessibility service is not enabled!");
        return false;
    }

    public static AlertDialog getAccessibilityServiceNotRunningAlertDialog(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme_Dialog)).create();
        alertDialog.setTitle(context.getString(R.string.accessibility_error_not_running_title));
        alertDialog.setMessage(context.getString(R.string.accessibility_error_not_running_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return alertDialog;
    }
}
