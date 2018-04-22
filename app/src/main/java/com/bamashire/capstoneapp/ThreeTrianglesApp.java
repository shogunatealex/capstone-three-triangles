package com.bamashire.capstoneapp;
/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

import android.app.Application;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.AchievementsClient;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import android.content.Context;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ThreeTrianglesApp extends Application {

        public static GoogleSignInClient mGoogleSignInClient;
        public static AchievementsClient mAchievementsClient;
        public static AchievementUnlocked achievementInfo;
        public Map<String, List<Integer>> presetHabits = new HashMap<String, List<Integer>>();


    @Override
    public void onCreate() {
        super.onCreate();
        Preferences.initiatePreferences(this);
        achievementInfo = new AchievementUnlocked(this);
        achievementInfo.setRounded(true).setLarge(true).setTopAligned(true).setDismissible(true);

        presetHabits.put("Dust",makeList(4,1,1));
        presetHabits.put("Vaccum",makeList(4,1,1));
        presetHabits.put("Meditate",makeList(4,0,1));
        presetHabits.put("Call Friend",makeList(4,2,1));
        presetHabits.put("Play Instrument",makeList(4,3,1));
        presetHabits.put("Read",makeList(1,0,1));
        presetHabits.put("Yoga",makeList(1,0,1));
        presetHabits.put("Run",makeList(1,0,1));
        presetHabits.put("Get 10,000 Steps",makeList(0,0,1));
        presetHabits.put("Exercise",makeList(0,0,1));
        presetHabits.put("Wake Up Early",makeList(0,0,1));
        presetHabits.put("8 Hours Of Sleep",makeList(0,0,1));
        presetHabits.put("No Alcohol",makeList(0,0,1));
        presetHabits.put("Quit Smoking",makeList(0,0,1));
        presetHabits.put("No Caffeine",makeList(0,0,1));
        presetHabits.put("Drink More Water",makeList(0,0,1));
        presetHabits.put("Take Multivitamin",makeList(0,0,1));
        presetHabits.put("Eat Breakfast",makeList(0,0,1));
        presetHabits.put("Eat More Veggies",makeList(0,0,3));
        presetHabits.put("Eat More Fruit",makeList(0,0,2));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN )
                .requestEmail()
                .requestId()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("f4d9d4630ecb78fad451d3f1390014a52f577cc2")
                .clientKey("6af300f5d01c48c45b865d49361f1c96f43f1c07")
                .server("http://18.219.221.226:80/parse/")
                .build()
        );

//        ParseObject object = new ParseObject("ExampleObject");
//        object.put("myNumber", "123");
//        object.put("myString", "rob");

//        object.saveInBackground(new SaveCallback () {
//            @Override
//            public void done(ParseException ex) {
//                if (ex == null) {
//                    Log.i("Parse Result", "Successful!");
//                } else {
//                    Log.i("Parse Result", "Failed" + ex.toString());
//                }
//            }
//        });

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        showToast("Database Connected");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public Map<String, List<Integer>> getPresetHabits(){
        return presetHabits;
    }

    private List makeList(Integer a, Integer b, Integer c)
    {
        List premadeList = new ArrayList();
        premadeList.add(a);
        premadeList.add(b);
        premadeList.add(c);
        return premadeList;
    }
}
