package com.bamashire.capstoneapp;

import android.app.AlarmManager;
import android.content.Context;

import java.util.Date;

/**
 * Created by Production on 4/19/2018.
 */

public class ServiceReminderPostNotificationServiceInfo extends AbstractServiceInfo{
    public Class getServiceClass() {
        return CheckInReminderPostNotificationService.class;
    }

    public String getCallServiceAction() {
        return IntentConstants.ACTION_REMINDER_POST_NOTIFICATIONS;
    }

    public boolean shouldBeActive(Context context) {
        return true;
    }

    public long getExecutionFrequencyMS(Context context) {
        return 1 * 1000;
    }

    protected long getFirstEverExecutionTimeMS() {
        return 5 * 1000;
    }

    protected Date getLastExecutionDate(Context context) {
        return Preferences.getKeyNotificationLastDate();
    }

}
