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
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kallyruan.eldermap.GPSServicePkg.GPS;;
import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.LocationPkg.ScheduledTrip;
import com.example.kallyruan.eldermap.NavigationPkg.AlarmReceiver;
import com.example.kallyruan.eldermap.NavigationPkg.DisplayActivity;
import com.example.kallyruan.eldermap.NavigationPkg.NotificationScheduler;
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
    NotificationManager manager;
    Notification myNotication;
    private boolean serviceAlive = false;
    public static String category; // show the list of nearby landmarks info
    ArrayList<Landmark> list = new ArrayList<>(); // returned list of landmarks
    ArrayList<Landmark> similarlist = new ArrayList<>(); // returned list of similar landmarks based on history
    LandmarkListAdapter adapter;
    LandmarkListAdapter similarAdapter;
    ListView landmarkList;
    LinearLayout recommendation;
    RelativeLayout loading;

    private int action_index;
    private SearchAlg searchAlg = new SearchAlg();
    private boolean firstUpdate = true;
    private Location currentLocation;
    private LocationReceiver receiver;
    private static Location destination; // the target destination
    private static String destinationName;
    private Activity mActivity;
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
        landmarkList = (ListView) findViewById(R.id.landmark_list);
        recommendation = findViewById(R.id.landmark_recommendation);
        loading = (RelativeLayout) findViewById(R.id.loadingPanel);
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

    @Override

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

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



    //check whether users click on a landmark
    public void checkButtonClick() {
        ListView view = (ListView) findViewById(R.id.landmark_list);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int location, long l) {
                destination = list.get(location).getLocation();
                destinationName = list.get(location).getName();
                navigationToast();
            }
        });

    }

    //check whether users want to depart now or not
    public void navigationToast() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Do you want to depart now?")
                .setIcon(R.mipmap.ic_launcher_app)
                .setPositiveButton("Start now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do confirmed change nickname action
                        switchToNavigation();
                    }})
                .setNeutralButton("Make a schedule", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //switch to the schedule page
                        switchToSchedule();
                    }
                })
                .setNegativeButton("No, thanks", null)
                .create();
        dialog.show();
    }

    public void switchToNavigation(){
        Intent i = new Intent(this, DisplayActivity.class);
        i.putExtra("destLatitude",destination.getLatitude());
        i.putExtra("destLongitude",destination.getLongitude());
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
        JSONObject userData = JSONFactory.userDataJSONMaker(userLoc, targetLoc);

        //ArrayList<Landmark> list = searchAlg.filterList(JSONFactory.parseJSON("http://eldersmapapi.herokuapp.com/api/search"));
        JSONObject result = new JSONObject(new HTTPPostRequest("http://eldersmapapi.herokuapp.com/api/search").execute(userData).get());
        if(result.get("status").toString().equals("OK")){
            list = searchAlg.filterList(result, userLoc);
        }
        adapter = new LandmarkListAdapter(this, list);
        listView.setAdapter(adapter);

        //check whether there exists a similar destination based on user history
        similarDestination();

        //if there is empty result list, show error message
        if(list.size()==0){
            Toast.makeText(this,"Please go back and try again!",Toast.LENGTH_SHORT).show();
        }



    }

    public static Location getDestination() {
        return destination;
    }

    public static void setDestination(Location destination) {
        destination = destination;
    }


    public void similarDestination(){
        Landmark place = similarityAlg();

        //here should check where is a recommendation based on our algorithm
        if(place != null){
            Log.d("test","show recommendation");
            TextView name = findViewById(R.id.locationName);
            TextView mark= findViewById(R.id.reviewMark);
            TextView cost= findViewById(R.id.cost);
            TextView distance= findViewById(R.id.distance);
            ImageView rank= findViewById(R.id.icon_rank);

            rank.setImageResource(R.mipmap.ic_rank_best);
            name.setText(place.getName());
            mark.setText("Rating: "+Float.toString(place.getRating()));
            cost.setText("Average Cost: "+"0.0");
            distance.setText("Distance: "+Integer.toString(0));

            TextView textview = (TextView) findViewById(R.id.recommendation_title);
            // adjust this line to get the TextView you want to change

            Typeface typeface = Typeface.createFromAsset(getAssets(),"Casual.ttf"); // create a typeface from the raw ttf
            textview.setTypeface(typeface); // apply the typeface to the textview


            recommendation.setVisibility(View.VISIBLE);
        }else{
            recommendation.setVisibility(View.INVISIBLE);
        }

    }

    // this method implements our similarity comparision algorithm
    public Landmark similarityAlg(){
        //for demo purpose, assume the top location is our recommendation
        try{
            Landmark recommendation = list.get(0);
            return recommendation;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



}

