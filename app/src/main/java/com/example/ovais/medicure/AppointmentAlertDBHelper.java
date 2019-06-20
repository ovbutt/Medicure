package com.example.ovais.medicure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import com.example.ovais.medicure.AppointmentAlert;


public class AppointmentAlertDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "appointment.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = "appoitnment";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_APPOINTMENT_TITLE = "title";
    public static final String COLUMN_APPOINMENT_DOCTOR_NAME = "name";
    public static final String COLUMN_APPOINMENT_DOCTOR_NUMBER = "phonenum";
    public static final String COLUMN_APPOINTMENT_ADDRESS = "address";
    public static final String COLUMN_APPOINTMENT_TIME = "time";
    public static final String COLUMN_APPOINTMENT_DATE = "date";
    public static final String COLUMN_APPOINTMENT_NOTES = "notes";


    public AppointmentAlertDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_APPOINTMENT_TITLE + " TEXT NOT NULL, " +
                COLUMN_APPOINMENT_DOCTOR_NAME + " TEXT NOT NULL, " +
                COLUMN_APPOINMENT_DOCTOR_NUMBER + " NUMBER NOT NULL, " +
                COLUMN_APPOINTMENT_ADDRESS + " VARCHAR NOT NULL, " +
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
    public void saveNewPerson(AppointmentAlert appointmentAlert) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_APPOINTMENT_TITLE, appointmentAlert.getTitle());
        values.put(COLUMN_APPOINMENT_DOCTOR_NAME, appointmentAlert.getDocname());
        values.put(COLUMN_APPOINMENT_DOCTOR_NUMBER, appointmentAlert.getDocphonenum());
        values.put(COLUMN_APPOINTMENT_ADDRESS, appointmentAlert.getDocaddress());
        values.put(COLUMN_APPOINTMENT_TIME, appointmentAlert.getTime());
        values.put(COLUMN_APPOINTMENT_DATE, appointmentAlert.getDate());
        values.put(COLUMN_APPOINTMENT_NOTES, appointmentAlert.getNotes());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    /**Query records, give options to filter results**/
    public List<AppointmentAlert> peopleList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<AppointmentAlert> appointmentAlertLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        AppointmentAlert appointmentAlert;

        if (cursor.moveToFirst()) {
            do {
                appointmentAlert = new AppointmentAlert();

                appointmentAlert.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                appointmentAlert.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_TITLE)));
                appointmentAlert.setDocname(cursor.getString(cursor.getColumnIndex(COLUMN_APPOINMENT_DOCTOR_NAME)));
                appointmentAlert.setDocphonenum(cursor.getString(cursor.getColumnIndex(COLUMN_APPOINMENT_DOCTOR_NUMBER)));
                appointmentAlert.setDocaddress(cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_ADDRESS)));
                appointmentAlert.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_TIME)));
                appointmentAlert.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_DATE)));
                appointmentAlert.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_NOTES)));
                appointmentAlertLinkedList.add(appointmentAlert);
            } while (cursor.moveToNext());
        }


        return appointmentAlertLinkedList;
    }

    /**delete record**/
    public void deletePersonRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

}
