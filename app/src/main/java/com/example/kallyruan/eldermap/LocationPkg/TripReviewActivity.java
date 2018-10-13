package com.example.kallyruan.eldermap.LocationPkg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import com.example.kallyruan.eldermap.DBQuery;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.LandmarkListActivity;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.MenuActivity;
import com.example.kallyruan.eldermap.ProfilePkg.User;
import com.example.kallyruan.eldermap.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TripReviewActivity extends AppCompatActivity {
    final float NORATE = -1;
    final static int INVALID = -999;
    final String HISTORY = "com.example.kallyruan.eldermap.LocationPkg.HistoryActivity";
    private static int tripID = INVALID;
    private static int tripIndex = INVALID;
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

    public static int getTripID() {
        return tripID;
    }

    public static void setTripID(int tripID) {
        TripReviewActivity.tripID = tripID;
    }

    public static int getTripIndex() {
        return tripIndex;
    }

    public static void setTripIndex(int tripIndex) {
        TripReviewActivity.tripIndex = tripIndex;
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
        updateReviewToDB(view);

        checkCallingActivity();
    }

    public void updateReviewToDB(View view){
        //check whether this trip exists in database
        if(update && DBQuery.checkHistoryTripExists(tripID)){
            //update mark here
            User.updateHistoryReview(getApplicationContext(),tripID,tripIndex,destinationMark,navigationMark);

            //reset state
            update = false;
            tripID = INVALID;
            tripIndex = INVALID;
        }else{
            //get mobile phone time
            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String time = timeFormat.format(new Date());
            int year = Integer.parseInt(time.substring(0,4));
            int month = Integer.parseInt(time.substring(4,6));
            int day = Integer.parseInt(time.substring(6,8));
            Log.d("time: ", time);
            //save the whole trip to user history
            FinishedTrip trip = new FinishedTrip(DBQuery.createHistoryID(),day, month,year,LandmarkListActivity.getDestination(),
                    LandmarkListActivity.getDestinationName(), destinationMark,navigationMark);
            User.addUserHistory(this,trip);
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


}
