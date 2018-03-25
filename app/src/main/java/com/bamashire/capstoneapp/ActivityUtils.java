package com.bamashire.capstoneapp;

import android.app.Activity;
import android.content.Intent;

import com.bamashire.capstoneapp.IntentConstants;
import com.bamashire.capstoneapp.SignUpActivity;
import com.bamashire.capstoneapp.SettingsActivity;
import static android.support.v4.app.ActivityCompat.startActivityForResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.parse.ParseUser;


/**
 * Created by Production on 2/27/2018.
 */

public final class ActivityUtils {

    public static void showSignUp(Activity parent) {
        launchActivityImpl(parent, IntentConstants.REQUEST_CODE_NULL, SignUpActivity.class);
    }

    public static void showMainPage(Activity parent) {
        launchActivityImpl(parent, IntentConstants.REQUEST_CODE_NULL, MainActivity.class);
    }


    public static void showHomePage(Activity parent) {
        launchActivityImpl(parent, IntentConstants.REQUEST_CODE_NULL, HomeActivity.class);
    }

    public static void showHomePageStart(Activity parent) {
        Intent intent = new Intent(parent, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        parent.startActivity(intent);
    }

    public static void showHomePageStartGoogle(Activity parent, GoogleSignInAccount account) {
        Intent intent = new Intent(parent, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Login", account);
        // TODO This is jerry rigged fix it. should look up how to pass intent id's
        ParseUser.enableAutomaticUser();
        parent.startActivity(intent);
    }

    public static void showUserSettings(Activity parent) {
        launchActivityImpl(parent, IntentConstants.REQUEST_CODE_NULL, SettingsActivity.class);
    }

    public static void showLogin(Activity parent) {
        launchActivityImpl(parent, IntentConstants.REQUEST_CODE_NULL, LoginActivity.class);
    }

    public static void showAddHabit(Activity parent) {
        launchActivityImpl(parent, IntentConstants.REQUEST_CODE_NULL, AddHabitActivity.class);
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

