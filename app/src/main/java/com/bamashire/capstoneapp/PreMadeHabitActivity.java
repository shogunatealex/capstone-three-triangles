package com.bamashire.capstoneapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//public static final String EXTRA_MESSAGE = "premade_habit";
public class PreMadeHabitActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Premade Habits");
        setContentView(R.layout.premade_habit);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void preMadeHabit(View view) {

        Button b = (Button)view;
        String buttonText = b.getText().toString();
        Intent intent = new Intent(this, AddHabitActivity.class);
        intent.putExtra("myhabitName", buttonText);
        this.startActivity(intent);
        finish();
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
