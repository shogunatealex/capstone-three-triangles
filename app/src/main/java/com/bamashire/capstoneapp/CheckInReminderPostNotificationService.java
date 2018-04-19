package com.bamashire.capstoneapp;

import android.app.Activity;
import android.content.Intent;

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

        if (lastNotify.before(date)){
            NotificationUtils.postServiceReminders(this, CHECKIN_ID, "Practice Notification", "This is a practice notification");
            Preferences.setKeyNotificationsLastDate(new Date());
        }

    }
}
