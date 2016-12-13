package com.example.caleb.goose3;


public class Goose {
    public static final String TABLE = "Geese";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_ID2 = "ID2";
    public static final String KEY_lat = "latitude";
    public static final String KEY_lon = "longitude";
    public static final String KEY_hint = "hint";
    public static final String KEY_seq = "seq";
    public static final String KEY_length = "length";

    public int gooseID;
    public Double lat;
    public Double lon;
    public int ID2;
    public String hint;
    public int seq;
    public int length;
}