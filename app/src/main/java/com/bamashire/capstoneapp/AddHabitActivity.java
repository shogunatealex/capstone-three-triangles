package com.bamashire.capstoneapp;

import android.app.Activity;
import android.content.Intent;
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

public class AddHabitActivity extends AppCompatActivity {

    Activity mParent = this;
    public Habit addHabit(View view) {
        EditText HabitNameText = (EditText) findViewById(R.id.HabitNameText);

        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("habitName", HabitNameText.getText().toString());

        Habit aHabit = new Habit(HabitNameText.getText().toString());
        if (HabitNameText.getText().toString() == ("")){

            showToast("A habit name is required");
        }
        else {
            ParseUser user = new ParseUser();


//            user.addHabitInBackground(new SignUpCallback() {
//                @Override
//                public void done(ParseException e) {
//                    if (e == null){
//                        Log.i("AddHabit","Successful");
//                        showToast("AddHabit Successful");
//
//                    }
//                    else {
//                        showToast("Failed with " + e.getMessage());
//                    }
//                }
//            });
        }

        ActivityUtils.showHomePage(mParent);
        return aHabit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
