package com.example.finalui.RideTrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "trip_db";
    private static final String TABLE_NAME = "myTable";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DISTANCE = "distance";
    private static final String COLUMN_DURATION = "duration";
    String br;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

       // db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
       // db.execSQL("CREATE TABLE IF NOT EXISTS student(eid VARCHAR,ename VARCHAR,esdate VARCHAR);");
        br = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("+COLUMN_NAME+ " Text, "+COLUMN_DISTANCE+ " Real, "+COLUMN_DURATION+ " Text);";
        db.execSQL(br);
        Log.i("kk", "kk");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME+" ;");

        // Create tables again
        onCreate(db);
    }

    public void insertTripData(String name, float distance, String duration) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DISTANCE, distance);
        values.put(COLUMN_DURATION, duration);

        // insert row
        db.insert(TABLE_NAME, null, values);
    }

    public List<TripData> getTripData() {//getting the data
        // get readable database as we are not inserting anything
        List<TripData> data = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);

        TripData trip = null;
       // Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" ;",null);

        String query = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);

        while (cursor.moveToNext())
        {
            trip = new TripData();//maybe error is there
            String name= cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String duration= cursor.getString(cursor.getColumnIndexOrThrow("duration"));
            float distance= cursor.getFloat(cursor.getColumnIndexOrThrow("distance"));
            trip.setName(name);
            trip.setDistance(distance);
            trip.setDuration(duration);
            data.add(trip);
        }

        return data;
    }
}
