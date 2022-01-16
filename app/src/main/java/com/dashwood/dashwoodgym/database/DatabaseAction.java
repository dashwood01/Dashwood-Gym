package com.dashwood.dashwoodgym.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteStatement;

import com.dashwood.dashwoodgym.R;
import com.dashwood.dashwoodgym.inf.InformationPlant;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAction {
    private final SQLiteDatabase database;
    private final HandlerDatabase dbHandler;

    public DatabaseAction(Context context, SQLiteDatabase db) {
        dbHandler = new HandlerDatabase();
        if (Data.readBoolPreference(context, context.getString(R.string.preference_table), false,
                context.getString(R.string.preference_key_table)))
            database = db;
        else
            database = dbHandler.createTablePlants(context, db);
    }

    public boolean insertPlants(String name, String workTime, String restTime) {
        try {
            String sql = "INSERT INTO " + dbHandler.TABLE_PLANTS + " VALUES(?,?,?,?);";
            SQLiteStatement statement = database.compileStatement(sql);
            database.beginTransaction();
            statement.clearBindings();
            statement.bindString(2, name);
            statement.bindString(3, workTime);
            statement.bindString(4, restTime);
            statement.execute();
            database.setTransactionSuccessful();
            database.endTransaction();
            return true;
        } catch (SQLiteFullException e) {
            e.printStackTrace();
            return false;
        }

    }

    @SuppressLint("Range")
    public List<InformationPlant> getAllPlants() {
        String[] columns = {dbHandler.COLUMN_ID
                , dbHandler.COLUMN_NAME
                , dbHandler.COLUMN_WORK_TIME
                , dbHandler.COLUMN_REST_TIME
        };
        List<InformationPlant> listOfPlant = new ArrayList<>();
        Cursor cursor = database.query(dbHandler.TABLE_PLANTS, columns,
                null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                InformationPlant informationAnswer = new InformationPlant();
                informationAnswer.setId(cursor.getInt(cursor.getColumnIndex(dbHandler.COLUMN_ID)));
                informationAnswer.setName(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_NAME)));
                informationAnswer.setWorkTimeAsSec(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_WORK_TIME)));
                informationAnswer.setRestTimeAsSec(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_REST_TIME)));
                listOfPlant.add(informationAnswer);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return listOfPlant;
    }

    @SuppressLint("Range")
    public InformationPlant getLastPlant() {
        String[] columns = {dbHandler.COLUMN_ID
                , dbHandler.COLUMN_NAME
                , dbHandler.COLUMN_WORK_TIME
                , dbHandler.COLUMN_REST_TIME
        };
        String query = "SELECT * FROM " + dbHandler.TABLE_PLANTS + " ORDER BY " + dbHandler.COLUMN_ID + " DESC LIMIT 1";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToLast();
        InformationPlant informationAnswer = new InformationPlant();
        informationAnswer.setId(cursor.getInt(cursor.getColumnIndex(dbHandler.COLUMN_ID)));
        informationAnswer.setName(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_NAME)));
        informationAnswer.setWorkTimeAsSec(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_WORK_TIME)));
        informationAnswer.setRestTimeAsSec(cursor.getString(cursor.getColumnIndex(dbHandler.COLUMN_REST_TIME)));
        cursor.close();
        return informationAnswer;
    }


    public boolean deletePlant(int id) {
        return database.delete(dbHandler.TABLE_PLANTS, dbHandler.COLUMN_ID + "=" + id, null) > 0;
    }

}
