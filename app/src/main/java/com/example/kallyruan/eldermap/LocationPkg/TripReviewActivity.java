package com.example.kallyruan.eldermap.LocationPkg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import com.example.kallyruan.eldermap.MainActivity;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.Landmark;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.LandmarkListActivity;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.MenuActivity;
import com.example.kallyruan.eldermap.ProfilePkg.SettingActivity;
import com.example.kallyruan.eldermap.ProfilePkg.User;
import com.example.kallyruan.eldermap.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TripReviewActivity extends AppCompatActivity {
    final float NORATE = -1;
    final static int INVALID = -999;
    final String HISTORY = "com.example.kallyruan.eldermap.LocationPkg.HistoryActivity";
    private static int updateID = INVALID;
    private static boolean update = false;
    //default mark is -1, which indicates no rate from user
    private float destinationMark = NORATE;
    private float navigationMark = NORATE;


    public static boolean isUpdate() {
        return update;
    }

    public static void setUpdate(boolean update) {
        TripReviewActivity.update = update;
    }

    public static int getUpdateID() {
        return updateID;
    }

    public static void setUpdateID(int updateID) {
        TripReviewActivity.updateID = updateID;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_review);
        recordReviewMark();
    }

    private void recordReviewMark() {
        //get destination mark
        final RatingBar destinationRating = (RatingBar) findViewById(R.id.destination_rating);
        destinationRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                destinationMark = rating;
            }
        });

        //get navigation mark
        final RatingBar navigationRating = (RatingBar) findViewById(R.id.navigation_rating);
        destinationRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                navigationMark = rating;
            }
        });
    }

    public void backToMenu(View view){
        //if user refuse to review, just save default rating
        saveToDB(view);

        checkCallingActivity();
    }

    public void saveToDB(View view){
        //check whether this trip exists in database
        if(update && checkTripExists(updateID)){
            //update mark here
            updateHistory(updateID,destinationMark,navigationMark);

            //reset state
            update = false;
            updateID = INVALID;
        }else{
            storeToHistory();
        }

        checkCallingActivity();
    }

    private void checkCallingActivity() {
        //check calling activity
        if(this.getCallingActivity().getClassName().equals(HISTORY)){
            //re-direct to history
            Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
            startActivity(i);
        }else {
            //re-direct to menu page
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        }
    }

    //store the whole navigation history to user profile with ratings
    private void storeToHistory() {
        //get mobile phone time
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String time = timeFormat.format(new Date());
        int year = Integer.parseInt(time.substring(0,4));
        int month = Integer.parseInt(time.substring(4,6));
        int day = Integer.parseInt(time.substring(6,8));
        Log.d("time: ", time);
        //save to user trip history
        FinishedTrip trip = new FinishedTrip(createUniqueID(),day, month,year,LandmarkListActivity.getDestination(),
                LandmarkListActivity.getDestinationName(), destinationMark,navigationMark);
        User.addHistoryTrip(trip);
    }

    /**
     * update new destination mark and navigation mark to responding trip review
     */
    private void updateHistory(int id, float destinationMark, float  navigationMark) {


    }

    //check with database
    public boolean checkTripExists(int id){


        //default false
        return false;
    }

    //create a id different from database history
    public int createUniqueID(){


        return 1;
    }

}
