package com.example.ovais.medicure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;




public class DietAlertDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "diet.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = "diet";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_APPOINTMENT_TITLE = "title";
    public static final String COLUMN_APPOINTMENT_TIME = "time";
    public static final String COLUMN_APPOINTMENT_DATE = "date";
    public static final String COLUMN_APPOINTMENT_NOTES = "notes";


    public DietAlertDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_APPOINTMENT_TITLE + " TEXT NOT NULL, " +
                COLUMN_APPOINTMENT_TIME + " VARCHAR NOT NULL, " +
                COLUMN_APPOINTMENT_DATE + " VARCHAR NOT NULL, " +
                COLUMN_APPOINTMENT_NOTES + " VARCHAR NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
    /**create record**/
    public void saveNewPerson(DietAlert dietAlert) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_APPOINTMENT_TITLE, dietAlert.getTitle());
        values.put(COLUMN_APPOINTMENT_TIME, dietAlert.getTime());
        values.put(COLUMN_APPOINTMENT_DATE, dietAlert.getDate());
        values.put(COLUMN_APPOINTMENT_NOTES, dietAlert.getNotes());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    /**Query records, give options to filter results**/
    public List<DietAlert> peopleList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<DietAlert> dietAlertLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DietAlert dietAlert;

        if (cursor.moveToFirst()) {
            do {
                dietAlert = new DietAlert();

                dietAlert.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                dietAlert.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_TITLE)));
                dietAlert.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_TIME)));
                dietAlert.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_DATE)));
                dietAlert.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_NOTES)));
                dietAlertLinkedList.add(dietAlert);
            } while (cursor.moveToNext());
        }


        return dietAlertLinkedList;
    }


    /**delete record**/
    public void deletePersonRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

}
