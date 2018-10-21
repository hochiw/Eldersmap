package com.example.eldermap.LocationPkg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;

import com.example.eldermap.AppMenuActivity;
import com.example.eldermap.DBQuery;
import com.example.eldermap.NearbyLankmarkPkg.LandmarkListActivity;
import com.example.eldermap.ProfilePkg.User;
import com.example.eldermap.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TripReviewActivity extends AppCompatActivity {
    final float NORATE = -1;
    final static int INVALID = -999;
    final String HISTORY = "HistoryActivity";

    // Default values
    private static int tripID = INVALID;
    private static int tripIndex = INVALID;
    private static boolean update = false;
    private float destinationMark = NORATE;
    private float navigationMark = NORATE;

    public static void setUpdate(boolean update) {
        TripReviewActivity.update = update;
    }

    public static int getTripID() {
        return tripID;
    }

    public static void setTripID(int tripID) {
        TripReviewActivity.tripID = tripID;
    }

    public static void setTripIndex(int tripIndex) {
        TripReviewActivity.tripIndex = tripIndex;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_review);
        recordReviewRating();
    }

    private void recordReviewRating() {
        // get destination rating
        final RatingBar destinationRating = findViewById(R.id.destination_rating);

        // assign the destination rating to the variable
        destinationRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                destinationMark = rating;
            }
        });

        //get navigation rating
        final RatingBar navigationRating = (RatingBar) findViewById(R.id.navigation_rating);

        // assign the navigation rating to the variable
        navigationRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                navigationMark = rating;
            }
        });
    }

    /**
     * Save the default rating if user refuses to review
     * @param view
     */
    public void backToMenu(View view){
        updateReviewToDB(view);
        // Start the next activity
        checkCallingActivity();
    }


    /**
     * Save the review to the database
     * @param view
     */
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

            //save the whole trip to user history
            FinishedTrip trip = new FinishedTrip(DBQuery.createHistoryID(),day, month,year,LandmarkListActivity.getDestination(),
                    LandmarkListActivity.getDestinationName(), destinationMark,navigationMark);
            User.addUserHistory(this,trip);
        }

        // Start the next activity
        checkCallingActivity();
    }

    /**
     * Start the next activity
     */
    private void checkCallingActivity() {
        //check calling activity
        if(this.getCallingActivity().getClassName().equals(HISTORY)){
            //re-direct to history
            Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
            startActivity(i);
        }else {
            //re-direct to menu page
            Intent intent = new Intent(getApplicationContext(), AppMenuActivity.class);
            startActivity(intent);
        }
    }


}
