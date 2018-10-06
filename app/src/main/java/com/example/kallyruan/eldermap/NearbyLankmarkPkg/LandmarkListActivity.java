package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.kallyruan.eldermap.GPSServicePkg.GPS;;
import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.NavigationPkg.DisplayActivity;
import com.example.kallyruan.eldermap.NavigationPkg.ScheduleTimeActivity;
import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;

import com.example.kallyruan.eldermap.ProfilePkg.BaseActivity;
import com.example.kallyruan.eldermap.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LandmarkListActivity extends BaseActivity {
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<Integer> adapter_id;
    private int action_index;
    private SearchAlg searchAlg = new SearchAlg();
    NotificationManager manager;
    Notification myNotication;
    private boolean firstUpdate = true;
    private GPS gps;
    public static String category; // show the list of nearby landmarks info
    ArrayList<Landmark> list = new ArrayList<>(); // returned list of landmarks
    ArrayList<Landmark> similarlist = new ArrayList<>(); // returned list of similar landmarks based on history
    LandmarkListAdapter adapter;
    LandmarkListAdapter similarAdapter;
    ListView landmarkList;
    RelativeLayout loading;
    private Location currentLocation;
    private static Location destination; // the target destination
    private static String destinationName;


    public static String getDestinationName() {
        return destinationName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landmark_list);
        landmarkList = (ListView) findViewById(R.id.landmark_list);
        loading = (RelativeLayout) findViewById(R.id.loadingPanel);
        landmarkList.setVisibility(View.INVISIBLE);

        // Initiate currentLocation variable
        currentLocation = Location.getInstance(0.0,0.0,0.0f);

        // Create GPS Intent
        Intent i = new Intent(getApplicationContext(),GPS.class);

        // Create intent filter for broadcast receiver
        IntentFilter intentFilter = new IntentFilter("LocationUpdate");

        // Start GPS service and register broadcast receiver
        startService(i);
        registerReceiver(new LocationReceiver(),intentFilter);

        //If disconnected with service, show with only loading panel and hence listview invisiable
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.INVISIBLE);
                landmarkList.setVisibility(View.VISIBLE);
            }
        }, 1500);
        checkButtonClick();
    }

    private class LocationReceiver extends BroadcastReceiver {

        @Override
        // Where a new location is received
        public void onReceive(Context context, Intent intent) {

            // Replace the location attributes with the new ones
            currentLocation.setLatitude(intent.getDoubleExtra("Latitude",0.0));
            currentLocation.setLongitude(intent.getDoubleExtra("Longitude",0.0));
            currentLocation.setBearing(intent.getFloatExtra("Bearing",0.0f));

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



    //check whether users click on a landmark
    public void checkButtonClick() {
        ListView view = (ListView) findViewById(R.id.landmark_list);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int location, long l) {
                destination= list.get(location).getLocation();
                navigationToast();
            }
        });

    }

    //check whether users want to depart now or not
    public void navigationToast() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Confirm action " +
                "dialog").setIcon(R.mipmap.ic_hospital)
                .setPositiveButton("Start now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do confirmed change nickname action
                        switchToNavigation();
                    }})
                .setNeutralButton("Make a schedule", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showNotification();
                        //switch to the schedule page
                        switchToSchedule();
                    }
                })
                .setNegativeButton("No, thanks", null)
                .setMessage("Are you want to depart now?").create();
        dialog.show();
    }

    // this method is to push a status bar notification (should be inside scheduleTimeActivity)
    @SuppressWarnings("deprecation")
    public void showNotification(){
        Intent intent = new Intent("com.rj.notitfications.SECACTIVITY");
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        builder.setAutoCancel(false);
        builder.setTicker("You have a scheduled trip with ElderMap");
        builder.setContentTitle("ElderMap Notification");
        builder.setContentText("Click here to start your scheduled journey!");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        //builder.setSubText("This is subtext...");   //API level 16
        builder.setNumber(100);
        builder.build();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);
    }

    public void switchToNavigation(){
        Intent i = new Intent(this, DisplayActivity.class);
        startActivityForResult(i, 1);
    }

    public void switchToSchedule(){
        Intent i = new Intent(this, ScheduleTimeActivity.class);
        startActivityForResult(i, 1);
    }

    /**
     * Shows the list of nearby landmarks
     */
    public void showLandmarkList(String targetLoc) throws  JSONException, ExecutionException, InterruptedException {
        ListView listView = (ListView) findViewById(R.id.landmark_list);

        //Data Input
        Location userLoc = currentLocation;
        Log.d("WTF",Double.toString(userLoc.getLatitude()));
        JSONObject userData = JSONFactory.userDataJSONMaker(userLoc, targetLoc);

        //ArrayList<Landmark> list = searchAlg.filterList(JSONFactory.parseJSON("http://eldersmapapi.herokuapp.com/api/search"));
        JSONObject result = new JSONObject(new HTTPPostRequest("http://eldersmapapi.herokuapp.com/api/search").execute(userData).get());
        if(result.get("status").toString().equals("OK")){
            list = searchAlg.filterList(result);
        }
        Log.d("test",list.toString());
        adapter = new LandmarkListAdapter(this, list);
        listView.setAdapter(adapter);

        //check whether there exists a similar destination based on user history
        similarDestination();

    }

    public static Location getDestination() {
        return destination;
    }

    public static void setDestination(Location destination) {
        destination = destination;
    }


    public void similarDestination(){
        ListView similarView = (ListView) findViewById(R.id.similar_destination);

        similarlist = similarityAlg();

        //here should check where is a recommendation based on our algorithm
        if(similarlist != null){
            adapter = new LandmarkListAdapter(this, similarlist);
            similarView.setAdapter(similarAdapter);
            similarView.setVisibility(View.VISIBLE);
        }else{
            similarView.setVisibility(View.INVISIBLE);
        }

    }

    // this method implements our similarity comparision algorithm
    public ArrayList<Landmark> similarityAlg(){

        //default not available
        return null;
    }



}

