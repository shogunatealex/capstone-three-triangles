package com.bamashire.capstoneapp;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Production on 4/19/2018.
 */

public abstract class LongRunService extends IntentService {
    protected LongRunService() {
        super("LongRunService");
        //setIntentRedelivery(false);
    }

    protected String getActionName() {
        return null;
    }

    private boolean canHandleIntent(Intent intent) {
        if( !getActionName().isEmpty() ) {
            return true;
        } else if( intent==null || intent.getAction()==null ) {
            return false;
        } else {
            return getActionName().equalsIgnoreCase(intent.getAction());
        }
    }

    @Override
    protected final void onHandleIntent(Intent intent) {

        if( canHandleIntent(intent) ) {
            try {
                onPreStart(intent, -1);
            } catch( Throwable e ) {
                onFailedToInitialize(e);
                return;
            }
            try {
                onStartImpl(intent, -1);
            } catch( Throwable e ) {
                onFailedToRun(e);
            } finally {
                try {
                    onPostStart(intent, -1);
                } catch( Throwable e ) {
                    onFailedToFinalize(e);
                }
            }
        }
    }

    protected void onFailedToFinalize(Throwable e) {
    }

    protected void onFailedToRun(Throwable e) {
    }

    protected void onFailedToInitialize(Throwable e) {
    }

    protected void onPreStart(Intent intent, int startId) {
    }

    protected abstract void onStartImpl(Intent intent, int startId) throws Exception;

    protected void onPostStart(Intent intent, int startId) {
    }
}
