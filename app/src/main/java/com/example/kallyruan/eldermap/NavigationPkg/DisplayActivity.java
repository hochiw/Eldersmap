package com.example.kallyruan.eldermap.NavigationPkg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kallyruan.eldermap.GPSServicePkg.GPS;
import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.LandmarkListActivity;
import com.example.kallyruan.eldermap.P2PPkg.ChatActivity;
import com.example.kallyruan.eldermap.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class DisplayActivity extends AppCompatActivity {
    TextView sign;
    ImageView graph;

    //variable for display instruction
    NavigationChecker checker;
    ArrayList<Position> poList = new ArrayList<>();

    // connection used for navigation checker, binds gps
    private boolean firstUpdate = true;
    private Location currentLocation;
    private static Location destination; // the target destination
    private LocationReceiver receiver;


//    // sample index for tracking demo display
////    private static int displayIndex = 0;
////
////    // sample list of instruction steps for demo displaying
////    public final ArrayList<SingleStepNavigation> getInformation() {
////        SingleStepNavigation sample1 = new SingleStepNavigation("Turn", "Left", "walk", 2, "min");
////        SingleStepNavigation sample2 = new SingleStepNavigation("Continue", "Forward", "walk", 5, "min");
////        SingleStepNavigation sample3 = new SingleStepNavigation("Turn", "Right", "walk", 1, "min");
////        SingleStepNavigation sample4 = new SingleStepNavigation("Continue", "Backward", "walk", 5, "min");
////
////        ArrayList<SingleStepNavigation> sampleList = new ArrayList<>();
////        sampleList.add(sample1);
////        sampleList.add(sample2);
////        sampleList.add(sample3);
////        sampleList.add(sample4);
////        return sampleList;
////    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_display);
        //setInformation(displayIndex);//for demo purpose

        //A button listener for Help button, once clicked, re-direct to chat page
        Button helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(i);
            }
        });

        Button endTrip = findViewById(R.id.end_navigation);
        endTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Create GPS Intent
        Intent i = new Intent(getApplicationContext(),GPS.class);

        // Create intent filter for broadcast receiver
        IntentFilter intentFilter = new IntentFilter("LocationUpdate");

        // Initiate broadcast receiver
        receiver = new LocationReceiver();
        // Start GPS service and register broadcast receiver
        startService(i);
        registerReceiver(receiver,intentFilter);

        currentLocation = Location.getInstance(0.0,0.0,0.0f);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    private class LocationReceiver extends BroadcastReceiver {

        @Override
        // Where a new location is received
        public void onReceive(Context context, Intent intent) {

            // Replace the location attributes with the new ones
            currentLocation.setLatitude(intent.getDoubleExtra("Latitude",0.0));
            currentLocation.setLongitude(intent.getDoubleExtra("Longitude",0.0));
            currentLocation.setBearing(intent.getFloatExtra("Bearing",0.0f));

            //LUL

            if (checker != null) {
                refreshList(checker.getPositions());
                checker.setUserLoc(currentLocation);
                checker.getUserLoc();
                setInformation(poList.get(0));
            }

            // Wait until the GPS is warmed up before displaying the list
            if (firstUpdate) {
                try {
                    //if connected to server, make loading panel view gone and show result list
                    checker = new NavigationChecker(currentLocation,Location.getInstance(
                            getIntent().getDoubleExtra("destLatitude",0.0),
                            getIntent().getDoubleExtra("destLongitude", 0.0),
                            0.0f));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                firstUpdate = false;
            }
        }
    };

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

        graph = (ImageView) findViewById(R.id.directionIcon);
        //set keywords for moving direction
        final String LEFT = "LEFT";
        final String RIGHT = "RIGHT";
        final String BACKWARD = "BACKWARD";
        final String FORWARD = "STRAIGHT";
        //set direction image if contains keywords
        if (signText.toUpperCase().contains(LEFT)) {
            graph.setImageResource(R.mipmap.ic_arrow_left);
        } else if (direction.toUpperCase().equals(RIGHT)) {
            graph.setImageResource(R.mipmap.ic_arrow_right);
        } else if (direction.toUpperCase().equals(FORWARD)) {
            graph.setImageResource(R.mipmap.ic_arrow_up);
        } else if (direction.toUpperCase().equals(BACKWARD)) {
            graph.setImageResource(R.mipmap.ic_arrow_down);
        }

    }

    /** this method is used in timertask to refresh arraylist
     * @param List
     */
    private void refreshList(ArrayList<Position> List) {
        this.poList = List;
    }
}

