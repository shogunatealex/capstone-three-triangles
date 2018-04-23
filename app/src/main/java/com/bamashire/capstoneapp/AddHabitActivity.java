package com.bamashire.capstoneapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Games;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;
import java.util.Map;


public class AddHabitActivity extends AppCompatActivity implements OnItemSelectedListener {

    private String habitID;
    private ParseObject habit;
    Spinner timesPerDropdown;
    Spinner freqDropdown;
    ArrayAdapter<String> freqAdapter;
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Habit");
    public static final String EXTRA_MESSAGE = "habit";
    private GoogleSignInAccount account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        account = GoogleSignIn.getLastSignedInAccount(this);

        //Frequency spinner setup
        freqDropdown = findViewById(R.id.FrequencySpinner);
        String[] freqItems = new String[]{"Daily", "Every other day", "Weekdays", "Weekends", "Frequency per week"};
        freqAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, freqItems);
        freqDropdown.setAdapter(freqAdapter);
        freqDropdown.setOnItemSelectedListener(this);

        //Times per week spinner setup
        timesPerDropdown = findViewById(R.id.TimesPerWeekSpinner);
        String[] timesPerItems = new String[]{"1", "2", "3", "4", "5", "6", "7"};
        ArrayAdapter<String> timesPerWeekAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timesPerItems);
        timesPerDropdown.setAdapter(timesPerWeekAdapter);

        //Either premade habit or an already created habit
        if(getIntent().getStringExtra("myhabitName") != null){
            habitID = getIntent().getStringExtra("myhabitID");
            String habitName = getIntent().getStringExtra("myhabitName");
            setTitle(habitName);
            EditText habitText = (EditText) findViewById(R.id.EnterHabitNameText);
            habitText.setText(habitName);
            EditText countText = (EditText) findViewById(R.id.CountTimesPerDayText);
            EditText editTextDescription = (EditText) findViewById(R.id.EditTextDescription);
            Spinner timesPerWeekSpinner = findViewById(R.id.TimesPerWeekSpinner);
            Spinner freqSpinner = findViewById(R.id.FrequencySpinner);

            //PreMade activity intent
            if(getIntent().getStringExtra("preMade") != null){
                Map<String, List<Integer>> presetHabits = ((ThreeTrianglesApp) this.getApplication()).getPresetHabits();
                freqDropdown.setSelection(presetHabits.get(habitName).get(0));
                if(presetHabits.get(habitName).get(0) == 4) {
                    timesPerWeekSpinner.setVisibility(View.VISIBLE);
                    timesPerDropdown.setSelection(presetHabits.get(habitName).get(1));
                }
                countText.setText(presetHabits.get(habitName).get(2).toString());
            }

            //Comes from HomeActivity
            else{
                countText.setText(getIntent().getStringExtra("perDayCount"));
                editTextDescription.setText(getIntent().getStringExtra("description"));

                if(getIntent().getStringExtra("myhabitID") != null) {
                    String freq = getIntent().getStringExtra("freq");
                    int spinnerPosition = freqAdapter.getPosition(freq);
                    freqDropdown.setSelection(spinnerPosition);
                }
                if(freqDropdown.getSelectedItem().toString().equals("Frequency per week")){
                    int times = Integer.parseInt(getIntent().getStringExtra("timesPerWeek"));
                    timesPerDropdown.setSelection(times - 1);
                }
            }

        }else{
            setTitle("Create Habit");
        }
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                TextView textView = (TextView) findViewById(R.id.TimesInAWeekText);
                Spinner spin = (Spinner) findViewById(R.id.TimesPerWeekSpinner);
                switch (position) {
                    case 0:
                        textView.setVisibility(View.INVISIBLE);
                        spin.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        textView.setVisibility(View.INVISIBLE);
                        spin.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        textView.setVisibility(View.INVISIBLE);
                        spin.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        textView.setVisibility(View.INVISIBLE);
                        spin.setVisibility(View.INVISIBLE);
                        break;
                    case 4:
                        textView.setVisibility(View.VISIBLE);
                        spin.setVisibility(View.VISIBLE);
                }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void addHabit(View view) {
        EditText habitText = (EditText) findViewById(R.id.EnterHabitNameText);
        EditText countText = (EditText) findViewById(R.id.CountTimesPerDayText);
        EditText editTextDescription = (EditText) findViewById(R.id.EditTextDescription);

        if (habitText.getText().toString().matches("")){
            showToast("Please enter a habit name");}
        else if (countText.getText().toString().matches("")){
            showToast("Please enter 'times per day'");}
        else {
            Intent intent = new Intent(this, HomeActivity.class);
            String habitName = habitText.getText().toString();
            Spinner freqDropdown = findViewById(R.id.FrequencySpinner);
            String spinnerText = freqDropdown.getSelectedItem().toString();
            String timesPerText;
            if(spinnerText.equals("Frequency per week")){
                Spinner timesPerWeekDropdown = findViewById(R.id.TimesPerWeekSpinner);
                timesPerText = timesPerWeekDropdown.getSelectedItem().toString();
            }
            else{
                timesPerText = "0";
            }
            if(habitID != null) {
                query.getInBackground(habitID, new GetCallback<ParseObject>() {

                    public void done(ParseObject habit, ParseException e) {
                        if (e == null) {
                            habit.put("habitName", habitName);
                            habit.put("streak", Integer.parseInt(getIntent().getStringExtra("streak")));
                            habit.put("frequency", spinnerText);
                            habit.put("ownerID", ParseUser.getCurrentUser().getObjectId());
                            habit.put("history",getIntent().getSerializableExtra("history"));
                            habit.put("description",editTextDescription.getText().toString());
                            habit.put("perDayCount",Integer.parseInt(countText.getText().toString()));
                            habit.put("timesPerWeek", timesPerText);
                            habit.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException ex) {

                                    if (ex == null){


                                        setResult(RESULT_OK, intent);
                                        Log.i("Parse Result", "Successful!");
                                    } else {
                                        Log.i("Parse Result", "Failed" + ex.toString());
                                    }
                                }
                            });
                        }
                    }
                });
            }else {
                ParseObject object = new ParseObject("Habit");
                object.put("habitName", habitName);
                object.put("streak", 0);
                object.put("frequency", spinnerText);
                object.put("ownerID", ParseUser.getCurrentUser().getObjectId());
                object.put("description",editTextDescription.getText().toString());
                object.put("perDayCount", Integer.parseInt(countText.getText().toString()));
                object.put("timesPerWeek", timesPerText);
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException ex) {
                        if (ex == null) {
                            setResult(RESULT_OK, intent);
                            if(account != null){
                                unlockFirstAchievement();
                                incrementAchievement();
                            }

                            Log.i("Parse Result", "Successful!");
                        } else {
                            Log.i("Parse Result", "Failed" + ex.toString());
                        }
                    }
                });
            }




            finish();
        }
    }

    public void unlockFirstAchievement(){
        Log.d("TAGSE", "unlockFirstAchievement: here1");
//        Games.getGamesClient(this, GoogleSignIn.getLastSignedInAccount(this)).setViewForPopups(this.findViewById(R.layout.activity_home));
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this)).unlock("CgkI0oOo6ZoYEAIQAQ");
    }
    public void incrementAchievement(){
        Log.d("TAGSE", "unlockFirstAchievement: here2");
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this)).increment("CgkI0oOo6ZoYEAIQAg",1);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //Intent intent = new Intent(this, HomeActivity.class);
        //setResult(RESULT_OK, intent);
        finish();
        //NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
