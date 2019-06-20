package com.example.ovais.medicure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.NoCopySpan;
import android.util.Log;

/**
 * Created by Ovais Butt on 3/22/2018.
 */

public class AllergiesHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Medicure.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "allergies";
        public static final String COL_TASK_TITLE = "title";
    }

    public AllergiesHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "Create Table " + TaskEntry.TABLE + " ( " +
                TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE);
        onCreate(db);
    }
}
