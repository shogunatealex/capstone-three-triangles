package com.bamashire.capstoneapp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.bamashire.capstoneapp.IntentConstants;
import com.bamashire.capstoneapp.SignUpActivity;
import com.bamashire.capstoneapp.SettingsActivity;
import static android.support.v4.app.ActivityCompat.startActivityForResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


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
        if (account != null) {
            String passwordID = account.getId();
            String username = account.getEmail().toLowerCase().replace("@", "");
            String email = account.getEmail().toLowerCase();

            Log.d("GoogleInfo", passwordID + email + username);

            ParseUser user = new ParseUser();

            user.setUsername(username);
            user.setPassword(passwordID);
            user.setEmail(email);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        parent.startActivity(intent);
                    } else {
                        ParseUser.logInInBackground(username, passwordID, new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    parent.startActivity(intent);
                                }
                            }
                        });
                    }
                }
            });
        }
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

