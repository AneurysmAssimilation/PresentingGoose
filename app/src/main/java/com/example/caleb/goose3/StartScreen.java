package com.example.caleb.goose3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    public void bwawk(View view) {
        Intent intent = new Intent(this, bwawk.class);
        startActivity(intent);
    }

    public void startDev(View view) {
        Intent intent = new Intent(this, DevMode.class);
        startActivity(intent);
    }
}
