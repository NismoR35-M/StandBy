package com.blankScreen.standby.settings;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.blankScreen.standby.R;
import com.blankScreen.standby.activities.MainActivity;
import com.blankScreen.standby.utils.Constants;

public class NoCloseOptionSelectedNotification {

    Intent intent;
    PendingIntent pendingIntent;
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;

    public NoCloseOptionSelectedNotification(Context context) {
        intent = new Intent(context, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel errors = new NotificationChannel("errors",
                    context.getString(R.string.notification_channel_errors_name),
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(errors);

            builder = new NotificationCompat.Builder(context, "errors")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(context.getString(R.string.close_option_error_no_option_selected_title))
                    .setContentText(context.getString(R.string.close_option_error_no_option_selected_description))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setChannelId("errors");
        } else {
            builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(context.getString(R.string.close_option_error_no_option_selected_title))
                    .setContentText(context.getString(R.string.close_option_error_no_option_selected_description))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager = NotificationManagerCompat.from(context);
    }
    public void drop() {
        notificationManager.notify(Constants.Notification.ID, builder.build());
    }
}
