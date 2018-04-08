package com.bamashire.capstoneapp;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.Games;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView homeRecyclerView;
    private RecyclerView.Adapter homeAdapter;
    private RecyclerView.LayoutManager homeLayoutManager;
<<<<<<< HEAD
    private List<ParseObject> myDataset = new ArrayList<ParseObject>();
=======
>>>>>>> ViewHabit
    Activity mParent = this;


    private void addHabit(){
        //myDataset.add(new Habit(habitName));
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Main Page");
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(ParseUser.getCurrentUser() == null) {
            ActivityUtils.showMainPage(this);
            return;
        }

        //Defining the recyclerview (list of all habits)
        homeRecyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
        homeRecyclerView.setHasFixedSize(true);

        homeLayoutManager = new LinearLayoutManager(this);
        homeRecyclerView.setLayoutManager(homeLayoutManager);

        homeAdapter = new HomeAdapter(myDataset, this);
        homeRecyclerView.setAdapter(homeAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (mParent, AddHabitActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 1);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getHabitsFromDb();

    }

    private void initRecyclerView() {
        //Defining the recyclerview (list of all habits)
        homeRecyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);

        homeLayoutManager = new LinearLayoutManager(this);
        homeRecyclerView.setLayoutManager(homeLayoutManager);

        homeAdapter = new HomeAdapter(myDataset, this);
        homeRecyclerView.setAdapter(homeAdapter);

        swipeController = new HomeSwipeController(new HomeSwipeControllerActions() {
            @Override
            public void onRightClicked(int position, View view) {
                Snackbar.make(view, "You have checked in ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
            getHabitsFromDb();
    }

    private void getHabitsFromDb(){
        //Get User's Habits bellow
        myDataset.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Habit");
        query.whereEqualTo("ownerID", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {

                for(ParseObject object: objects){
                    Log.d("succesfull querry", "done: "+ object.getString("habitName"));
                    addNewHabit(object);
                    //!!!!!!!!!!!
                    //THIS IS WHERE YOU MAKE HABITS WITH "OBJECT"
                    //!!!!!!!!!!!
                    //!!!!!!!!!!!
                }

            }
        });
<<<<<<< HEAD
=======
>>>>>>> ViewHabit
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

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
            ActivityUtils.showMainPage(this);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void addNewHabit(ParseObject habit){
        myDataset.add(habit);
        homeAdapter.notifyDataSetChanged();
    }

    private static final int RC_ACHIEVEMENT_UI = 9003;

    private void showAchievements() {;//TODO: THIS SHOULD BE IN ACTIVITYUTILS
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
}
