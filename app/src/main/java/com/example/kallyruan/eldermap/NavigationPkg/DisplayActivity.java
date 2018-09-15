package com.example.kallyruan.eldermap.NavigationPkg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kallyruan.eldermap.LocationPkg.TripReviewActivity;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.LandmarkListActivity;
import com.example.kallyruan.eldermap.R;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    TextView sign;
    String move;
    String direction;
    String transportationMethod;
    int period;
    String unit;
    ImageView graph;


    // sample index for tracking demo display
    private static int displayIndex = 0;

    // sample list of instruction steps for demo displaying
    public final ArrayList<SingleStepNavigation> getInformation() {
        SingleStepNavigation sample1 = new SingleStepNavigation("Turn", "Left", "walk", 2, "min");
        SingleStepNavigation sample2 = new SingleStepNavigation("Continue", "Forward", "walk", 5, "min");
        SingleStepNavigation sample3 = new SingleStepNavigation("Turn", "Right", "walk", 1, "min");
        SingleStepNavigation sample4 = new SingleStepNavigation("Continue", "Backward", "walk", 5, "min");

        ArrayList<SingleStepNavigation> sampleList = new ArrayList<>();
        sampleList.add(sample1);
        sampleList.add(sample2);
        sampleList.add(sample3);
        sampleList.add(sample4);
        return sampleList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_display);
        setInformation(displayIndex);
        refresh();
    }

    // for the present demo, assume the navigation page would refresh every two seconds
    public void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (displayIndex < getInformation().size() - 1){
                        final Intent mainIntent = new Intent(getApplicationContext(), DisplayActivity.class);
                        startActivity(mainIntent);
                        displayIndex += 1;
                    }else{
                        toTripReview();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000);
    }

    // set the display page information
    public void setInformation(int i) {
        setNavigationText(i);
        setDirectionGraph(i);

    }

    //set direction graph based on navigation instruction
    private void setDirectionGraph(int i) {
        graph = (ImageView) findViewById(R.id.directionIcon);
        final String LEFT = "LEFT";
        final String RIGHT = "RIGHT";
        final String BACKWARD = "BACKWARD";
        final String FORWARD = "FORWARD";
        String direction = getInformation().get(i).getDirection();
        if (direction.toUpperCase().equals(LEFT)) {
            graph.setImageResource(R.mipmap.ic_arrow_left);
        } else if (direction.toUpperCase().equals(RIGHT)) {
            graph.setImageResource(R.mipmap.ic_arrow_right);
        } else if (direction.toUpperCase().equals(FORWARD)) {
            graph.setImageResource(R.mipmap.ic_arrow_up);
        } else if (direction.toUpperCase().equals(BACKWARD)) {
            graph.setImageResource(R.mipmap.ic_arrow_down);
        }

    }

    //set direction text based on navigation instruction
    private void setNavigationText(int i) {
        sign = (TextView) findViewById(R.id.navigation_sign);
        ArrayList<SingleStepNavigation> sampleList = getInformation();
        //get the latest next-step navigation
        move = sampleList.get(i).getMove();
        direction = sampleList.get(i).getDirection();
        transportationMethod = sampleList.get(i).getTransportationMethod();
        ;
        period = sampleList.get(i).getPeriod();
        unit = sampleList.get(i).getUnit();

        //if time period > 1, add "s" to the end of unit
        if (period > 1) {
            unit += "s";
        }

        //combine all to the expected format
        String signText = String.format("%s %s, %s %d %s", move, direction,
                transportationMethod, period, unit);
        sign.setText(signText);
    }

    public void endTrip(View view){
        Intent intent = new Intent(getApplicationContext(), LandmarkListActivity.class);
        startActivity(intent);
    }

    private void toTripReview() {
        Intent intent = new Intent(getApplicationContext(),TripReviewActivity.class);
        startActivity(intent);
    }
}

