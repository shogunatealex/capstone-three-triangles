package com.bamashire.capstoneapp;

import android.app.Activity;
import android.content.Intent;

import com.bamashire.capstoneapp.IntentConstants;
import com.bamashire.capstoneapp.SignUpActivity;
import com.bamashire.capstoneapp.SettingsActivity;
/**
 * Created by Production on 2/27/2018.
 */

public final class ActivityUtils {

    public static void showSignUp(Activity parent) {
        launchActivityImpl(parent, IntentConstants.REQUEST_CODE_NULL, SignUpActivity.class);
    }

    public static void showHomePage(Activity parent) {
        launchActivityImpl(parent, IntentConstants.REQUEST_CODE_NULL, HomeActivity.class);
    }
    public static void showUserSettings(Activity parent) {
        launchActivityImpl(parent, IntentConstants.REQUEST_CODE_NULL, SettingsActivity.class);
    }

    private static void launchActivityImpl(Activity parent, int requestCode, Intent intent) {
        if (requestCode == IntentConstants.REQUEST_CODE_NULL) {
            parent.startActivity(intent);
        } else {
            parent.startActivityForResult(intent, requestCode);
        }
    }

    private static void launchActivityImpl(Activity parent, int requestCode, Class activityClass) {
        launchActivityImpl(parent, requestCode, activityClass, null);
    }

    private static void launchActivityImpl(Activity parent, int requestCode, Class activityClass, String action) {
        final Intent intent = new Intent(parent, activityClass);

        if( action!=null ) {
            intent.setAction(action);
        }

        launchActivityImpl(parent, requestCode, intent);
    }
}

