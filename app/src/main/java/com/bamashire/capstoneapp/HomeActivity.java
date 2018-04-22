package com.bamashire.capstoneapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView homeRecyclerView;
    private HomeAdapter homeAdapter;
    private RecyclerView.LayoutManager homeLayoutManager;
    private List<ParseObject> myDataset = new ArrayList<ParseObject>();
    private HomeSwipeController swipeController;
    private ItemTouchHelper itemTouchhelper;
    Activity mParent = this;

    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings;
    private LinearLayout layoutFabCustom;
    private LinearLayout layoutFabPremade;
    private GoogleSignInAccount account;

    private void addHabit(){
        //myDataset.add(new Habit(habitName));
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        account = GoogleSignIn.getLastSignedInAccount(this);
        super.onCreate(savedInstanceState);
        setTitle("Main Page");
        setContentView(R.layout.activity_home);
        Games.getGamesClient(this, GoogleSignIn.getLastSignedInAccount(this)).setViewForPopups(findViewById(android.R.id.content));
        Games.getGamesClient(this, GoogleSignIn.getLastSignedInAccount(this)).setGravityForPopups(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(ParseUser.getCurrentUser() == null) {
            ThreeTrianglesApp.mGoogleSignInClient.signOut();
            ActivityUtils.showMainPage(this);
            return;
        }

        initRecyclerView();
        BackgroundServiceUtils.startBackgroundServices(this);

        fabSettings = (FloatingActionButton) this.findViewById(R.id.fabAddHabit);
        layoutFabCustom = (LinearLayout) this.findViewById(R.id.layoutFabCustom);
        layoutFabPremade = (LinearLayout) this.findViewById(R.id.layoutFabPremade);

        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });

        layoutFabCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (mParent, AddHabitActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 1);
                closeSubMenusFab();
            }
        });

        layoutFabPremade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (mParent, PreMadeHabitActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 1);
                closeSubMenusFab();
            }
        });

        //Only main FAB is visible in the beginning
        closeSubMenusFab();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);

        TextView drawerName = (TextView) hView.findViewById(R.id.drawer_name);
        TextView drawerEmail = (TextView) hView.findViewById(R.id.drawer_email);

        drawerName.setText(ParseUser.getCurrentUser().getUsername());
        drawerEmail.setText(ParseUser.getCurrentUser().getEmail());

    }

    //closes FAB submenus
    private void closeSubMenusFab(){
        layoutFabCustom.setVisibility(View.INVISIBLE);
        layoutFabPremade.setVisibility(View.INVISIBLE);
        fabSettings.setImageResource(R.drawable.ic_fab_plus);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab() {
        layoutFabCustom.setVisibility(View.VISIBLE);
        layoutFabPremade.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fabSettings.setImageResource(R.drawable.ic_clear);
        fabExpanded = true;
    }

    //Initializes the recyclerview
    private void initRecyclerView() {
        //Defining the recyclerview (list of all habits)
        homeRecyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);

        homeLayoutManager = new LinearLayoutManager(this);
        homeRecyclerView.setLayoutManager(homeLayoutManager);

        homeAdapter = new HomeAdapter(myDataset, this);
        homeRecyclerView.setAdapter(homeAdapter);

        //Adds swipe actions to each item on the list
        swipeController = new HomeSwipeController(new HomeSwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                homeAdapter.habits.get(position).deleteInBackground();
                homeAdapter.habits.remove(position);
                homeAdapter.notifyItemRemoved(position);
                homeAdapter.notifyItemRangeChanged(position, homeAdapter.getItemCount());
            }
            @Override
            public void onRightClicked(int position, View view) {
                ParseObject habit = homeAdapter.habits.get(position);
                ArrayList<String> dates = (ArrayList<String>) habit.get("history");
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());

                if (dates == null || dates.size() == 0) {
                    if(account != null){
                        incrementCheckinAchievements();
                    }
                    habit.increment("streak");
                    habit.add("history", formattedDate);
                    Snackbar.make(view, "You have checked in with " + habit.getString("habitName"), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (checkInDateRange(habit)) {
                    Snackbar.make(view, "You have already checked in today.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    if(account != null){
                        incrementCheckinAchievements();
                    }
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
                getHabitsFromDb();
                homeAdapter.notifyItemRangeChanged(position, homeAdapter.getItemCount());
            }
            public boolean checkInDateRange(ParseObject habit){
                ArrayList<String> dates = (ArrayList<String>) habit.get("history");
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                Log.i("test", ""+habit.get("perDayCount"));


                //day
                if(habit.getString("frequency").equals("Daily") && ((int) habit.get("perDayCount"))==1){
                    return dates.get(dates.size() - 1).split(" ")[0].equals(formattedDate.split(" ")[0]);
                }else{
                    int counter = 0;
                    int i = 0;
                    while(i< dates.size() -1 && dates.get(dates.size() - i-1).split(" ")[0].equals(formattedDate.split(" ")[0])){
                        if(dates.get(dates.size() - i-1).split(" ")[0].equals(formattedDate.split(" ")[0])){
                            counter++;
                            if(counter >= (Integer.parseInt(habit.get("perDayCount").toString()))-1){
                                return true;
                            }
                        }
                        i++;
                    }
                }
                return false;
            }
        });


        itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(homeRecyclerView);

        homeRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ThreeTrianglesApp.mGoogleSignInClient.silentSignIn();
        getHabitsFromDb();
        if (homeAdapter != null){
            homeAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void getHabitsFromDb(){
        //Get User's Habits bellow
        myDataset.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Habit");
        if(ParseUser.getCurrentUser() == null){
            return;
        }
        query.whereEqualTo("ownerID", ParseUser.getCurrentUser().getObjectId());
        //Goes through the database, grabs each entry, and stores them in the list for the home adapter to use
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {

                if (objects != null){
                    for(ParseObject object: objects){
                        Log.d("succesfull querry", "done: "+ object.getString("habitName"));
                        addNewHabit(object);
                    }
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        GoogleSignInAccount x = GoogleSignIn.getLastSignedInAccount(this);
        if (id == R.id.nav_manage) {
            ActivityUtils.showUserSettings(mParent);
        } else if (id == R.id.nav_share) {


        }
        else if (id == R.id.nav_achieve){
            if(GoogleSignIn.getLastSignedInAccount(this) == null){
                showToast("You must be logged into Google in order to use this feature");
                return false;
            }
            else{
                showAchievements();
            }

        }
        else if (id == R.id.nav_sign_out){
            ParseUser.logOut();
            ThreeTrianglesApp.mGoogleSignInClient.signOut();
            ActivityUtils.showMainPage(this);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Adds the habit to the home adapter list and tells it to refresh
    public void addNewHabit(ParseObject habit){
        myDataset.add(habit);
        homeAdapter.notifyDataSetChanged();
    }

    private static final int RC_ACHIEVEMENT_UI = 9003;

    private void showAchievements() {
        //TODO: THIS SHOULD BE IN ACTIVITYUTILS
        final Task<Intent> intentTask = Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getAchievementsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void incrementCheckinAchievements(){
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this)).increment("CgkI0oOo6ZoYEAIQBA",1);
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this)).increment("CgkI0oOo6ZoYEAIQBQ",1);
    }
}
