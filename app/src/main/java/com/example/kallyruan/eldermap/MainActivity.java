package com.example.kallyruan.eldermap;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.kallyruan.eldermap.P2PPkg.ChatActivity;
import com.example.kallyruan.eldermap.ProfilePkg.SignupActivity;
import com.example.kallyruan.eldermap.ProfilePkg.User;
import com.google.android.gms.location.LocationRequest;

public class MainActivity extends AppCompatActivity implements LocationListener {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static String ANDROID_ID;
    boolean askedPermission = false;
    LocationManager locationManager;
    String provider;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_welcome);

        ANDROID_ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        //set title size and style
        TextView title = findViewById(R.id.welcome);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"CasualTitle.ttf"); // create a typeface from the raw ttf
        title.setTypeface(typeface);

        //set image
        ImageView image = findViewById(R.id.welcome_image);
        image.setImageResource(R.mipmap.ic_launcher_app);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //check whether user exist in the database
                if (DBQuery.checkUserExist()&& DBQuery.checkSurveyCompleted()){
                    if(DBQuery.checkUserType()==User.USER){
                        checkLocationPermission();
                    }else {
                        Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                        startActivity(i);
                    }
                    //if does not exist, re-direct to sign up page
                } else {
                    Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                    startActivity(i);
                }

            }
        }, 1500);



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

            //retrieve all user data from database
            if(User.retrieveUserData()){
                //if successful, redirect to App Menu page
                Intent i = new Intent(getApplicationContext(), AppMenuActivity.class);
                startActivity(i);
                //if failed, show error message
            }else{
                Toast.makeText(this,"Failed to retrieve User data. Please restart the APP."
                        ,Toast.LENGTH_SHORT).show();
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