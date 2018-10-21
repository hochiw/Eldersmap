package com.example.eldermap.LocationPkg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eldermap.NavigationPkg.DisplayActivity;
import com.example.eldermap.ProfilePkg.BaseActivity;
import com.example.eldermap.ProfilePkg.User;
import com.example.eldermap.R;

import java.util.ArrayList;

public class HistoryActivity extends BaseActivity {
    private ArrayList<FinishedTrip> list = new ArrayList<>(); // returned history list
    private HistoryAdapter adapter; // adapter of the history
    private ListView historyList; // list of the history
    private RelativeLayout loading;
    private FinishedTrip destination; // destination of the history

    private static int tripID; // the target trip id to update marks
    private static int tripIndex; // the index of trip in the history list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list);

        // Set and place the title
        TextView title = findViewById(R.id.history_list_title);
        title.setGravity(Gravity.CENTER_HORIZONTAL);

        // create a typeface from the raw ttf
        Typeface typeface = Typeface.createFromAsset(getAssets(),"FormalTitle.ttf"); // create a typeface from the raw ttf
        title.setTypeface(typeface);

        // Display the list
        showList();

        // Assign the views to the variables
        historyList = findViewById(R.id.history_list);
        loading = findViewById(R.id.loadingPanel);

        // Hide the scheduled trip
        historyList.setVisibility(View.INVISIBLE);


        // Loading screen
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


    /**
     * Assign the corresponding destination and id to the variables that the user has selected
     */
    public void checkButtonClick() {
        ListView view = findViewById(R.id.history_list);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int location, long l) {
                // Assign the activity and the list of history to the corresponding variables
                tripID= list.get(location).getTripID();
                tripIndex = location;
                destination = list.get(location);
                navigationToast();
            }
        });

    }

    /**
     * Confirmation box to check whether the user wants to start the nagivation or not
     */
    public void navigationToast() {
        // Create a confirmation box
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("What do you want to do" +
                " with this history trip?").setIcon(R.mipmap.ic_launcher_app)
                // Option to start the trip
                .setPositiveButton("Start this trip again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do confirmed change nickname action
                        switchToNavigation();
                    }})
                // Option to delete the trip
                .setNeutralButton("Change Review Mark", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       //re-direct to review page
                        switchToReview();
                    }
                })
                // Option to dismiss the box
                .setNegativeButton("Cancel", null)
                .create();

        // Show the confirmation box
        dialog.show();
    }

    /**
     * Start the nagivation
     */
    public void switchToNavigation(){
        Intent i = new Intent(this, DisplayActivity.class);
        // Put all the details of the destination and send it to the nagivation activity
        i.putExtra("destLatitude",destination.getDestination().getLatitude());
        i.putExtra("destLongitude",destination.getDestination().getLongitude());
        startActivityForResult(i, 1);
    }

    /**
     * Bring the user to the review page
     */
    public void switchToReview(){
        // Assign the information to the trip review page
        TripReviewActivity.setUpdate(true);
        TripReviewActivity.setTripID(tripID);
        TripReviewActivity.setTripIndex(tripIndex);

        // Start the review activity
        Intent i = new Intent(this, TripReviewActivity.class);
        startActivityForResult(i, 1);
    }

    /**
     * Shows the list of history trips
     */
    public void showList() {

        ListView listView = findViewById(R.id.history_list);

        // Get the history list from the user profile
        list = User.getUserHistory();

        //set adapter
        adapter = new HistoryAdapter(this, list);
        listView.setAdapter(adapter);

    }


}

