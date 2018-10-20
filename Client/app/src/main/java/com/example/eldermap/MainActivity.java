package com.example.eldermap;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eldermap.ProfilePkg.SignupActivity;
import com.example.eldermap.ProfilePkg.User;
import com.example.kallyruan.eldermap.R;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static String ANDROID_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_welcome);

        // Assign the android id to the variable
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

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if the user profile exists and if the user has completed the survey
                if (DBQuery.checkUserExist()&& DBQuery.checkSurveyCompleted()){
                    // Check if the app has the permission
                    if (checkLocationPermission()) {
                        //retrieve all user data from database
                        if(User.retrieveUserData()){
                            //if successful, redirect to App Menu page
                            Intent i = new Intent(getApplicationContext(), AppMenuActivity.class);
                            startActivity(i);
                            //if failed, show error message
                        }else{
                            Toast.makeText(getApplicationContext(),"Failed to retrieve User data. Please restart the APP."
                                    ,Toast.LENGTH_SHORT).show();
                        }
                }
                    //if does not exist, re-direct to sign up page
                } else {
                    Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                    startActivity(i);
                }

            }
        }, 1000);





    }


    /**
     * this method is to check whether user Location service permission is granted. If not, pop up an
     * acknowledge message and request for permission.
     */
    public boolean checkLocationPermission() {
        //if permission not granted, promote for permission request
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // This is to check whether we should show an explanation
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
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
                // Request for the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            //if location permission is granted, continue to AppMenu page
        } else {
            return true;
        }
        return false;
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
                    // Execute the task if the app has the permission
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                    }
                } else {
                    // Send a warning message to the user if they denied the permission request
                    Toast.makeText(MainActivity.this,"App feature may not work without " +
                                                                "permission!",Toast.LENGTH_SHORT).show();
                }
                // Redirect user to the start menu
                startMenu();
            }

        }
    }

    /**
     * Redirect the user to the main menu
     */
    private void startMenu() {
        final Intent mainIntent = new Intent(getApplicationContext(), AppMenuActivity.class);
        startActivity(mainIntent);
    }

}