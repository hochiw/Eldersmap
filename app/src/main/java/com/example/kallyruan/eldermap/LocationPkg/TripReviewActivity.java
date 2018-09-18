package com.example.kallyruan.eldermap.LocationPkg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;

import com.example.kallyruan.eldermap.NearbyLankmarkPkg.Landmark;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.LandmarkListActivity;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.MenuActivity;
import com.example.kallyruan.eldermap.ProfilePkg.User;
import com.example.kallyruan.eldermap.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TripReviewActivity extends AppCompatActivity {
    final int NORATE = -1;
    //default mark is -1, which indicates no rate from user
    private float destinationMark = NORATE;
    private float navigationMark = NORATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_review);
        recordReviewMark();
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
        FinishedTrip trip = new FinishedTrip(day, month,year,LandmarkListActivity.getDestination(),
                LandmarkListActivity.getDestinationName(), destinationMark,navigationMark);
        User.addHistoryTrip(trip);
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
        storeToHistory();
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }
}
