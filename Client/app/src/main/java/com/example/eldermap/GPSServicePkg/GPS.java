package com.example.eldermap.GPSServicePkg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
/**
 * GPS class is to handle all request about GPS feature. This class will continuous get latest
 * location and send updates to the observers
 */
public class GPS extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    // Initializing the google api and request variable
    private GoogleApiClient googleAPI;
    private LocationRequest mLocationRequest;

    // The interval of the location update
    private static final long INTERVAL = 1000;

    public GPS() {}

    @Override
    public void onCreate() {
        super.onCreate();

        // Initializing the request and google api
        createLocationReq();
        googleAPI = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("RestrictedApi")

    /**
     * Parameters for the location request
     */
    protected void createLocationReq() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(INTERVAL/2);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    /**
     * Start updating the location when the api is connected
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLocation();
    }


    /**
     * Initialize the location update
     */
    protected void updateLocation() {
        if (googleAPI != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(googleAPI, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    /**
     * Send the new location to all the observers on update
     * @param location
     */
    @Override
    public void onLocationChanged(android.location.Location location) {
        Intent i = new Intent();
        // Set the broadcast tag
        i.setAction("LocationUpdate");

        // Put all the location updates into the intent
        i.putExtra("Latitude",location.getLatitude());
        i.putExtra("Longitude",location.getLongitude());
        i.putExtra("Altitude",location.getAltitude());

        // Send the intent to the observers
        sendBroadcast(i);
    }
}
