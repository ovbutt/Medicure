package com.example.ovais.medicure;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import com.example.ovais.medicure.Doctors;

//MyDoctor3

public class DoctorsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "doctors.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = "Doctors";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DOCTORS_NAME = "name";
    public static final String CCOLUMN_DOCTORS_SPECIALIZATION = "specialization";
    public static final String COLUMN_DOCTORS_PHONENUM = "phonenum";
    public static final String COLUMN_DOCTORS_LANDLINE = "landline";
    public static final String COLUMN_DOCTORS_EMAIL = "email";
    public static final String COLUMN_DOCTORS_ADDRESS = "address";


    public DoctorsDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DOCTORS_NAME + " TEXT NOT NULL, " +
                CCOLUMN_DOCTORS_SPECIALIZATION + " TEXT NOT NULL, " +
                COLUMN_DOCTORS_PHONENUM + " NUMBER NOT NULL, " +
                COLUMN_DOCTORS_LANDLINE + " NUMBER NOT NULL, " +
                COLUMN_DOCTORS_EMAIL + " VARCHAR NOT NULL, " +
                COLUMN_DOCTORS_ADDRESS + " VARCHAR NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
    /**create record**/
    public void saveNewPerson(Doctors doctors) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DOCTORS_NAME, doctors.getName());
        values.put(CCOLUMN_DOCTORS_SPECIALIZATION, doctors.getSpecialization());
        values.put(COLUMN_DOCTORS_PHONENUM, doctors.getPhonenum());
        values.put(COLUMN_DOCTORS_LANDLINE, doctors.getLandline());
        values.put(COLUMN_DOCTORS_EMAIL, doctors.getEmail());
        values.put(COLUMN_DOCTORS_ADDRESS, doctors.getAddress());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    /**Query records, give options to filter results**/
    public List<Doctors> peopleList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<Doctors> doctorsLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Doctors doctors;

        if (cursor.moveToFirst()) {
            do {
                doctors = new Doctors();

                doctors.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                doctors.setName(cursor.getString(cursor.getColumnIndex(COLUMN_DOCTORS_NAME)));
                doctors.setSpecialization(cursor.getString(cursor.getColumnIndex(CCOLUMN_DOCTORS_SPECIALIZATION)));
                doctors.setPhonenum(cursor.getString(cursor.getColumnIndex(COLUMN_DOCTORS_PHONENUM)));
                doctors.setLandline(cursor.getString(cursor.getColumnIndex(COLUMN_DOCTORS_LANDLINE)));
                doctors.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_DOCTORS_EMAIL)));
                doctors.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_DOCTORS_ADDRESS)));
                doctorsLinkedList.add(doctors);
            } while (cursor.moveToNext());
        }

        return doctorsLinkedList;
    }

    /**delete record**/
    public void deletePersonRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }
}
