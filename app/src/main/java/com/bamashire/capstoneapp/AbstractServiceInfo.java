package com.bamashire.capstoneapp;

import android.content.Context;

import java.util.Date;

/**
 * Created by Production on 4/19/2018.
 */

public abstract class AbstractServiceInfo {
        public abstract Class getServiceClass();
        public abstract String getCallServiceAction();

        public abstract long getExecutionFrequencyMS(Context context);
        public abstract boolean shouldBeActive(Context context);

        protected abstract Date getLastExecutionDate(Context context);
        protected abstract long getFirstEverExecutionTimeMS();

        public long getOnFailureRescheduleTimeMS() {
            return 60 * 1000;
        }

        public final Date getNextExecutionDate(Context context) {
            final Date lastExecutionDate = getLastExecutionDate(context);
            final long now = System.currentTimeMillis();
            long nextExecutionMS;

            if( (lastExecutionDate==null) || (lastExecutionDate.getTime()>now) ) {
                nextExecutionMS = now + getFirstEverExecutionTimeMS();
            } else {
                nextExecutionMS = lastExecutionDate.getTime() + getExecutionFrequencyMS(context);
                if( now>nextExecutionMS ) {
                    nextExecutionMS = now + getFirstEverExecutionTimeMS();
                }
            }
            return new Date(nextExecutionMS);
        }
    }

