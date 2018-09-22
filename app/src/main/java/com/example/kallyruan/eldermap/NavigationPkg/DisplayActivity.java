package com.example.kallyruan.eldermap.NavigationPkg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kallyruan.eldermap.GPSServicePkg.GPSTracker;
import com.example.kallyruan.eldermap.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DisplayActivity extends AppCompatActivity {
    TextView sign;
    String move;
    String direction;
    String transportationMethod;
    int period;
    String unit;
    ImageView graph;

    /*For navigation checker part*/
    private boolean serviceAlive = false;
    private GPSTracker gps;


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
        // Connect to the GPS Service
        Intent i = new Intent(this,GPSTracker.class);
        startService(i);
        bindService(i,mServiceConn, Context.BIND_AUTO_CREATE);
        //refresh();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (serviceAlive) {
            unbindService(mServiceConn);
            serviceAlive = false;
        }
    }

    private ServiceConnection mServiceConn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceAlive = false;

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            GPSTracker.binder mBinder = (GPSTracker.binder) service;
            gps = mBinder.getInstance();
            serviceAlive = true;
            try {
                NavigationChecker checker = new NavigationChecker(gps);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    };

    // for the present demo, assume the navigation page would refresh every two seconds
    public void refresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    final Intent mainIntent = new Intent(getApplicationContext(), DisplayActivity.class);
                    startActivity(mainIntent);
                    displayIndex += 1;
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
        final String FORWARD = "STRAIGHT";
        String direction = getInformation().get(i).getDirection();
        if (direction.toUpperCase().equals(LEFT)) {
            graph.setImageResource(R.mipmap.ic_arrow_left);
        } else if (direction.toUpperCase().equals(RIGHT)) {
            graph.setImageResource(R.mipmap.ic_arrow_right);
        } else if (direction.toUpperCase().equals(FORWARD)) {
            graph.setImageResource(R.mipmap.ic_arrow_up);
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
}

