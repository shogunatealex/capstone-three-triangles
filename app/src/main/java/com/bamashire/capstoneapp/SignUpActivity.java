package com.bamashire.capstoneapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.loginSwitchText) {
            ActivityUtils.showLogin(this);
            finish();
        }
    }

    Activity mParent = this;
    public void signUp(View view) {
        EditText usernameText = (EditText) findViewById(R.id.usernameText);
        EditText passwordText = (EditText) findViewById(R.id.passwordText);
        EditText emailText  = (EditText) findViewById(R.id.emailText);
        if (usernameText.getText().toString().matches( "") || passwordText.getText().toString().matches("") || emailText.getText().toString().matches("")){
           showToast(getString(R.string.credentials_required));
        }
        else {
            ParseUser user = new ParseUser();

            user.setUsername(usernameText.getText().toString().toLowerCase());
            user.setPassword(passwordText.getText().toString());
            user.setEmail(emailText.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        showToast(getString(R.string.signup_successful));
                        ActivityUtils.showHomePage(mParent);
                        finish();
                    }
                    else {
                        showToast("Failed with " + e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        TextView changeLoginTextView = (TextView) findViewById(R.id.loginSwitchText);
        changeLoginTextView.setOnClickListener(this);
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}
