package com.blankScreen.standby.tile;

import android.content.Context;
import android.content.Intent;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import com.blankScreen.standby.permissions.Permission;
import com.blankScreen.standby.services.OverlayService;
import com.blankScreen.standby.utils.Constants;

public class OverlayQuickTile extends TileService {

    private Context context = null;
    private Runnable start_overlay = new Runnable() {
        @Override
        public void run() {
            if (!checkConditions()) {
                return;
            }
            Intent i = new Intent(context, OverlayService.class);
            i.putExtra(Constants.Intent.Extra.OverlayAction.KEY, Constants.Intent.Extra.OverlayAction.SHOW);
            startService(i);
        }
    };

    public boolean checkConditions(){
        Log.i(getClass().getName(), "Check if permission is given and service is running");
        if (!Permission.isAccessibilityServiceRunning(context)){
            if (!Permission.checkAccessibilityServiceEnabled(this)) {
                Log.i(getClass().getName(),"Service not enabled.Prompt user..");
                showDialog(Permission.getAccessibilityServiceNotEnabledAlertDialog(context));
                return false;
            }
            showDialog(Permission.getAccessibilityServiceNotRunningAlertDialog(context));
            return false;
        }
        if (!Permission.checkPermissionOverlay(context)){
            showDialog(Permission.getPermissionOverlayRequestAlertDialog(context));
            return false;
        }
        //Good to go,launch overlay
        return true;
    }

    @Override
    public void onTileAdded() {
        onStartListening();
    }

    @Override
    public void onStartListening() {
        getQsTile().setState(Tile.STATE_INACTIVE);
        getQsTile().updateTile();
    }
}
