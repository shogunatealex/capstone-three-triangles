package com.bamashire.capstoneapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.db.chart.animation.Animation;
import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.XRenderer;
import com.db.chart.renderer.YRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.view.BarChartView;
import com.db.chart.view.LineChartView;

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


                Snackbar.make(view, "You have checked in with " + habit.getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        habit = (Habit) getIntent().getSerializableExtra("habit");

        setTitle(habit.getName());

        populateData();
        lineGraph();
        barChart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("S", "SETTINGS CLICKED");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateData() {
        TextView description = (TextView) findViewById(R.id.habit_description);
        TextView streak = (TextView) findViewById(R.id.habit_streak);
        TextView frequency = (TextView) findViewById(R.id.habit_frequency);

        description.setText(habit.getDescription());
        streak.setText("Your current streak is " + Integer.toString(habit.getConsecutiveDays()) + " days!");
        frequency.setText("You are expected to check in " + Integer.toString(habit.getFrequency()) + " times a day.");
    }

    private void lineGraph() {
        LineChartView lcv = findViewById(R.id.line_chart);
        String[] mLabels = {"Jan", "Feb", "Mar", "Apr"};

        float[] mValues = {3.5f, 4.7f, 4.3f, 8f};
        LineSet ls = new LineSet(mLabels, mValues);
        ls.setColor(Color.parseColor("#758cbb"))
                .setFill(Color.parseColor("#2d374c"))
                .setDotsColor(Color.parseColor("#758cbb"))
                .setThickness(4)
                .beginAt(0);
        lcv.addData(ls);
        lcv.show();
    }

    private void barChart() {
        String[] mLabels = {"Jan", "Feb", "Mar", "Apr"};
        float[][] mValues = {{6.5f, 8.5f, 2.5f, 10f}, {7.5f, 3.5f, 5.5f, 4f}};
        int[] order = {1, 0, 2, 3};

        BarChartView mChart = (BarChartView) findViewById(R.id.bar_chart);
        BarSet barSet = new BarSet(mLabels, mValues[0]);
        barSet.setColor(Color.parseColor("#fc2a53"));
        mChart.addData(barSet);
        mChart.setYAxis(false);
        mChart.setXAxis(false);
        mChart.setYLabels(YRenderer.LabelPosition.NONE);

        mChart.show();
    }
}
