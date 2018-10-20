package com.example.kallyruan.eldermap.NavigationPkg;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.kallyruan.eldermap.LocationPkg.FutureActivity;
import com.example.kallyruan.eldermap.ProfilePkg.User;
import com.example.kallyruan.eldermap.R;

import static android.content.Context.NOTIFICATION_SERVICE;



public class AlarmReceiver extends BroadcastReceiver {

    // Initialize the variables
    private NotificationManager manager;
    private Notification myNotication;
    private static int comingTripID;

    public static void setComingTripID(int comingTripID) {
        AlarmReceiver.comingTripID = comingTripID;
    }
    public static int getComingTripID(){
        return comingTripID;
    }

    /**
     * check the raw copy in notfication schedular class
     * @param context Application context
     * @param i intent
     */
    @Override
    public void onReceive(Context context, Intent i) {
        //set up the pending intent to direct user to scheduled trip page when users click on the
        //notification
        Intent notificationIntent = new Intent(context, FutureActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(FutureActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(comingTripID, PendingIntent.FLAG_ONE_SHOT);

        // Update the next trip
        User.updateComingTripID();

        setupNotificationContent(context, pendingIntent);


    }

    /**
     * Build the notification and display it on time
     * @param context application context
     * @param pendingIntent intent
     */
    private void setupNotificationContent(Context context, PendingIntent pendingIntent){
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


