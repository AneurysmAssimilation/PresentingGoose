package com.example.caleb.goose3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class markGoose extends AppCompatActivity {
    double lat;
    double lon;
    int id;
    String hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_goose);
        Intent intent = getIntent();
        lat = intent.getDoubleExtra(bwawk.latKey, 0);
        lon = intent.getDoubleExtra(bwawk.lonKey, 0);

        String latS = Double.toString(lat);
        String lonS = Double.toString(lon);
        TextView latT = (TextView) findViewById(R.id.gooseLat);
        TextView lonT = (TextView) findViewById(R.id.gooseLon);
        latT.setText(latS);
        lonT.setText(lonS);
    }

    public void saveGoose(View view) {
        EditText Ehint = (EditText) findViewById(R.id.hintEdit);
        EditText Eid = (EditText) findViewById(R.id.numberEdit);
        hint = Ehint.getText().toString();
        id = Integer.parseInt(Eid.getText().toString());
        Intent intent = new Intent(this, bwawk.class);
        DBAssist db = new DBAssist(this);
        Goose goose = new Goose();
        goose.lat = lat;
        goose.lon = lon;
        goose.hint = hint;
        goose.gooseID = id;
        goose.ID2 = id;
        db.insert(goose);
        startActivity(intent);
    }

    public void finalGoose(View view) {
        EditText Ehint = (EditText) findViewById(R.id.hintEdit);
        EditText Eid = (EditText) findViewById(R.id.numberEdit);
        hint = Ehint.getText().toString();
        id = Integer.parseInt(Eid.getText().toString());
        Intent intent = new Intent(this, playIt.class);
        DBAssist db = new DBAssist(this);
        Goose goose = new Goose();
        Goose goose2 = new Goose();
        goose.lat = lat;
        goose.lon = lon;
        goose.hint = hint;
        goose.gooseID = id;
        goose.ID2 = id;
        db.insert(goose);
        goose.hint = "#devHack";
        goose.ID2 = id+1;
        goose.gooseID = id+1;
        db.insert(goose);
        startActivity(intent);
    }
}
