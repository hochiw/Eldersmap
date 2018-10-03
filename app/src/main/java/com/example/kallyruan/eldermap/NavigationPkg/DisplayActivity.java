package com.example.kallyruan.eldermap.NavigationPkg;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kallyruan.eldermap.GPSServicePkg.GPSTracker;
import com.example.kallyruan.eldermap.P2PPkg.ChatActivity;
import com.example.kallyruan.eldermap.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class DisplayActivity extends AppCompatActivity {
    TextView sign;
    String direction;
    ImageView graph;

    //variable for display instruction
    NavigationChecker checker;
    ArrayList<Position> poList = new ArrayList<>();

    // connection used for navigation checker, binds gps
    private boolean serviceAlive = false;
    private GPSTracker gps;


//    // sample index for tracking demo display
////    private static int displayIndex = 0;
////
////    // sample list of instruction steps for demo displaying
////    public final ArrayList<SingleStepNavigation> getInformation() {
////        SingleStepNavigation sample1 = new SingleStepNavigation("Turn", "Left", "walk", 2, "min");
////        SingleStepNavigation sample2 = new SingleStepNavigation("Continue", "Forward", "walk", 5, "min");
////        SingleStepNavigation sample3 = new SingleStepNavigation("Turn", "Right", "walk", 1, "min");
////        SingleStepNavigation sample4 = new SingleStepNavigation("Continue", "Backward", "walk", 5, "min");
////
////        ArrayList<SingleStepNavigation> sampleList = new ArrayList<>();
////        sampleList.add(sample1);
////        sampleList.add(sample2);
////        sampleList.add(sample3);
////        sampleList.add(sample4);
////        return sampleList;
////    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_display);
        //setInformation(displayIndex);//for demo purpose

        //A button listener for Help button, once clicked, re-direct to chat page
        Button helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(i);
            }
        });

        // Connect to the GPS Service
        Intent i = new Intent(this,GPSTracker.class);
        startService(i);
        bindService(i,mServiceConn, Context.BIND_AUTO_CREATE);

        //timer task to get the latest position ArrayList and show current instruction
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                refreshList(checker.getPositions());
                //display the latest instruction to user
                setInformation(poList.get(0));
            }
        };
        timer.schedule(task, 10000);

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
                checker = new NavigationChecker(gps);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    };

//    // for the present demo, assume the navigation page would refresh every two seconds
//    public void refresh() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final Intent mainIntent = new Intent(getApplicationContext(), DisplayActivity.class);
//                    startActivity(mainIntent);
//                    displayIndex += 1;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 2000);
//    }


    /**
     * this method is to set instruction information on display page based on information content
     * @param current Position has all information for next instruction
     */
    public void setInformation(Position current) {
        //set instruction text
        sign = (TextView) findViewById(R.id.navigation_sign);
        String signText = current.getInstruction();
        sign.setText(signText);


        graph = (ImageView) findViewById(R.id.directionIcon);
        //set keywords for moving direction
        final String LEFT = "LEFT";
        final String RIGHT = "RIGHT";
        final String BACKWARD = "BACKWARD";
        final String FORWARD = "STRAIGHT";
        //set direction image if contains keywords
        if (signText.toUpperCase().contains(LEFT)) {
            graph.setImageResource(R.mipmap.ic_arrow_left);
        } else if (direction.toUpperCase().equals(RIGHT)) {
            graph.setImageResource(R.mipmap.ic_arrow_right);
        } else if (direction.toUpperCase().equals(FORWARD)) {
            graph.setImageResource(R.mipmap.ic_arrow_up);
        } else if (direction.toUpperCase().equals(BACKWARD)) {
            graph.setImageResource(R.mipmap.ic_arrow_down);
        }

    }

    /** this method is used in timertask to refresh arraylist
     * @param List
     */
    private void refreshList(ArrayList<Position> List) {
        this.poList = List;
    }
}

