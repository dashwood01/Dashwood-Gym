package com.dashwood.dashwoodgym.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dashwood.dashwoodgym.extra.Values;

public class SyncDatabase {

    private static DatabaseAction databaseAction;

    public static synchronized DatabaseAction getDatabase(Context context) {
        if (databaseAction == null) {
            databaseAction = new DatabaseAction(context, createDB(context));
        }
        return databaseAction;
    }

    private static SQLiteDatabase createDB(Context context) {
        return SQLiteDatabase.openOrCreateDatabase(context.getExternalFilesDir(Values.getDirDatabase()) + "/" + HandlerDatabase.DB_NAME, null);
    }
}