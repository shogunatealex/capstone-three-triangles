package com.bamashire.capstoneapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {


    Activity mParent = this;
    public void signIn(View view) {
        EditText usernameText = (EditText) findViewById(R.id.usernameLoginText);
        EditText passwordText = (EditText) findViewById(R.id.passwordLoginText);
        if (usernameText.getText().toString().matches("") || passwordText.getText().toString().matches("")){
           showToast("A username,email, and password are required");
        }
        else {
            ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null){
                        showToast("Successful Login");
                        ActivityUtils.showHomePage(mParent);
                    }
                    else {
                        showToast(e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
