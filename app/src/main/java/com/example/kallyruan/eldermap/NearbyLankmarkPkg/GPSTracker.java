package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.Manifest;
import android.location.Criteria;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.kallyruan.eldermap.LocationPkg.Location;


public class GPSTracker implements LocationListener {


    private boolean GPSEnabled = false;
    private boolean networkEnabled = false;
    private Context context;
    private LocationManager locationManager;
    private android.location.Location location;

    public GPSTracker(Context context) {
        this.context = context;
    }

    public Location getLocation() {

            locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {


            if (GPSEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        return Location.getInstance(location.getLatitude(),location.getLongitude());
                    }
                }
            } else if (networkEnabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        return Location.getInstance(location.getLatitude(),location.getLongitude());
                    }
                }
            }
        }
        return null;


    }

    @Override
    public void onLocationChanged(android.location.Location location) {
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
