package com.example.kallyruan.eldermap.ProfilePkg;

import com.example.kallyruan.eldermap.LocationPkg.FinishedTrip;
import com.example.kallyruan.eldermap.LocationPkg.ScheduledTrip;

import java.util.ArrayList;

public class User {
    private static ArrayList<ScheduledTrip> scheduledTripList = new ArrayList<>();
    private static ArrayList<FinishedTrip> historyTripList = new ArrayList<>();
    public final static int INVALID = 0;
    private static int textSize = INVALID;

    public static ArrayList<ScheduledTrip> getScheduledTripList() {
        return scheduledTripList;
    }


    public static int getTextSize() {
        //get user preferred textsize from database
        if(textSize == INVALID){
            textSize = retrieveUserTextSize();
        }

        return textSize;
    }

    //retrieve user textsize from database
    private static int retrieveUserTextSize() {


        //default size is MEDIUM
        return BaseActivity.MEDIUM;
    }

    // update the latest textsize based on user option
    public static void notifytextSizeChange(int textSizePreference){
        textSize = textSizePreference;

        saveSizeToDatabase(textSizePreference);
    }

    // this method is to upload user preference to database
    private static void saveSizeToDatabase(int textSize) {

    }

    public static void setScheduledTripList(ArrayList<ScheduledTrip> scheduledTripList) {
        scheduledTripList = scheduledTripList;
    }

    public static void addScheduledTrip(ScheduledTrip trip){
        scheduledTripList.add(trip);
        //sort after each adding
    }

    public static void addHistoryTrip(FinishedTrip trip) {
        historyTripList.add(trip);
    }
}
