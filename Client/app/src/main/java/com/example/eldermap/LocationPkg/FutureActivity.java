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

public class FutureActivity extends BaseActivity {

    private ArrayList<ScheduledTrip> list = new ArrayList<>(); // returned future list
    private FutureAdapter adapter; // adapter for the scheduled trips
    private ListView futureList; // list of scheduled trip
    private RelativeLayout loading;
    private Location destination; // destination of the scheduled trip

    private static int tripID; // the target trip id to update marks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.future_list);

        // Set and place the title
        TextView title = findViewById(R.id.future_list_title);
        title.setGravity(Gravity.CENTER_HORIZONTAL);

        // create a typeface from the raw ttf
        Typeface typeface = Typeface.createFromAsset(getAssets(),"FormalTitle.ttf");
        title.setTypeface(typeface);

        // Display the list
        showList();

        // Assign the views to the variables
        futureList = findViewById(R.id.future_list);
        loading = findViewById(R.id.loadingPanel);

        // Hide the scheduled trip
        futureList.setVisibility(View.INVISIBLE);

        // Loading screen
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


    /**
     * Assign the corresponding destination and id to the variables that the user has selected
     */
    public void checkButtonClick() {
        ListView view = findViewById(R.id.future_list);

        view.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int location, long l) {
                tripID = User.getScheduledTripList().get(location).getTripID();
                destination = User.getScheduledTripList().get(location).getDestination();
                navigationToast(tripID,location);
            }
        });

    }

    /**
     * Confirmation box to check whether the user wants to start the nagivation or not
     * @param tripID id of the trip
     * @param location destination id of the trip
     */
    public void navigationToast(final int tripID, final int location) {
        // Create a confirmation box
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("What do you want to do" +
                " with this scheduled trip?").setIcon(R.mipmap.ic_launcher_app)

                // Option to start the trip
                .setPositiveButton("Start this trip now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // start navigation and delete this trip from future trip list
                        User.deleteScheduledTrip(getApplicationContext(),tripID, location);
                        switchToNavigation();

                    }})

                // Option to delete the trip
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
                // Option to dismiss the menu
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
        i.putExtra("destLatitude",destination.getLatitude());
        i.putExtra("destLongitude",destination.getLongitude());
        startActivityForResult(i, 1);
    }

    /**
     * Shows the list of future trips
     */
    public void showList() {
        ListView listView = findViewById(R.id.future_list);

        //retrieve input
        list = User.getScheduledTripList();

        //set adapter
        adapter = new FutureAdapter(this, list);
        listView.setAdapter(adapter);

    }


}

