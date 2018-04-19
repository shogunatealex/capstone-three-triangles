package com.bamashire.capstoneapp;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Production on 4/19/2018.
 */

public class BackgroundServiceUtils {
    public static void startBackgroundServices(Context context) {
        context.sendBroadcast(new Intent(IntentConstants.ACTION_SCHEDULE_ALL_SERVICES, null, context, BackgroundServicesManager.class));
    }

    public static void stopBackgroundServices(Context context) {
        context.sendBroadcast(new Intent(IntentConstants.ACTION_CANCEL_ALL_SERVICES, null, context, BackgroundServicesManager.class));
    }

}
