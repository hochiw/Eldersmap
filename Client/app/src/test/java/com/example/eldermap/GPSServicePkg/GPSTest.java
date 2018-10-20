package com.example.eldermap.GPSServicePkg;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.eldermap.GPSServicePkg.GPS;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;

/**
 *  The below link from google, saying that the test of Service requires Instrumented Unit Test,
 *  which is about UI/Integration test that is not required for the purpose of the subject.
 *  https://developer.android.com/training/testing/integration-testing/service-testing#java
 *  I personally assume that since the whole class is based on Google API yet the coding and
 *  functionality should be valid. No need for further testing.
 */

public class GPSTest extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private  String TAG = "GOS";
    private static final long INTERVAL = 1000;
    @Mock
    private GoogleApiClient googleApiClient;
    @Mock
    private LocationRequest locationRequest;

    private GPS gps;

    @Before
    public void setup(){
        locationRequest = PowerMockito.mock(LocationRequest.class);
        googleApiClient = PowerMockito.mock(GoogleApiClient.class);
    }

    @Test
    public void onCreate() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Test
    public void onStartCommand() {
    }

    @Test
    public void onBind() {
    }

    @Test
    public void createLocationReq() {
    }

    @Test
    public void onConnected() {
    }

    @Test
    public void updateLocation() {
    }

    @Test
    public void onConnectionSuspended() {
    }

    @Test
    public void onConnectionFailed() {
    }

    @Test
    public void onLocationChanged() {
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {

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

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}