package com.example.kallyruan.eldermap;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.example.kallyruan.eldermap.LocationPkg.FinishedTrip;
import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.LocationPkg.ScheduledTrip;
import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;
import com.example.kallyruan.eldermap.ProfilePkg.BaseActivity;
import com.example.kallyruan.eldermap.ProfilePkg.ChangeWalkActivity;
import com.example.kallyruan.eldermap.ProfilePkg.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.kallyruan.eldermap.ProfilePkg.User.USER;

public class DBQuery {

    static String UserID;
    static int HistoryID = 0;//for demo purpose
    static int PlanID = 0; //for demo purpose

    private static String baseURL = "http://eldersmapapi.herokuapp.com/api/";

    /**
     * this method is to check whether user exists in the database
     */
    public static boolean checkUserExist() {
        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not

        try {
            HTTPPostRequest request = new HTTPPostRequest(baseURL + "getProfile");
            String result = request.execute(new JSONObject().put("userID",UserID)).get();
            if (result == "Forbidden") {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkSurveyCompleted() {
        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getProfile");
                JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", UserID)).get());
                if (result.getJSONObject("survey").getInt("completed") == 1) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //default user type is USER
        return false;
    }

    public static boolean surveyComplete() {
        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "updateProfile");
                JSONObject data = new JSONObject();
                data.put("userID", UserID);
                data.put("key","completed");
                data.put("value",1);
                request.execute(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //default user type is USER
        return false;
    }

    /**
     * this method is to check user type from database
     * @return int CustomerType (USER = 0, ADMIN = 1)
     */
    public static int checkUserType() {

        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getProfile");
                JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", UserID)).get());
                return result.getInt("userType");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //default user type is USER
        return USER;
    }


    public static boolean createProfile() {
        //get MEID number
        UserID = User.getUserID();


            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "createProfile");
                request.execute(new JSONObject().put("userID",UserID));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        return false;
    }
    /**
     * this method is to get User textsize preference from database
     * @return int Textsize
     */
    public static int retrieveTextSize() {


        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getProfile");
                JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", UserID)).get());
                int textSize = result.getJSONObject("survey").getInt("textSize");
                Log.d("TEXT",Integer.toString(textSize));
                return textSize;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BaseActivity.MEDIUM;
    }

    /**
     * this method is to get User walking preference from database
     * @return int walking preference
     */
    public static int retrieveWalking() {


        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getProfile");
                JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", UserID)).get());
                return result.getJSONObject("survey").getInt("walking");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ChangeWalkActivity.FIFTEENMINUTES;
    }

    /**
     * this method is to get User walking preference from database
     * @return int walking preference
     */
    public static boolean retrieveDataPrivilege() {

        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getProfile");
                JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", UserID)).get());
                switch(result.getJSONObject("survey").getInt("userData")) {
                    case 0:
                        return false;
                    case 1:
                        return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //otherwise return default
        return false;
    }

    public static boolean updateTextsize(int textSize) {

        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "updateProfile");
                JSONObject data = new JSONObject();
                data.put("userID",UserID);
                data.put("key","textSize");
                data.put("value",textSize);
                request.execute(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean updatePermission(boolean permissionPreference){

        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "updateProfile");
                JSONObject data = new JSONObject();
                data.put("userID",UserID);
                data.put("key","userData");
                data.put("value",permissionPreference);
                request.execute(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean updateWalking(int walking){

        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "updateProfile");
                JSONObject data = new JSONObject();
                data.put("userID",UserID);
                data.put("key","walking");
                data.put("value",walking);
                request.execute(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static ArrayList<ScheduledTrip> retrievePlan() {
        ArrayList<ScheduledTrip> list = new ArrayList<>();

        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getAllPlan");
                JSONArray result = new JSONArray(request.execute(new JSONObject().put("userID", UserID)).get());
                for (int i = 0;i < result.length();i++) {
                    JSONObject item = result.getJSONObject(i);
                    int id = item.getInt("id");
                    int day = item.getJSONObject("datetime").getInt("day");
                    int month = item.getJSONObject("datetime").getInt("month");
                    int year = item.getJSONObject("datetime").getInt("year");
                    int hour = item.getJSONObject("datetime").getInt("hour");
                    int minute = item.getJSONObject("datetime").getInt("minute");
                    int type = item.getJSONObject("location").getInt("type");
                    Double lat = item.getJSONObject("location").getDouble("latitude");
                    Double lon = item.getJSONObject("location").getDouble("longitude");
                    String name = item.getJSONObject("location").getString("name");
                    Location loc = Location.getInstance(lat,lon);
                    loc.setType(type);
                    list.add(ScheduledTrip.getInstance(id,day,month,year,hour,minute,loc,name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public static ArrayList<FinishedTrip> retrieveHistory() {
        ArrayList<FinishedTrip> historyTripList = new ArrayList<>();

        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getAllHistory");
                JSONArray result = new JSONArray(request.execute(new JSONObject().put("userID", UserID)).get());
                for (int i = 0;i < result.length();i++) {
                    JSONObject item = result.getJSONObject(i);
                    int id = item.getInt("id");
                    int day = item.getJSONObject("date").getInt("day");
                    int month = item.getJSONObject("date").getInt("month");
                    int year = item.getJSONObject("date").getInt("year");
                    float locationRating = item.getLong("locationRating");
                    float tripRating = item.getLong("tripRating");
                    Double lat = item.getJSONObject("location").getDouble("latitude");
                    Double lon = item.getJSONObject("location").getDouble("longitude");
                    String name = item.getJSONObject("location").getString("name");
                    Location loc = Location.getInstance(lat,lon);
                    historyTripList.add(new FinishedTrip(id,day,month,year,loc,name,locationRating,tripRating));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return historyTripList;
    }



    /**
     * this method is to add a future trip plan to user database
     * @param trip
     */
    public static boolean addUserPlan(ScheduledTrip trip){

        //get MEID number
        UserID= User.getUserID();
        Log.d("UPDATE",Integer.toString(trip.getTargetHour()));
        if (checkUserExist()) {
            try {

                HTTPPostRequest request = new HTTPPostRequest(baseURL + "createPlan");
                JSONObject data = new JSONObject();


                data.put("userID",UserID);
                data.put("id",trip.getTripID());
                data.put("year",trip.getTargetYear());
                data.put("month",trip.getTargetMonth());
                data.put("day",trip.getTargetDay());
                data.put("hour",trip.getTargetHour());
                data.put("minute",trip.getTargetMinute());
                data.put("name",trip.getName());
                data.put("latitude",trip.getDestination().getLatitude());
                data.put("longitude",trip.getDestination().getLongitude());
                data.put("type",trip.getDestination().getType());
                request.execute(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * this method is to add a future trip plan to user database
     * @param tripID
     */
    public static boolean deleteUserPlan(int tripID){

        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "delPlan");
                JSONObject data = new JSONObject();
                data.put("userID",UserID);
                data.put("id",tripID);
                request.execute(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * this method is to add a finished trip to user history database
     * @param trip
     */
    public static boolean addUserHistory(FinishedTrip trip){


        //get MEID number
        UserID= User.getUserID();

        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "createHistory");
                JSONObject data = new JSONObject();
                data.put("userID",UserID);
                data.put("id",trip.getTripID());

                data.put("year",trip.getTargetYear());
                data.put("month",trip.getTargetMonth());
                data.put("day",trip.getTargetDay());


                data.put("name",trip.getName());
                data.put("latitude",trip.getDestination().getLatitude());
                data.put("longitude",trip.getDestination().getLongitude());
                data.put("type",trip.getDestination().getType());

                data.put("locationRating",trip.getdestinationMark());
                data.put("tripRating",trip.getTripMark());
                request.execute(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean checkHistoryTripExists(int TripID){
        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getHistory");
                JSONObject data = new JSONObject();
                data.put("userID",UserID);
                data.put("id",TripID);
                request.execute(data);
                return true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //otherwise return default
        return false;
    }


    //create a unique history trip id that doesn't exist in user database
    public static int createHistoryID(){
        int id;
        id = HistoryID;
        HistoryID += 1;

        return id;
    }

    //create a unique future trip id that doesn't exist in user database
    public static int createPlanID(){
        int id;
        id = PlanID;
        PlanID += 1;
        return id;
    }

    public static boolean updateHistoryReview(int tripID, float destinationMark, float navigationMark) {

        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist() && checkHistoryTripExists(tripID)) {
            try {
                HTTPPostRequest destination = new HTTPPostRequest(baseURL + "updateHistory");
                JSONObject updateData = new JSONObject();
                updateData.put("userID",UserID);
                updateData.put("id",tripID);
                updateData.put("key","locationRating");
                updateData.put("value",destinationMark);
                destination.execute(updateData);
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "updateHistory");
                updateData.remove("key");
                updateData.remove("value");
                updateData.put("key","tripRating");
                updateData.put("value",navigationMark);
                request.execute(updateData);

            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }



}
