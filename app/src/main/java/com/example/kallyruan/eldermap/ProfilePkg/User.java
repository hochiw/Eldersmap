package com.example.kallyruan.eldermap.ProfilePkg;

import android.accounts.NetworkErrorException;
import android.provider.Settings;
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
    private static int userType = INVALID;
    public static int USER = 0;
    public static int ADMIN = 1;
    public static String profileUrl = "http://eldersmapapi.herokuapp.com/api/profile";
    public static String profileUpdateUrl = "http://eldersmapapi.herokuapp.com/api/updateProfile";



    public static ArrayList<ScheduledTrip> getScheduledTripList() {
        return scheduledTripList;
    }


    /**
     * this method is to get User textsize preference from database
     * @return int Textsize
     */
    public static int getTextSize() {
        //ig textsize is already set
        if(textSize == INVALID){
            textSize = retrieveUserTextSize();
        }

        return textSize;
    }

    /**
     * this method is to check whether user exists in the database
     */
    public static boolean checkUserExist() {
        if (MainActivity.ANDROID_ID != null) {
            try {

                HTTPPostRequest request = new HTTPPostRequest(User.profileUrl);
                JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", MainActivity.ANDROID_ID)).get());

                if (request.getStatusCode() == 200) {
                    if (result.getJSONObject("survey").getInt("completed") == 1) {
                        return true;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        //default user doesn't exist
        return false;
    }

    /**
     * this method is to check user type from database
     * @return int CustomerType (USER = 0, ADMIN = 1)
     */
    public static int checkUserType() {

        if (userType == INVALID){
            userType = retrieveUserType();
        }
        //default user type is USER
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

    private static int retrieveUserType() {
        HTTPPostRequest request = new HTTPPostRequest(profileUrl);
        try {
            JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", MainActivity.ANDROID_ID)).get());
            return result.getInt("userType");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return USER;
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
     * this method is to add a ScheduledTrip to the scheduledTripList ArrayList and database
     * @param trip
     */
    public static void addScheduledTrip(ScheduledTrip trip){
        scheduledTripList.add(trip);
        //sort after each adding according on departing time

        addUserPlan(1, trip);

    }

    /**
     * this method is to add a future trip plan to user database
     * @param UserID
     * @param Trip
     */
    public static void addUserPlan(int UserID, ScheduledTrip Trip){

    }

    /**
     * this method is to delete a planned future trip plan from user database
     * @param UserID
     * @param TripID
     */
    public static void deleteUserPlan(int UserID,int TripID){

    }

    /**
     * this method is to add a finished trip plan to user database history
     * @param trip
     */
    public static void addUserHistory(FinishedTrip trip){
        String historyURL = "http://eldersmapapi.herokuapp.com/api/history";
        JSONObject data = new JSONObject();
        JSONObject date = new JSONObject();
        JSONObject location = new JSONObject();
         try {
            data.put("userID",MainActivity.ANDROID_ID);
            data.put("id",trip.getTripID());

            date.put("year",trip.getTargetYear());
            date.put("month",trip.getTargetMonth());
            date.put("day",trip.getTargetDay());

            location.put("name",trip.getName());
            location.put("latitude",trip.getDestination().getLatitude());
            location.put("longitude",trip.getDestination().getLongitude());

            data.put("date",date);
            data.put("location",location);
            data.put("locationRating",trip.getdestinationMark());
            data.put("tripRating",trip.getTripMark());

            new HTTPPostRequest(historyURL).execute(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method is to get all future trip plan from user database
     * @param UserID
     * @return ArrayList<ScheduledTrip>
     */
    public static ArrayList<ScheduledTrip> getUserPlan(int UserID){

        return null;
    }

    /**
     * this method is to get all trip plan from user database history
     * @param UserID
     * @return ArrayList<FinishedTrip>
     */
    public static ArrayList<FinishedTrip> getUserHistory(int UserID){

        return null;
    }



    /**
     * this method is to delete a ScheduledTrip from the scheduledTripList ArrayList and database
     * @param trip
     */
    public static void deleteScheduledTrip(ScheduledTrip trip){
        ///delete here

        //sort after each delete according on departing time

        deleteUserPlan(1, trip.getTripID());

    }

    /**
     * this method is to add a FinishedTrip to the historyTripList ArrayList and database
     * @param trip
     */
    public static void addHistoryTrip(FinishedTrip trip) {
        historyTripList.add(trip);

        addUserHistory(trip);
    }

}
