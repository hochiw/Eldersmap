package com.example.kallyruan.eldermap.NavigationPkg;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.kallyruan.eldermap.LocationPkg.FutureActivity;
import com.example.kallyruan.eldermap.LocationPkg.HistoryActivity;
import com.example.kallyruan.eldermap.MainActivity;
import com.example.kallyruan.eldermap.ProfilePkg.User;
import com.example.kallyruan.eldermap.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Jaison on 17/06/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    String TAG = "AlarmReceiver";
    NotificationManager manager;
    Notification myNotication;
    private static int comingTripID;

    public static void setComingTripID(int comingTripID) {
        AlarmReceiver.comingTripID = comingTripID;
    }
    public static int getComingTripID(){
        return comingTripID;
    }

    //check the raw copy in notfication schedular class
    @Override
    public void onReceive(Context context, Intent i) {
        //set up the pending intent to direct user to scheduled trip page when users click on the
        //notification
        Intent notificationIntent = new Intent(context, FutureActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(FutureActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(comingTripID, PendingIntent.FLAG_ONE_SHOT);

        Log.d(TAG, "onReceive: ");
        Log.d("test","ring the alarm "+Integer.toString(comingTripID));

        User.updateComingTripID();

        //set up the notification content
        Notification.Builder builder = new Notification.Builder(context);
        builder.setTicker("You have a scheduled trip with ElderMap")
                .setContentTitle("ElderMap Notification")
                .setContentText("Click here to start your scheduled journey!")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setOngoing(true)  //an ongoing status bar notification
                .setAutoCancel(true) //the notification would disappear after being clicked on
                .build();

        myNotication = builder.getNotification();
        manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(11, myNotication);


    }
}


