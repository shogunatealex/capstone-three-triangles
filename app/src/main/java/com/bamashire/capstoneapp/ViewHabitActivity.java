package com.bamashire.capstoneapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewHabitActivity extends AppCompatActivity {

    private Habit habit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        habit = (Habit) getIntent().getSerializableExtra("habit");

        setTitle(habit.getName());

        populateData();
    }

    private void populateData() {
        TextView description = (TextView) findViewById(R.id.habit_description);
        TextView streak = (TextView) findViewById(R.id.habit_streak);
        TextView frequency = (TextView) findViewById(R.id.habit_frequency);

        description.setText(habit.getDescription());
        streak.setText(Integer.toString(habit.getConsecutiveDays()));
        frequency.setText(Integer.toString(habit.getFrequency()));
    }
}
