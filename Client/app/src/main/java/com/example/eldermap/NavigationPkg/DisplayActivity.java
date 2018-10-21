package com.example.eldermap.NavigationPkg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eldermap.LocationPkg.TripReviewActivity;
import com.example.eldermap.GPSServicePkg.GPS;
import com.example.eldermap.LocationPkg.Location;
import com.example.eldermap.P2PPkg.ChatActivity;
import com.example.eldermap.R;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    private TextView sign;
    private TextView distance;
    private ImageView graph;
    //variable for display instruction
    private NavigationChecker checker;
    private ArrayList<Position> poList = new ArrayList<>();
    // connection used for navigation checker, binds gps
    private Location currentLocation;
    private LocationReceiver receiver;
    private SensorManager mSensorManager;
    private SensorListener sensorListener;
    private IntentFilter intentFilter;
    private float currentAngle;
    private float arrowAngle = 0f;
    private boolean firstUpdate = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_display);

        // distance display box
        distance = findViewById(R.id.distance);

        //A button listener for help button which redirects the user to chat page when clicked
        Button helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(i);
            }
        });

        // A button listener for the end trip button which redirects the user to the reivew page
        // when clicked
        Button endTrip = findViewById(R.id.end_navigation);
        endTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),TripReviewActivity.class);
                startActivityForResult(i,1);
            }
        });

        // Create GPS Intent
        Intent i = new Intent(getApplicationContext(),GPS.class);

        // Create intent filter for broadcast receiver
        intentFilter = new IntentFilter("LocationUpdate");

        // Initiate broadcast receiver
        receiver = new LocationReceiver();
        // Start GPS service and register broadcast receiver
        startService(i);
        registerReceiver(receiver,intentFilter);

        currentLocation = Location.getInstance(0.0,0.0);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorListener = new SensorListener();
        mSensorManager.registerListener(sensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                48,
                SensorManager.SENSOR_DELAY_GAME);


        // Initialize the direction arrow
        graph = findViewById(R.id.directionIcon);
        graph.setImageResource(R.mipmap.ic_arrow_up);



    }

    /**
     * Disconnects the receiver the sensor when the activity is stopped
     */
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
        mSensorManager.unregisterListener(sensorListener);

    }

    /**
     * Connects the receiver the sensor when the activity is resumed
     */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,intentFilter);
        mSensorManager.registerListener(sensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                48,
                SensorManager.SENSOR_DELAY_GAME);
    }


    // Custom broadcast receiver
    private class LocationReceiver extends BroadcastReceiver {

        /**
         * Calls when the reciever receives an intent from the gps service
         * @param context Application context
         * @param intent Received Intent
         */
        @Override
        public void onReceive(Context context, Intent intent) {

            // Replace the location attributes with the new ones
            currentLocation.setLatitude(intent.getDoubleExtra("Latitude",0.0));
            currentLocation.setLongitude(intent.getDoubleExtra("Longitude",0.0));
            currentLocation.setAltitude(intent.getDoubleExtra("Altitude",0.0));

            // Wait until the GPS is warmed up before displaying the list
            if (firstUpdate) {
                try {
                    //if connected to server, make loading panel view gone and show result list
                    checker = new NavigationChecker(currentLocation,Location.getInstance(
                            getIntent().getDoubleExtra("destLatitude",0.0),
                            getIntent().getDoubleExtra("destLongitude", 0.0)));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                firstUpdate = false;
            }

            // Check the current user location and update the arrow
            if (checker != null) {
                refreshList(checker.getPositions());
                checker.setUserLoc(currentLocation);
                checker.checkpointDetection();
                if (poList.size() != 0) {
                    setInformation(poList.get(0));
                } else {
                    switchToReview();
                }

                distance.setText(String.format("%.2fm",checker.getDistance()));
            }
        }
    }

    // Custom listener for the geomagnetic field sensor
    private class SensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (checker != null) {

                // Algorithm to calculate the direction the arrow should be pointing to
                double bearing = checker.calculateAngle();

                GeomagneticField geoField = checker.calcGeofield();

                currentAngle = Math.round(sensorEvent.values[0]);

                currentAngle -= geoField.getDeclination();

                if (bearing < 0) {
                    bearing += 360;
                }

                float direction = (float) bearing - currentAngle;

                if (direction < 0) {
                    direction += 360;
                }

                // Animation for the arrow
                RotateAnimation ra = new RotateAnimation(
                        arrowAngle,
                        direction,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f);

                ra.setDuration(210);

                ra.setFillAfter(true);

                // Start the animation
                graph.startAnimation(ra);

                // Smoothening the animation
                arrowAngle = direction;
            }



        }




        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

//    // for the present demo, assume the navigation page would refresh every two seconds
//    public void refresh() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final Intent mainIntent = new Intent(getApplicationContext(), DisplayActivity.class);
//                    startActivity(mainIntent);
//                    displayIndex += 1;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 2000);
//    }


    /**
     * this method is to set instruction information on display page based on information content
     * @param current Position has all information for next instruction
     */
    public void setInformation(Position current) {
        //set instruction text
        sign = (TextView) findViewById(R.id.navigation_sign);
        String signText = current.getInstruction();
        sign.setText(signText);

        String direction = current.getModifier();



        //set keywords for moving direction
       // final String LEFT = "LEFT";
        //final String RIGHT = "RIGHT";
        //final String BACKWARD = "BACKWARD";
      //  final String FORWARD = "STRAIGHT";
        //set direction image if contains keywords
     //   if (signText.toUpperCase().contains(LEFT)) {
    //        graph.setImageResource(R.mipmap.ic_arrow_left);
    //    } else if (direction.toUpperCase().equals(RIGHT)) {
    //        graph.setImageResource(R.mipmap.ic_arrow_right);
    //    } else if (direction.toUpperCase().equals(FORWARD)) {
    //        graph.setImageResource(R.mipmap.ic_arrow_up);
    //    } else if (direction.toUpperCase().equals(BACKWARD)) {
    //        graph.setImageResource(R.mipmap.ic_arrow_down);
    //    }

    }

    /** this method is used in timertask to refresh arraylist
     * @param List
     */
    private void refreshList(ArrayList<Position> List) {
        this.poList = List;
    }

    public void switchToReview(){
        Intent i = new Intent(this, TripReviewActivity.class);
        startActivityForResult(i, 1);
    }
}

