package com.bamashire.capstoneapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

/**
 * Created by Production on 4/18/2018.
 */

public final class Preferences {
    public static final String APPLICATION_NAME = "threeTriangles";

    private static Context context = null;
    private static PreferenceChangeListener preferenceChangeListener = new PreferenceChangeListener() {
        @Override
        public void preferenceChange(PreferenceChangeEvent evt) {

        }
    };

    public static final String KEY_NOTIFICATIONS_LAST_DATE = "three.triangles.notifications.last-date";


    public static void setKeyNotificationsLastDate(Date lastDate) {
        setDatePreferenceImpl(KEY_NOTIFICATIONS_LAST_DATE, lastDate);
    }

    public static Date getKeyNotificationLastDate() {
        return getDatePreferenceImpl(KEY_NOTIFICATIONS_LAST_DATE);
    }


    public static void initiatePreferences(Context context) {
        Preferences.context = context;
        final SharedPreferences preferences = getSharedPreferences();
        final SharedPreferences.Editor editor = preferences.edit();
        createNewPreferences(preferences, editor);
        editor.commit();
    }

    public static void createNewPreferences(SharedPreferences preferences, SharedPreferences.Editor editor) {
        if (!preferences.contains(KEY_NOTIFICATIONS_LAST_DATE)){
            editor.putLong(KEY_NOTIFICATIONS_LAST_DATE, new Date().getTime()); // Sets to current time today.
        }
    }

    private static Date getDatePreferenceImpl(String key) {
        final long date = getSharedPreferences().getLong(key, -1);

        return (date!=-1) ? new Date(date) : null;
    }

    public static SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(APPLICATION_NAME, Context.MODE_PRIVATE);
    }

    private static void setDatePreferenceImpl(String key, Date value) {
        final SharedPreferences.Editor editor = getSharedPreferences().edit();

        if( value!=null ) {
            editor.putLong(key, value.getTime());
        } else {
            editor.remove(key);
        }
        editor.commit();
    }

}
