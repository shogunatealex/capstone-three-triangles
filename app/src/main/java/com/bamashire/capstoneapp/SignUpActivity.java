package com.bamashire.capstoneapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    public void signUp(View view) {
        EditText usernameText = (EditText) findViewById(R.id.usernameText);
        EditText passwordText = (EditText) findViewById(R.id.passwordText);
        EditText emailText  = (EditText) findViewById(R.id.emailText);
        if (usernameText.getText().toString() == ""|| passwordText.getText().toString() == "" || emailText.getText().toString() == ""){
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
