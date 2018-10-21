package com.example.eldermap.NearbyLankmarkPkg;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eldermap.GPSServicePkg.GPS;
import com.example.eldermap.LocationPkg.Location;
import com.example.eldermap.NavigationPkg.DisplayActivity;
import com.example.eldermap.NavigationPkg.ScheduleTimeActivity;
import com.example.eldermap.NetworkPkg.HTTPPostRequest;
import com.example.eldermap.ProfilePkg.BaseActivity;
import com.example.eldermap.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * LandmarkListActivity class is to request nearby lankmark, show sorted suggested landmark list,
 * handles UI display, intersection and redirecting to other activities.
 */
public class LandmarkListActivity extends BaseActivity {
    public static String category; // show the list of nearby landmarks info

    private ArrayList<Landmark> list = new ArrayList<>(); // returned list of landmarks
    private LandmarkListAdapter adapter; // Adapter for the landmark list
    private ListView landmarkList;
    private LinearLayout recommendation;
    private RelativeLayout loading;
    private Landmark place = null;
    private SearchAlg searchAlg = new SearchAlg();
    private Location currentLocation; // Current user location
    private LocationReceiver receiver; // Location receiver
    private boolean firstUpdate = true; // Check if it's the first update

    private static Location destination; // the target destination
    private static String destinationName; // the name of the destination
    public static String getDestinationName() {
        return destinationName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landmark_list);
        //set title size and style
        TextView title = findViewById(R.id.landmark_list_title);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"FormalTitle.ttf"); // create a typeface from the raw ttf
        title.setTypeface(typeface);

        //get all views
        landmarkList = findViewById(R.id.landmark_list);
        recommendation = findViewById(R.id.landmark_recommendation);
        loading = findViewById(R.id.loadingPanel);
        landmarkList.setVisibility(View.INVISIBLE);

        // Initiate currentLocation variable
        currentLocation = Location.getInstance(0.0,0.0);

        // Create GPS Intent
        Intent i = new Intent(getApplicationContext(),GPS.class);

        // Create intent filter for broadcast receiver
        IntentFilter intentFilter = new IntentFilter("LocationUpdate");

        // Initiate broadcast receiver
        receiver = new LocationReceiver();
        // Start GPS service and register broadcast receiver
        recommendation.setVisibility(View.INVISIBLE);
        // Connect to the GPS Service
        startService(i);
        registerReceiver(receiver,intentFilter);

        //If disconnected with service, show with only loading panel and hence listview invisiable
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.INVISIBLE);
                landmarkList.setVisibility(View.VISIBLE);
                recommendation.setVisibility(View.VISIBLE);

            }
        }, 2500);
        checkButtonClick();
    }

    /**
     * Unregister the location receiver when the activity is destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    // Custom broadcast receiver for the gps service
    private class LocationReceiver extends BroadcastReceiver {

        @Override
        // Where a new location is received
        public void onReceive(Context context, Intent intent) {
            // Replace the location attributes with the new ones
            currentLocation.setLatitude(intent.getDoubleExtra("Latitude",0.0));
            currentLocation.setLongitude(intent.getDoubleExtra("Longitude",0.0));

            // Wait until the GPS is warmed up before displaying the list
            if (firstUpdate) {
                try {
                    //if connected to server, make loading pannel view gone and show result list
                    showLandmarkList(category);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                firstUpdate = false;
            }
        }
    }


    /**
     * check whether users click on a landmark
     */
    public void checkButtonClick() {
        ListView view = findViewById(R.id.landmark_list);
        LinearLayout recommendation = findViewById(R.id.landmark_recommendation);

        // Set the destination to the corresponding location that the user has clicked
        view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int location, long l) {
                destination = list.get(location).getLocation();
                destinationName = list.get(location).getName();
                navigationToast();
            }
        });

        // Set the destination to the recommended location that the user has clicked
        recommendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destination = place.getLocation();
                destinationName = place.getName();
                navigationToast();
            }
        });

    }

    /**
     * Confirmation box to check whether the user wants to start the nagivation or not
     */
    public void navigationToast() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Do you want to depart now?")
                .setIcon(R.mipmap.ic_launcher_app)
                // Option to start the navigation
                .setPositiveButton("Start now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do confirmed change nickname action
                        switchToNavigation();
                    }})

                // Option to make a schedule
                .setNeutralButton("Make a schedule", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //switch to the schedule page
                        switchToSchedule();
                    }
                })

                //Option to dismiss the box
                .setNegativeButton("No, thanks", null)
                .create();

        // Show the confirmation box
        dialog.show();
    }

    /**
     * Start the nagivation
     */
    public void switchToNavigation(){
        Intent i = new Intent(this, DisplayActivity.class);

        // Put the information into the intent and start the activity
        i.putExtra("destLatitude",destination.getLatitude());
        i.putExtra("destLongitude",destination.getLongitude());
        startActivityForResult(i, 1);
    }

    /**
     * Move to the schedule creator
     */
    public void switchToSchedule(){
        Intent i = new Intent(this, ScheduleTimeActivity.class);
        startActivityForResult(i, 1);
    }

    /**
     * Shows the list of nearby landmarks
     */
    public void showLandmarkList(String targetLoc) throws  JSONException, ExecutionException, InterruptedException {
        ListView listView = findViewById(R.id.landmark_list);

        //Data Input
        Location userLoc = currentLocation;
        JSONObject userData = JSONFactory.userDataJSONMaker(userLoc, targetLoc);

        // Request for the location list from the api server
        JSONObject result = new JSONObject(new HTTPPostRequest("http://eldersmapapi.herokuapp.com/api/search").execute(userData).get());
        if(result.get("status").toString().equals("OK")){
            // Filter the list
            list = searchAlg.filterList(result, userLoc);
        }

        // Set the adapter for the list
        adapter = new LandmarkListAdapter(this, list);
        listView.setAdapter(adapter);

        //check whether there exists a similar destination based on user history
        recommendedDestination();

        //if there is empty result list, show error message
        if(list.size()==0){
            Toast.makeText(this,"Please go back and try again!",Toast.LENGTH_SHORT).show();
        }



    }

    public static Location getDestination() {
        return destination;
    }


    /**
     * Set the recommended destination
     */
    public void recommendedDestination(){

        // Get the place using the algorithm
        place = recommendedAlg();

        if(place != null){

            // Assign the view elements to the variables
            TextView name = findViewById(R.id.locationName);
            TextView mark = findViewById(R.id.reviewMark);
            TextView distance = findViewById(R.id.distance);
            TextView textview = findViewById(R.id.recommendation_title);
            ImageView rank = findViewById(R.id.icon_rank);

            // Set the information to the elements
            rank.setImageResource(R.mipmap.ic_rank_best);
            name.setText(place.getName());
            mark.setText("Rating: " + Float.toString(place.getRating()));
            distance.setText("Estimate time(min): " + Integer.toString(place.getEstTime()));

            // Font for the recommendation
            Typeface typeface = Typeface.createFromAsset(getAssets(),"Casual.ttf"); // create a typeface from the raw ttf
            textview.setTypeface(typeface); // apply the typeface to the text view

            // Show the recommendation after all the calculation
            recommendation.setVisibility(View.VISIBLE);

            // Remove the first element from the list
            list.remove(0);
            adapter.notifyDataSetChanged();

        }else{
            // If place is empty hide the recommendation field
            recommendation.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * Get the recommended location
     * @return best location
     */
    public Landmark recommendedAlg(){
        // Remove the first location from the list and set it as the recommended location
        try{
            return list.remove(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}

