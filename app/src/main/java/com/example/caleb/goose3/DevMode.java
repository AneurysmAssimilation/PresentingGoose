package com.example.caleb.goose3;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class DevMode extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    double currentLatitude;
    double currentLongitude;
    public boolean stop = false;
    int del = 1;
    int seq;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_mode);
        setupDevGame();
        currentLatitude = 44.668577;
        currentLongitude = -70.147609;
        TextView devLat = (TextView) findViewById(R.id.devLat);
        TextView devLon = (TextView) findViewById(R.id.devLon);
        devLat.setText(Double.toString(currentLatitude));
        devLon.setText(Double.toString(currentLongitude));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ActivityCompat.requestPermissions(DevMode.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1600);
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("Current Location");
        marker = mMap.addMarker(options);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public void toastHint(View view) {
        Goose goose = new Goose();
        DBAssist db = new DBAssist(this);
        String hint = db.returnHint(goose, del, 999);
        Toast.makeText(DevMode.this, hint, Toast.LENGTH_LONG).show();
    }
    protected void reDraw() {
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        TextView devLat = (TextView) findViewById(R.id.devLat);
        TextView devLon = (TextView) findViewById(R.id.devLon);
        devLat.setText(Double.toString(currentLatitude));
        devLon.setText(Double.toString(currentLongitude));
        if(marker != null) {
            marker.remove();
        }
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("Current Location");
        marker = mMap.addMarker(options);
    }
    public void moveUp(View view) {
        currentLatitude += .0001;
        reDraw();
    }
    public void moveDown(View view) {
        currentLatitude -= .0001;
        reDraw();
    }
    public void moveLeft(View view) {
        currentLongitude -= .0001;
        reDraw();
    }
    public void moveRight(View view) {
        currentLongitude += .0001;
        reDraw();
    }
    public void checkForWin(View view) {
        Goose goose = new Goose();
        double lat = currentLatitude;
        double lon = currentLongitude;

        DBAssist db = new DBAssist(this);
        double winLat = db.returnLat(goose, del, 999);
        double winLon = db.returnLon(goose, del, 999);

        if(lat >= winLat-.0003 && lat <= winLat+.0003) {
            if (lon >= winLon - .0003 && lon <= winLon + .0003) {
                if (db.returnLength(goose, del, 999) == del) { //nothing left!
                    Toast.makeText(DevMode.this, "You Won!!", Toast.LENGTH_LONG).show();
                    //int holdOn = 0;
                    //while(holdOn <= del) {
                    //    db.delete(goose, holdOn);
                    //    holdOn += 1;
                    //}
                    del = 1;
                    Intent intent = new Intent(this, StartScreen.class);
                    startActivity(intent);
                }
                LatLng latLng = new LatLng(winLat, winLon);
                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title("Found Goose")
                        .snippet(db.returnHint(goose, del, 999));
                Marker newMarker = mMap.addMarker(options);

                del += 1;
                Toast.makeText(DevMode.this, "You found it! Check your hint for the next one!", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(DevMode.this, "Not Here!", Toast.LENGTH_SHORT).show();
        }
    }
    public void setupDevGame() {
        DBAssist db = new DBAssist(this);
        Goose goose = new Goose();
        goose.lat = 44.671289;
        goose.lon = -70.149336;
        goose.hint = "A house with eight sides.";
        goose.gooseID = 1;
        goose.ID2 = 1;
        goose.seq = 999;
        goose.length = 3;
        db.insert(goose);
        goose.lat = 44.668555;
        goose.lon = -70.147412;
        goose.hint = "Where you spend 90% of your time.";
        goose.gooseID = 2;
        goose.ID2 = 2;
        goose.seq = 999;
        goose.length = 3;
        db.insert(goose);
        goose.lat = 44.667631;
        goose.lon = -70.147544;
        goose.hint = "The only flat spot in Farmington.";
        goose.gooseID = 3;
        goose.ID2 = 3;
        goose.seq = 999;
        goose.length = 3;
        db.insert(goose);
    }

}
