package com.example.caleb.goose3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;

public class DBAssist {

    private DBHandler handler;

    public DBAssist(Context context) {
        handler = new DBHandler(context);
    }

    public int insert(Goose goose) {
        SQLiteDatabase db = handler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Goose.KEY_lat, goose.lat);
        values.put(Goose.KEY_lon,goose.lon);
        values.put(Goose.KEY_ID2,goose.ID2);
        values.put(Goose.KEY_hint, goose.hint);
        values.put(Goose.KEY_seq, goose.seq);
        values.put(Goose.KEY_length, goose.length);

        long gooseID = db.insert(Goose.TABLE, null, values);
        db.close();
        return (int) gooseID;
    }

    public void delete(Goose goose, int ID) {

        SQLiteDatabase db = handler.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Goose.TABLE, Goose.KEY_ID + "= ?", new String[] { String.valueOf(ID) });
        db.close(); // Closing database connection
    }

    public void update(Goose goose) {

        SQLiteDatabase db = handler.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Goose.KEY_lat, goose.lat);
        values.put(Goose.KEY_lon, goose.lon);
        values.put(Goose.KEY_hint, goose.hint);
        values.put(Goose.KEY_ID2, goose.ID2);
        values.put(Goose.KEY_seq, goose.seq);
        values.put(Goose.KEY_length, goose.length);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Goose.TABLE, values, Goose.KEY_ID + "= ?", new String[] { String.valueOf(goose.gooseID) });
        db.close(); // Closing database connection
    }

    public Double returnLat(Goose goose, int ID, int seq) {
        SQLiteDatabase db = handler.getWritableDatabase();
        Cursor pull = db.rawQuery("SELECT "+Goose.KEY_lat+" FROM "+Goose.TABLE+ " WHERE "+Goose.KEY_ID2+"="+ID+" AND "+Goose.KEY_seq+"="+seq, null);
        pull.moveToFirst();
        Double result = pull.getDouble(0);
        pull.close();
        return result;
    }

    public Double returnLon(Goose goose, int ID, int seq) {
        SQLiteDatabase db = handler.getWritableDatabase();
        Cursor pull = db.rawQuery("SELECT "+Goose.KEY_lon+" FROM "+Goose.TABLE+ " WHERE "+Goose.KEY_ID2+"="+ID+" AND "+Goose.KEY_seq+"="+seq, null);
        pull.moveToFirst();
        Double result = pull.getDouble(0);
        pull.close();
        return result;
    }

    public String returnHint(Goose goose, int ID, int seq) {
        SQLiteDatabase db = handler.getWritableDatabase();
        Cursor pull = db.rawQuery("SELECT "+Goose.KEY_hint+" FROM "+Goose.TABLE+ " WHERE "+Goose.KEY_ID2+"="+ID+" AND "+Goose.KEY_seq+"="+seq, null);
        pull.moveToFirst();
        String result = pull.getString(0);
        pull.close();
        return result;
    }

    public String returnSequence(Goose goose, int ID) {
        SQLiteDatabase db = handler.getWritableDatabase();
        Cursor pull = db.rawQuery("SELECT "+Goose.KEY_seq+" FROM "+Goose.TABLE+ " WHERE "+Goose.KEY_ID2+"="+ID, null);
        pull.moveToFirst();
        String result = pull.getString(0);
        pull.close();
        return result;
    }

    public Integer returnLength(Goose goose, int ID, int seq) {
        SQLiteDatabase db = handler.getWritableDatabase();
        Cursor pull = db.rawQuery("SELECT "+Goose.KEY_length+" FROM "+Goose.TABLE+ " WHERE "+Goose.KEY_ID2+"="+ID+" AND "+Goose.KEY_seq+"="+seq, null);
        pull.moveToFirst();
        int result = pull.getInt(0);
        pull.close();
        return result;
    }



}