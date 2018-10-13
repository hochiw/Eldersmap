package com.example.kallyruan.eldermap.LocationPkg;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kallyruan.eldermap.GPSServicePkg.GPSTracker;
import com.example.kallyruan.eldermap.NavigationPkg.DisplayActivity;
import com.example.kallyruan.eldermap.NavigationPkg.ScheduleTimeActivity;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.JSONFactory;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.Landmark;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.LandmarkListAdapter;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.SearchAlg;
import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;
import com.example.kallyruan.eldermap.ProfilePkg.BaseActivity;
import com.example.kallyruan.eldermap.ProfilePkg.User;
import com.example.kallyruan.eldermap.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FutureActivity extends BaseActivity {
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<Integer> adapter_id;
    private int action_index;

    ArrayList<ScheduledTrip> list = new ArrayList<>(); // returned future list
    FutureAdapter adapter;
    ListView futureList;
    RelativeLayout loading;
    private static int tripID; // the target trip id to update marks
    private static String destinationName;

    public static String getDestinationName() {
        return destinationName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.future_list);

        //set title size and style
        TextView title = findViewById(R.id.future_list_title);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"FormalTitle.ttf"); // create a typeface from the raw ttf
        title.setTypeface(typeface);


        showList();

        futureList = (ListView) findViewById(R.id.future_list);
        loading = (RelativeLayout) findViewById(R.id.loadingPanel);
        futureList.setVisibility(View.INVISIBLE);

        //If disconnected with service, show with only loading panel and hence listview invisiable
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.INVISIBLE);
                futureList.setVisibility(View.VISIBLE);
            }
        }, 1500);

        checkButtonClick();
    }


    //check whether users click on a future trip
    public void checkButtonClick() {
        ListView view = (ListView) findViewById(R.id.future_list);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int location, long l) {
                tripID= User.getScheduledTripList().get(location).getTripID();
                navigationToast(tripID,location);
            }
        });

    }

    //check whether users want to depart now or not
    public void navigationToast(final int tripID, final int location) {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("What do you want to do" +
                " with this scheduled trip?").setIcon(R.mipmap.ic_launcher_app)
                .setPositiveButton("Start this trip now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // start navigation and delete this trip from future trip list
                        User.deleteScheduledTrip(getApplicationContext(),tripID, location);
                        switchToNavigation();

                    }})
                .setNeutralButton("Delete this plan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete the trip plan and update next coming reminder tripID
                        User.deleteScheduledTrip(getApplicationContext(), tripID, location);
                        //reload page after changing
                        Intent i = new Intent(getApplicationContext(), FutureActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }


    public void switchToNavigation(){
        Intent i = new Intent(this, DisplayActivity.class);
        startActivityForResult(i, 1);
    }

    /**
     * Shows the list of future trips
     */
    public void showList() {
        ListView listView = (ListView) findViewById(R.id.future_list);

        //retrieve input
        list = User.getScheduledTripList();
        //for demo purpose
        demoData();

        //set adapter
        adapter = new FutureAdapter(this, list);
        listView.setAdapter(adapter);

    }

    //demo purpose
    private void demoData() {
        ScheduledTrip trip = new
                ScheduledTrip(123, 1, 10, 2018, 10,10,
                new Location(1.0,1.0,1,0),"test place") ;
        ScheduledTrip trip1 = new
                ScheduledTrip(123, 1, 10, 2018, 13,10,
                new Location(1.0,1.0,1,2),"test place") ;
        list.add(trip);
        list.add(trip1);
    }

}

