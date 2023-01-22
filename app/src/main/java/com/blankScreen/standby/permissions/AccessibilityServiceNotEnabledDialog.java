package com.blankScreen.standby.permissions;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AccessibilityServiceNotEnabledDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        return Permission.getAccessibilityServiceNotEnabledAlertDialog(getContext());
    }
}
