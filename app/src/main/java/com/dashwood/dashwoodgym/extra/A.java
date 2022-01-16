package com.dashwood.dashwoodgym.extra;

import android.app.Application;

import com.dashwood.dashwoodgym.database.SyncDatabase;

public class A extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SyncDatabase.getDatabase(getApplicationContext());
    }

}
