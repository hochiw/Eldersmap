package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.example.kallyruan.eldermap.LocationPkg.Location;


public class GPSTracker extends Service implements LocationListener {


    private boolean GPSEnabled = false;
    private boolean networkEnabled = false;
    private LocationManager locationManager;
    private static final int INTERVAL = 1000;
    private static final float DISTANCE = 10f;
    private Location mLocation;

    IBinder mBinder = new binder();
    LocationListener mLocationListener;

    @Override
    public void onLocationChanged(android.location.Location location) {
        mLocation.setLatitude(location.getLatitude());
        mLocation.setLongitude(location.getLongitude());

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

    public class binder extends Binder {
        public GPSTracker getInstance() {
            return GPSTracker.this;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
            mLocation = Location.getInstance(0.0,0.0);
            if (locationManager == null) {
                locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            }
            GPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (ContextCompat.checkSelfPermission( getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
                try {
                    if (GPSEnabled) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
                    } else if (networkEnabled) {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
    }

    public Location getLoc() {
        return mLocation;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
