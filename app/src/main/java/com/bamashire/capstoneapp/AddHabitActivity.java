package com.bamashire.capstoneapp;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class AddHabitActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "habit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void addHabit(View view) {
        EditText editText = (EditText) findViewById(R.id.EnterHabitNameText);

        if (editText.getText().toString().matches("")){

            showToast("Please enter a habit name");}
        else {
            Intent intent = new Intent(this, HomeActivity.class);
            String habitName = editText.getText().toString();

            ParseObject object = new ParseObject("Habit");
            object.put("habitName", habitName);
            object.put("streak",0);
            object.put("ownerID", ParseUser.getCurrentUser().getObjectId());

            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException ex) {
                    if (ex == null) {
                        setResult(RESULT_OK, intent);
                        Log.i("Parse Result", "Successful!");
                    } else {
                        Log.i("Parse Result", "Failed" + ex.toString());
                    }
                }
            });



            finish();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //Intent intent = new Intent(this, HomeActivity.class);
        //setResult(RESULT_OK, intent);
        finish();
        //NavUtils.navigateUpFromSameTask(this);
    }
}
