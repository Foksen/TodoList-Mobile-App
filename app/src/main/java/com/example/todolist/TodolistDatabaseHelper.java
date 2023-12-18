package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodolistDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "todolist";
    private static final int DB_VERSION = 1;

    TodolistDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    public static void addTask(SQLiteDatabase db, String title, String desc) {
        ContentValues taskValues = new ContentValues();
        taskValues.put("TITLE", title);
        taskValues.put("DESCRIPTION", desc);
        db.insert("TASKS", null, taskValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE TASKS ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "TITLE TEXT,"
            + "DESCRIPTION TEXT)");
        }
    }
}
