package com.Main.csci3130groupassignment.Maps;

import com.example.csci3130groupassignment.R;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;




public class GoogleMaps extends FragmentActivity implements OnMapReadyCallback {


    /*
    Class for showing the location of Jobs in the Jobs List on Google Maps
     */
    String city;
    private GoogleMap map;
    private Location location;
    private LocationTracker locationTracker;
    private LatLng latLngJob;
    String jobName;
    private Circle circle;


    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationTracker = new LocationTracker(this);
        location = locationTracker.getLocation();
        setContentView(R.layout.activity_google_map);
        this.city = getCity();

        //For the job show
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("EXTRA_LATITUDE") && intent.hasExtra("EXTRA_LONGITUDE")) {
            double latitude = intent.getDoubleExtra("EXTRA_LATITUDE", 0);
            double longitude = intent.getDoubleExtra("EXTRA_LONGITUDE", 0);
            jobName = getIntent().getStringExtra("EXTRA_JOB_NAME");
            latLngJob = new LatLng(latitude, longitude);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    protected String getCity() {
        Intent mapIntent = getIntent();
        return mapIntent.getStringExtra("itemLocation");
    }

    protected LatLng getLatLong() {
        return new LatLng(locationTracker.getLatitude(), locationTracker.getLongitude());
    }

    protected String getLatLongAsString() {
        if (locationTracker.getLocation() != null) {
            double latitude = locationTracker.getLocation().getLatitude();
            double longitude = locationTracker.getLocation().getLongitude();
            String latLongString = latitude + ", " + longitude;
            return latLongString;
        } else {
            return "Location not available";
        }
    }

    /**
     *
     * @param googleMap
     */
    @Override
    public void onMapReady( GoogleMap googleMap) {
        this.map = googleMap;
        LatLng location = getLatLong();

        updateMarker();

        if(latLngJob != null){
            showJobLocation(latLngJob, jobName);
        }

    }

    public void updateMarker() {
        if (locationTracker.getLocation() != null) {
            LatLng currentLocation = new LatLng(locationTracker.getLatitude(), locationTracker.getLongitude());
            map.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
            addCircle();
        } else {
            Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     * @param requestCode The request code passed in {@link #requestPermissions(
     * android.app.Activity, String[], int)}
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LocationTracker.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationTracker.startLocationUpdates();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     *
     * @param latLng
     * @param jobName
     */
    public void showJobLocation(LatLng latLng, String jobName){
        if(latLng != null){
            map.addMarker(new MarkerOptions().position(latLng).title(jobName));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
        else{
            Toast.makeText(this, "Location not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void addCircle() {
        CircleOptions circleOptions = new CircleOptions()
                .center(getLatLong())
                .radius(10000)
                .strokeColor(Color.BLUE)
                .strokeWidth(2);


        circle = map.addCircle(circleOptions);
    }
}