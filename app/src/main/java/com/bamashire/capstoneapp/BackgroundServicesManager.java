package com.bamashire.capstoneapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

/**
 * Created by Production on 4/19/2018.
 */


public class BackgroundServicesManager extends BroadcastReceiver{
    private static final int SCHEDULE_CODE = 1;
    private ServiceReminderPostNotificationServiceInfo serviceReminderPostNotificationServiceInfo = new ServiceReminderPostNotificationServiceInfo();

    @Override
    public final void onReceive(Context context, Intent intent) {
        if( IntentConstants.ACTION_SCHEDULE_ALL_SERVICES.equalsIgnoreCase(intent.getAction()) ) {
            scheduleOrCancelService(context, serviceReminderPostNotificationServiceInfo, false);
        }
    }


    private void scheduleOrCancelService(Context context, AbstractServiceInfo serviceInfo, boolean explicitCancel) {
        if( serviceInfo.shouldBeActive(context) ) {
            setServiceAlarm(context, serviceInfo.getNextExecutionDate(context), serviceInfo);
        } else {
            cancelService(context, serviceInfo, explicitCancel);
        }
    }


    private void rescheduleService(Context context, AbstractServiceInfo serviceInfo) {
        if( serviceInfo.shouldBeActive(context) ) {
            setServiceAlarm(context, new Date(System.currentTimeMillis() + serviceInfo.getOnFailureRescheduleTimeMS()), serviceInfo);
        } else {

        }
    }

    private void cancelService(Context context, AbstractServiceInfo serviceInfo, boolean explicitCancel) {
        final Intent alarmIntent = new Intent(serviceInfo.getCallServiceAction(), null, context, serviceInfo.getServiceClass());
        final PendingIntent pendingAlarmIntent = PendingIntent.getService(context, SCHEDULE_CODE, alarmIntent, 0);
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(pendingAlarmIntent);
        if( explicitCancel ) {

        }
    }

    private void immediatelyExecuteService(Context context, AbstractServiceInfo serviceInfo) {
        if( serviceInfo.shouldBeActive(context) ) { // Execute with 2-seconds delay (almost immediately!) if active...
            setServiceAlarm(context, new Date(System.currentTimeMillis() + (2 * 1000)), serviceInfo);
        } else {

        }
    }

    private void setServiceAlarm(Context context, Date executionDate, AbstractServiceInfo serviceInfo) {
        final Intent alarmIntent = new Intent(serviceInfo.getCallServiceAction(), null, context, serviceInfo.getServiceClass());
        final PendingIntent pendingAlarmIntent = PendingIntent.getService(context, SCHEDULE_CODE, alarmIntent, 0);
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final long runFrequencyMS = serviceInfo.getExecutionFrequencyMS(context);

        if( runFrequencyMS>0 ) { // Repeating
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, executionDate.getTime(), runFrequencyMS, pendingAlarmIntent);
        } else { // Non-Repeating
            //alarmManager.set(AlarmManager.RTC_WAKEUP, executionDate.getTime(), pendingAlarmIntent);
            //AppLogger.debug("Service '" + serviceInfo.getServiceClass().getName() + "' has been scheduled to run once on " + executionDate + "!");
            throw new IllegalStateException();
        }
    }
}
