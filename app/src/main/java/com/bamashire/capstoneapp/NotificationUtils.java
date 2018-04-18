package com.bamashire.capstoneapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Production on 4/18/2018.
 */

public final class NotificationUtils {

    public static void postServiceReminders(Context context, int notificationId, String title, String content, Activity parent) {
        final PendingIntent contentIntent = PendingIntent.getActivity(context, notificationId, new Intent(context, parent.getClass()), PendingIntent.FLAG_CANCEL_CURRENT);
        final Notification.Builder notificationBuilder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.app_logo)
                .setTicker(title)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(title + "...")
                .setContentText(content)
                .setContentIntent(contentIntent);

//        if( Preferences.hasStatusBarNotificationLED() ) {
//            notificationBuilder.setLights(ledColor, 500, 3000);
//        }

        getNotificationManager(context).notify(notificationId, notificationBuilder.getNotification());
    }

    private static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
