package com.example.kallyruan.eldermap.ProfilePkg;

import com.example.kallyruan.eldermap.LocationPkg.FinishedTrip;
import com.example.kallyruan.eldermap.LocationPkg.ScheduledTrip;

import java.util.ArrayList;

public class User {
    private static ArrayList<ScheduledTrip> scheduledTripList = new ArrayList<>();
    private static ArrayList<FinishedTrip> historyTripList = new ArrayList<>();


    public static ArrayList<ScheduledTrip> getScheduledTripList() {
        return scheduledTripList;
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
