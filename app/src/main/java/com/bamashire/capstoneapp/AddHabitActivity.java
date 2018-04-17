package com.bamashire.capstoneapp;

import android.content.Intent;
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


public class AddHabitActivity extends AppCompatActivity implements OnItemSelectedListener {

    private String habitID;
    private ParseObject habit;
    Spinner timesPerDropdown;
    Spinner freqDropdown;
    Spinner countDropdown;
    ArrayAdapter<String> freqAdapter;
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Habit");


    public static final String EXTRA_MESSAGE = "habit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        if(getIntent().getStringExtra("myhabitName") != null){
            habitID = getIntent().getStringExtra("myhabitID");
            setTitle(getIntent().getStringExtra("myhabitName"));
            EditText habitText = (EditText) findViewById(R.id.EnterHabitNameText);
            habitText.setText(getIntent().getStringExtra("myhabitName"));
        }else{
            setTitle("Create Habit");
        }

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

        //Count spinner setup
        countDropdown = findViewById(R.id.CountSpinner);
        String[] countItems = new String[]{"Times per day", "Minutes per day"};
        ArrayAdapter<String> countAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countItems);
        countDropdown.setAdapter(countAdapter);
        countDropdown.setOnItemSelectedListener(this);
        if(getIntent().getStringExtra("myhabitID") != null) {
            int spinnerPosition = freqAdapter.getPosition(getIntent().getStringExtra("frequency"));
            freqDropdown.setSelection(spinnerPosition);
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
        EditText editText = (EditText) findViewById(R.id.EnterHabitNameText);

        if (editText.getText().toString().matches("")){

            showToast("Please enter a habit name");}
        else {
            Intent intent = new Intent(this, HomeActivity.class);
            String habitName = editText.getText().toString();
            Spinner freqDropdown = findViewById(R.id.FrequencySpinner);
            String spinnerText = freqDropdown.getSelectedItem().toString();
            if(habitID != null) {
                query.getInBackground(habitID, new GetCallback<ParseObject>() {

                    public void done(ParseObject habit, ParseException e) {
                        if (e == null) {
                            habit.put("habitName", habitName);
                            habit.put("streak", Integer.parseInt(getIntent().getStringExtra("streak")));
                            habit.put("frequency", spinnerText);
                            habit.put("ownerID", ParseUser.getCurrentUser().getObjectId());
                            habit.saveInBackground(new SaveCallback() {
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
                        }
                    }
                });
            }else {
                ParseObject object = new ParseObject("Habit");
                object.put("habitName", habitName);
                object.put("streak", 0);
                object.put("frequency", spinnerText);
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
            }




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

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
