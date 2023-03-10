package com.blankScreen.standby.permissions;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AccessibilityServiceNotRunningDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return Permission.getAccessibilityServiceNotRunningAlertDialog(getContext());
    }

}
