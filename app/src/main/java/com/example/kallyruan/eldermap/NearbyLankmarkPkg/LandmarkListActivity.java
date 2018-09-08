package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.MainActivity;
import com.example.kallyruan.eldermap.NavigationPkg.DisplayActivity;
import com.example.kallyruan.eldermap.NavigationPkg.ScheduleTimeActivity;
import com.example.kallyruan.eldermap.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class LandmarkListActivity extends Activity {
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<Integer> adapter_id;
    private int action_index;
    private SearchAlg searchAlg = new SearchAlg();
    int locationIndex;
    NotificationManager manager;
    Notification myNotication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landmark_list);
        //display the list of landmarks
        try {
            showLandmarkList();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkButtonClick();
    }

    //check whether users click on a landmark
    public void checkButtonClick() {
        ListView view = (ListView) findViewById(R.id.landmark_list);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int location, long l) {
                locationIndex= location;
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

    // this method is to push a status bar notification
    @SuppressWarnings("deprecation")
    public void showNotification(){
        Intent intent = new Intent("com.rj.notitfications.SECACTIVITY");
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(getApplicationContext());

        builder.setAutoCancel(false);
        builder.setTicker("this is ticker text");
        builder.setContentTitle("WhatsApp Notification");
        builder.setContentText("You have a new message");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSubText("This is subtext...");   //API level 16
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


//    /**
//     * Shows the list of nearby landmarks
//     */
//    public void showLandmarkList() throws IOException, JSONException {
//        ListView listView = (ListView) findViewById(R.id.landmark_list);
//
//
//        //Data Input(current connection broke so use the test list instead)
//        //ArrayList<Landmark> list = searchAlg.filterList(APICommunicator.parseJSON("http://eldersmapapi.herokuapp.com/api/search?location=-33.8670522%2C151.1957362&radius=1500&pType=library")) ;
//
//        // test list since the API connection is not working now
//        ArrayList<Landmark> testList = new ArrayList<>();
//            for (int i = 0; i < 10; i++){
//                Location currentLoc = Location.getInstance(2.0,3.0);
//                Landmark curLandmark = new Landmark("testPlace", "757 swanston street", 1, currentLoc, 0.0);
//                testList.add(curLandmark);
//            }
//
//        LandmarkListAdapter adapter = new LandmarkListAdapter(this, testList);
//        listView.setAdapter(adapter);
//    }
}

