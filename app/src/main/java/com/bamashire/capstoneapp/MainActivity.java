package com.bamashire.capstoneapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.parse.ParseAnalytics;

public class MainActivity extends AppCompatActivity {
    public static final int RC_SIGN_IN = 3951;
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("831833899474-tiaahf6huam5gf0hgk08p4e0v1v728hc.apps.googleusercontent.com")
            .requestEmail()
            .requestId()
            .build();


    Activity mParent = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        super.onCreate(savedInstanceState);


        setContentView(R.layout.main_activity);

        findViewById(R.id.sign_in_google_button).setOnClickListener((v) -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });

        findViewById(R.id.signupMainButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.showSignUp(mParent);
            }
        });

        findViewById(R.id.loginMainButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.showLogin(mParent);
            }
        });


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {

        ActivityUtils.showHomePageStartGoogle(this, account);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Uh OH", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}
