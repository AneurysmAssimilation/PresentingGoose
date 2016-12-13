package com.example.caleb.goose3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "geese.db";

    public DBHandler(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE = "CREATE TABLE " + Goose.TABLE  + "("
                + Goose.KEY_ID  + " INTEGER PRIMARY KEY ,"
                + Goose.KEY_lat + " DOUBLE, "
                + Goose.KEY_lon + " DOUBLE, "
                + Goose.KEY_ID2 + " INTEGER, "
                + Goose.KEY_hint + " STRING, "
                + Goose.KEY_seq + " INTEGER, "
                + Goose.KEY_length + " INTEGER )";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Goose.TABLE);
        onCreate(db);

    }

}