package com.example.kallyruan.eldermap.ProfilePkg;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.example.kallyruan.eldermap.LocationPkg.FinishedTrip;
import com.example.kallyruan.eldermap.LocationPkg.ScheduledTrip;
import com.example.kallyruan.eldermap.MainActivity;
import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class User {
    private static ArrayList<ScheduledTrip> scheduledTripList = new ArrayList<>();
    private static ArrayList<FinishedTrip> historyTripList = new ArrayList<>();
    public final static int INVALID = 0;
    private static int textSize = INVALID;
    public static String profileUrl = "http://eldersmapapi.herokuapp.com/api/profile";
    public static String profileUpdateUrl = "http://eldersmapapi.herokuapp.com/api/updateProfile";
    private static String userType = null;

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

    public static String getUserType() {
        if (userType == null) {
            userType = retrieveUserType();
        }
        return userType;
    }

    /**
     * this method is to retrieve user textsize from database
     * @return
     */
    private static int retrieveUserTextSize() {

        HTTPPostRequest request = new HTTPPostRequest(profileUrl);
        try {
            JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", MainActivity.ANDROID_ID)).get());
            return result.getJSONObject("survey").getInt("textSize");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //default size is MEDIUM
        return BaseActivity.MEDIUM;
    }

    private static String retrieveUserType() {
        HTTPPostRequest request = new HTTPPostRequest(profileUrl);
        try {
            JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", MainActivity.ANDROID_ID)).get());
            switch(result.getInt("userType")) {
                case 1:
                    return "admin";
                case 0:
                    return "client";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "client";
    }

    /**
     * this method is to update the latest textsize based on user option
     * @param textSizePreference
     */
    public static void notifytextSizeChange(int textSizePreference){
        textSize = textSizePreference;

        saveSizeToDatabase(textSizePreference);
    }


    public static void saveSizeToDatabase(int textSize) {
        saveToDatabase("textSize",textSize);
    }

    public static void saveToDatabase(String key, int value) {
        JSONObject request = new JSONObject();
        try {
            request.put("userID", MainActivity.ANDROID_ID);
            request.put("key",key);
            request.put("value",value);
            HTTPPostRequest post = new HTTPPostRequest(profileUpdateUrl);
            post.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setScheduledTripList(ArrayList<ScheduledTrip> scheduledTripList) {
        scheduledTripList = scheduledTripList;
    }

    /**
     * this method is to add a ScheduledTrip to the scheduledTripList ArrayList
     * @param trip
     */
    public static void addScheduledTrip(ScheduledTrip trip){
        scheduledTripList.add(trip);
        //sort after each adding according on departing time

    }

    /**
     * this method is to add a FinishedTrip to the historyTripList ArrayList
     * @param trip
     */
    public static void addHistoryTrip(FinishedTrip trip) {
        historyTripList.add(trip);
    }

}
