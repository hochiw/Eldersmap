package com.example.kallyruan.eldermap;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.kallyruan.eldermap.NavigationPkg.DisplayActivity;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.MenuActivity;
import com.example.kallyruan.eldermap.P2PPkg.ChatActivity;
import com.example.kallyruan.eldermap.ProfilePkg.SignupActivity;
import com.example.kallyruan.eldermap.ProfilePkg.User;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

public class MainActivity extends AppCompatActivity implements LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    boolean askedPermission = false;
    LocationManager locationManager;
    String provider;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_welcome);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (User.checkUserExist()) {
            checkLocationPermission();

            //if user doesn't exist, re-direct to Sign up page
        } else {
            Intent i = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(i);
        }


    }


    /**
     * this method is to check whether user Location service permission is granted. If not, pop up an
     * acknowledge message and request for permission.
     */
    public boolean checkLocationPermission() {
        //if permission not granted, promote for permission request
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("test: ", "1 NO location permission");
            // This is to check whether we should show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d("test: ", "request permission");
                // Show an explanation to the user and then request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission request")
                        .setMessage("In order to use this App properly, you need to approve " +
                                "location Permission.")
                        .setPositiveButton("Yes, I acknowledge.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Request the permission once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                Log.d("test: ", "no explanation");
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            //if location permission is granted, continue to AppMenu page
        } else {
            Log.d("test: ", "GET location permission");
            //if is a normal user, re-direct to App Main menu page
            Log.d("test type", Integer.toString(User.checkUserType()));

            if (User.checkUserType() == User.USER) {
                Intent i = new Intent(getApplicationContext(), AppMenuActivity.class);
                startActivity(i);
                // if is an admin, User will only have chat feature
            } else if (User.checkUserType() == User.ADMIN) {
                Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(i);
            }
        }
        return true;
    }


    @Override
    public void onLocationChanged(Location location) {
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();

        Log.i("Location info: Lat", lat.toString());
        Log.i("Location info: Lng", lng.toString());
    }



    /**
     * this method is an override message to react with user permission decision. If users refuse to
     * grant the permission, an alert would pop up.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("test: ","permission granted!");
                    // if get permission, then do the task
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        //locationManager.requestLocationUpdates(provider, 400, 1, this);
                        //Log.d("test location", provider);
//                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
////
                    }
                } else {
                    // if permission denied, send user the alert message
                    Toast.makeText(MainActivity.this,"App feature may not work without " +
                                                                "permission!",Toast.LENGTH_SHORT).show();
                }
                // Whenever get permission from user, re-route user to menu page
                startMenu();
                return;
            }

        }
    }

    /**
     * this method would re-direct to App main menu page
     */
    private void startMenu() {
        final Intent mainIntent = new Intent(getApplicationContext(), AppMenuActivity.class);
        startActivity(mainIntent);
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