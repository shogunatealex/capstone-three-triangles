package com.bamashire.capstoneapp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

/**
 * Created by Production on 4/19/2018.
 */

public class CheckInReminderPostNotificationService extends LongRunService {
    public static final int CHECKIN_ID = 4932;

    public CheckInReminderPostNotificationService() {
    }

    @Override
    protected String getActionName() {
        return IntentConstants.ACTION_REMINDER_POST_NOTIFICATIONS;
    }

    @Override
    protected void onStartImpl(Intent intent, int startId) throws Exception {

        Date date = new Date();
        Date lastNotify = Preferences.getKeyNotificationLastDate();
        Log.d("Tag", "onStartImpl: ");
        if (lastNotify.before(date)){
            NotificationUtils.postServiceReminders(this, CHECKIN_ID, "Check-In", "Remember to Check in for your habits!" );
            Preferences.setKeyNotificationsLastDate(new Date());
        }

    }
}
