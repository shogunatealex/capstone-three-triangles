package com.bamashire.capstoneapp;

import android.graphics.Color;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.db.chart.animation.Animation;
import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.XRenderer;
import com.db.chart.renderer.YRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.view.BarChartView;
import com.db.chart.view.LineChartView;

import static java.lang.Thread.sleep;

public class ViewHabitActivity extends AppCompatActivity {

    private String habitID;
    private ParseObject habit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(getIntent().getStringExtra("habitName"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        callApi();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> dates = (ArrayList<String>) habit.get("history");
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());

                if (dates == null || dates.size() == 0) {
                    habit.increment("streak");
                    habit.add("history", formattedDate);
                    Snackbar.make(view, "You have checked in with " + habit.getString("habitName"), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (dates.get(dates.size() - 1).split(" ")[0].equals(formattedDate.split(" ")[0])) {
                    Snackbar.make(view, "You have already checked in today.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    habit.increment("streak");
                    habit.add("history", formattedDate);
                    Snackbar.make(view, "You have checked in with " + habit.getString("habitName"), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                habit.saveInBackground();

                callApi();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView ThreeTriangleButtons = (findViewById(R.id.three_triangles_image));
        int resId;
        int i = 50;
        String packageName = getPackageName();
        resId = getResources().getIdentifier("triangle" + String.valueOf(i), "drawable", packageName);
        ThreeTriangleButtons.setImageResource(resId);
        lineGraph();
        barChart();
    }
    public void callApi(){

        habitID = getIntent().getStringExtra("myhabit");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Habit");
        query.whereEqualTo("objectId", habitID);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                for(ParseObject object: objects){
                    habit = object;
                    Log.d("succesfull querry", "done: "+ object.getString("habitName"));
                }
                populateData();
            }
        });
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

        description.setText(habit.getString("description"));
        streak.setText("Your current streak is " + habit.getNumber("streak") + " days!");
        frequency.setText("You are expected to check in " + habit.getString("frequency"));
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
