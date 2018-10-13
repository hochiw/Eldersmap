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
import android.widget.ImageView;
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

public class HistoryActivity extends BaseActivity {
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<Integer> adapter_id;
    private int action_index;

    ArrayList<FinishedTrip> list = new ArrayList<>(); // returned history list
    HistoryAdapter adapter;
    ListView historyList;
    RelativeLayout loading;
    private static int tripID; // the target trip id to update marks
    private static int tripIndex; // the index of trip in the history list
    private static String destinationName;

    public static String getDestinationName() {
        return destinationName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list);

        //set title size and style
        TextView title = findViewById(R.id.history_list_title);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"FormalTitle.ttf"); // create a typeface from the raw ttf
        title.setTypeface(typeface);



        showList();

        historyList = (ListView) findViewById(R.id.history_list);
        loading = (RelativeLayout) findViewById(R.id.loadingPanel);
        historyList.setVisibility(View.INVISIBLE);


        //If disconnected with service, show with only loading panel and hence listview invisiable
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.INVISIBLE);
                historyList.setVisibility(View.VISIBLE);
            }
        }, 1500);

        checkButtonClick();
    }


    //check whether users click on a landmark
    public void checkButtonClick() {
        ListView view = (ListView) findViewById(R.id.history_list);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int location, long l) {
                tripID= list.get(location).getTripID();
                tripIndex = location;
                navigationToast();
            }
        });

    }

    //check whether users want to depart now or not
    public void navigationToast() {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("What do you want to do" +
                " with this history trip?").setIcon(R.mipmap.ic_launcher_app)
                .setPositiveButton("Start this trip again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do confirmed change nickname action
                        switchToNavigation();
                    }})
                .setNeutralButton("Change Review Mark", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       //re-direct to review page
                        switchToReview(which);
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

    public void switchToReview(int index){
        TripReviewActivity.setUpdate(true);
        Log.d("test trip index ",Integer.toString(index));
        TripReviewActivity.setTripID(tripID);
        TripReviewActivity.setTripIndex(tripIndex);
        Intent i = new Intent(this, TripReviewActivity.class);
        startActivityForResult(i, 1);
    }

    /**
     * Shows the list of history trips
     */
    public void showList() {
        ListView listView = (ListView) findViewById(R.id.history_list);
        list = User.getUserHistory();

        //Data Input
        demoData();
        Log.d("test history list: ",Integer.toString(list.size()));


        //set adapter
        adapter = new HistoryAdapter(this, list);
        listView.setAdapter(adapter);

    }

    //demo purpose
    private void demoData() {
        FinishedTrip trip1 = new
                FinishedTrip(123, 1, 10, 2018,
                Location.getInstance(15.0,22.5,53,0), "campus", 3,
                4) ;
        FinishedTrip trip2 = new
                FinishedTrip(123, 1, 6, 2018,
                Location.getInstance(15.0,22.5,53,1), "campus", 3,
                4) ;
        list.add(trip1);
        list.add(trip2);

    }

}

