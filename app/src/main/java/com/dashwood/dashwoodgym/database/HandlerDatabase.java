package com.dashwood.dashwoodgym.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.dashwood.dashwoodgym.R;

public class HandlerDatabase {
    public static final String DB_NAME = "PlantDatabase";
    public final String TABLE_PLANTS = "plant_tb";
    public final String COLUMN_ID = "id";
    public final String COLUMN_NAME = "name";
    public final String COLUMN_WORK_TIME = "work_time";
    public final String COLUMN_REST_TIME = "rest_time";

    public SQLiteDatabase createTablePlants(Context context, SQLiteDatabase db) {
        try {
            String CREATE_TABLE_PLANT = "CREATE TABLE " + TABLE_PLANTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_WORK_TIME + " TEXT," +
                    COLUMN_REST_TIME + " TEXT" + ");";
            db.execSQL(CREATE_TABLE_PLANT);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        Data.saveBoolPreference(context, context.getString(R.string.preference_table), true, context.getString(R.string.preference_key_table));
        return db;
    }

}