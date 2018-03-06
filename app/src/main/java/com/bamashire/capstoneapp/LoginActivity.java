package com.bamashire.capstoneapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {


    Activity mParent = this;
    public void signIn(View view) {
        EditText usernameText = (EditText) findViewById(R.id.usernameText);
        EditText passwordText = (EditText) findViewById(R.id.passwordText);
        EditText emailText  = (EditText) findViewById(R.id.emailText);
        if (usernameText.getText().toString() == "" || passwordText.getText().toString() == "" || emailText.getText().toString() == ""){
           showToast("A username,email, and password are required");
        }
        else {
            ParseUser user = new ParseUser();

            user.setUsername(usernameText.getText().toString());
            user.setPassword(passwordText.getText().toString());
            user.setEmail(emailText.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        Log.i("Signup","Successful");
                        showToast("Signup Successful");

                    }
                    else {
                        showToast("Failed with " + e.getMessage());
                    }
                }
            });
        }
        ActivityUtils.showHomePage(mParent);
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
