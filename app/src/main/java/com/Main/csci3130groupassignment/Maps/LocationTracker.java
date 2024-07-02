package com.Main.csci3130groupassignment.Maps;


import static android.content.Context.LOCATION_SERVICE;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.Circle;


public class LocationTracker extends Activity implements LocationListener {
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    private static final int REQUEST_CODE_PERMISSION = 2;
    private boolean mLocationPermissionGranted;
    private Circle circle;


    protected LocationManager locationManager;
    Context mContext;
    Location location = null;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    /**
     *
     * @param mContext
     */
    public LocationTracker(Context mContext) {
        this.mContext = mContext;
        location = getLocation();
    }

    /**
     *
     * @param location the updated location
     */
    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.location = location;

        Log.d(TAG, "Location changed: " + location.getLatitude() + ", " + location.getLongitude());
    }

    public double getLatitude() {
        if (location != null) {
            return location.getLatitude();
        }
        return 0.0;
    }

    public double getLongitude() {
        if (location != null) {
            return location.getLongitude();
        }
        return 0.0;
    }

    public boolean canGetLocation() {
        return location != null;
    }

    public Location getLocation() {
        getLocationPermission();
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPSEnabled && isNetworkEnabled) {
            if (locationManager.isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    getLocationPermission();
                }
                else {
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                }

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                }
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return location;
            }
        }

        else {
            location = null;
        }
        return location;
    }




    public void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: starts");
        String[] permissions = {FINE_LOCATION, COURSE_LOCATION};
        if (ContextCompat.checkSelfPermission(mContext, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        } else if (ContextCompat.checkSelfPermission(mContext, COURSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mLocationPermissionGranted = true;
            startLocationUpdates();
        }
        Log.d(TAG, "getLocationPermission: ends");
    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            Log.d(TAG, "Location updates started");
        }
    }

    @Nullable
    //@Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
