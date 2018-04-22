package com.bamashire.capstoneapp;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

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
    public boolean LockQuery;
    private ArrayList<String> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LockQuery = false;
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
                if (!LockQuery){
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

                    try {
                        habit.save();
                    } catch (ParseException e) {
                        habit.saveInBackground();
                    }

                    callApi();
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void callApi(){
        LockQuery = true;
        habitID = getIntent().getStringExtra("myhabit");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Habit");
        query.whereEqualTo("objectId", habitID);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                LockQuery = false;
                for(ParseObject object: objects){
                    habit = object;
                    Log.d("succesfull querry", "done: "+ object.getString("habitName"));

                    history = (ArrayList<String>) habit.get("history");

                    ImageView ThreeTriangleButtons = (findViewById(R.id.ThreeTriangleImage));
                    int i = Integer.parseInt(habit.get("streak").toString());
                    int resId;
                    String packageName = getPackageName();
                    resId = getResources().getIdentifier("triangle" + String.valueOf(i), "drawable", packageName);
                    ThreeTriangleButtons.setImageResource(resId);

                }
                populateData();
                calendarChange();
            }
        });
    }

    public void calendarChange() {
        CalendarView cv = findViewById(R.id.calendar);
        TextView datetext = findViewById(R.id.calendar_date);
        TextView historytext = findViewById(R.id.calendar_history);
        SimpleDateFormat date = new SimpleDateFormat("EEEE, MMMM d");
        SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFull = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        SimpleDateFormat time = new SimpleDateFormat("h:mm aa");

        Date d = new Date(cv.getDate());
        ArrayList<String> dates = getCheckInHistory(dateF.format(d));
        if (dates.size() == 0) {
            historytext.setText("You did not check in on this day");
        } else {
            historytext.setText("You checked in at " + dates.toString());
        }
        datetext.setText(date.format(d));

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String dateCurrent = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(dayOfMonth);
                try {
                    Date d = dateF.parse(dateCurrent);
                    ArrayList<String> dates = getCheckInHistory(dateF.format(d));

                    if (dates.size() == 0) {
                        historytext.setText("You did not check in on this day");
                    } else {
                        String text = "You checked in at ";
                        int count = 0;
                        for (String item : dates) {
                            Date nd = dateFull.parse(item);
                            if (count == 0) {
                                text += time.format(nd);
                            } else if (count >= 1) {
                                text += ", " + time.format(nd);
                            }
                            count++;

                        }
                        historytext.setText(text);

                    }
                    datetext.setText(date.format(d));
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ArrayList<String> getCheckInHistory(String date) {
        ArrayList<String> dates = new ArrayList<String>();
        for (String item: history) {
            if (item.contains(date)) {
                dates.add(item);
            }
        }

        return dates;
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
        if (id == R.id.action_edit) {
            Intent i = new Intent(this, AddHabitActivity.class);
            i.putExtra("myhabitID", habit.getObjectId());
            i.putExtra("myhabitName", habit.getString("habitName"));
            i.putExtra("description", habit.getString("description"));
            i.putExtra("freq", habit.getString("frequency"));
            i.putExtra("description", habit.getString("description"));
            i.putExtra("streak", Integer.toString((Integer) habit.get("streak")));
            i.putExtra("perDayCount", Integer.toString((Integer) habit.get("perDayCount")));

            i.putExtra("history", (Serializable) habit.get("history"));
            Log.i("history",habit.get("history").toString());

            this.startActivity(i);
            return true;
        } else if (id == R.id.action_delete) {
            habit.deleteInBackground();
            finish();
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
        description.setText(habit.getString("description"));
    }

}
