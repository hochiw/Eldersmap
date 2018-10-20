package com.example.kallyruan.eldermap;


import com.example.kallyruan.eldermap.LocationPkg.FinishedTrip;
import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.LocationPkg.ScheduledTrip;
import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;
import com.example.kallyruan.eldermap.ProfilePkg.BaseActivity;
import com.example.kallyruan.eldermap.ProfilePkg.ChangeWalkActivity;
import com.example.kallyruan.eldermap.ProfilePkg.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.kallyruan.eldermap.ProfilePkg.User.USER;

public class DBQuery {

    // Initialize the variables
    private static String UserID;
    private static int HistoryID = 0;
    private static int PlanID = 0;

    private static String baseURL = "http://eldersmapapi.herokuapp.com/api/";

    /**
     * this method is to check whether user exists in the database
     */
    public static boolean checkUserExist() {
        // get the user id
        UserID= User.getUserID();

        // Send a post request to the api server and check whether the user profile exists
        try {
            HTTPPostRequest request = new HTTPPostRequest(baseURL + "getProfile");
            String result = request.execute(new JSONObject().put("userID",UserID)).get();
            if (result == "Forbidden") {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkSurveyCompleted() {
        // Get the user id
        UserID= User.getUserID();

        // Check if user profile exists
        if (checkUserExist()) {
            try {
                // Send a post request to the server and retrieve the user profile
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getProfile");
                JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", UserID)).get());
                // Check if the user has completed the survey or not
                if (result.getJSONObject("survey").getInt("completed") == 1) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean surveyComplete() {
        // get user id
        UserID= User.getUserID();

        //check whether user profile exists or not
        if (checkUserExist()) {
            try {
                // Send a post request to the API server
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "updateProfile");

                // Create the json data object
                JSONObject data = new JSONObject();

                // User ID
                data.put("userID", UserID);

                // Set the completed variable to 1
                data.put("key","completed");
                data.put("value",1);

                // Execute the async task
                request.execute(data);

                // Return true if success
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * this method is to check the user type from database
     * @return int CustomerType (USER = 0, ADMIN = 1)
     */
    public static int checkUserType() {

        // Get the user id
        UserID= User.getUserID();

        // Check if user profile exists
        if (checkUserExist()) {
            try {
                // Send a post request to the API server and retrieve the user profile
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getProfile");
                JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", UserID)).get());
                // Return the user type as an interger
                return result.getInt("userType");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //default user type is USER
        return USER;
    }


    public static boolean createProfile() {
        // get the user id
        UserID = User.getUserID();
            try {
                // Send a post request to the API server to create the user profile
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "createProfile");
                request.execute(new JSONObject().put("userID",UserID));
                // Return true if success
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
        // Get the user id
        UserID= User.getUserID();

        // Check if user exists or not
        if (checkUserExist()) {
            try {
                // Send a post request and retrieve the user profile
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getProfile");
                JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", UserID)).get());
                // Get the text size from the user profile
                int textSize = result.getJSONObject("survey").getInt("textSize");
                // Return the preferred text size
                return textSize;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Return the default size if error occurs
        return BaseActivity.MEDIUM;
    }

    /**
     * this method is to get User walking preference from database
     * @return int walking preference
     */
    public static int retrieveWalking() {
        // Get the user id
        UserID= User.getUserID();

        // Check if user exists or not
        if (checkUserExist()) {
            try {
                // Send a post request and retrieve the user profile
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getProfile");
                JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", UserID)).get());
                // Get the walking distance preference from the user profile
                return result.getJSONObject("survey").getInt("walking");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Return the default value if error occurs
        return ChangeWalkActivity.FIFTEENMINUTES;
    }

    /**
     * this method is to get User walking preference from database
     * @return int walking preference
     */
    public static boolean retrieveDataPrivilege() {
        // Get the user id
        UserID= User.getUserID();

        // Check if the user profile exists
        if (checkUserExist()) {
            try {
                // Send a post request and retrieve the user profile
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getProfile");
                JSONObject result = new JSONObject(request.execute(new JSONObject().put("userID", UserID)).get());
                // Get the data preference from the user profile
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
        //return default value if error occurs
        return false;
    }

    public static boolean updateTextsize(int textSize) {
        // Get the user id
        UserID= User.getUserID();

        // Check if the user profile exists
        if (checkUserExist()) {
            try {
                // Create a post requests to update the profile
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "updateProfile");

                // Create json object
                JSONObject data = new JSONObject();

                // User ID
                data.put("userID",UserID);

                // Set the text size to the preferred value
                data.put("key","textSize");
                data.put("value",textSize);

                // Execute the async task
                request.execute(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean updatePermission(boolean permissionPreference){

        // Get the user id
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                // Create a post requests to update the profile
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "updateProfile");

                // Create json object
                JSONObject data = new JSONObject();

                // User ID
                data.put("userID",UserID);

                // Set permission preference
                data.put("key","userData");
                data.put("value",permissionPreference);

                // Execute the async task
                request.execute(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean updateWalking(int walking){

        // Get the user id
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                // Create a post requests to update the profile
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "updateProfile");

                // Create json object
                JSONObject data = new JSONObject();

                // User ID
                data.put("userID",UserID);

                // Set new walking distance preference
                data.put("key","walking");
                data.put("value",walking);

                // Execute the async task
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

        // Get the user id
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                //Create a post request to retrieve scheduled trips
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getAllPlan");

                //Assign returning list of scheduled trips to a JSON array
                JSONArray result = new JSONArray(request.execute(new JSONObject().put("userID", UserID)).get());

                //Iterate through the JSON array and assign the values within
                //each JSON object to their respective variables
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

                    //Create a scheduled trip and add it to the list
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

        // Get the user id
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                //Create a post request to retrieve history
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getAllHistory");

                //Assign returning list of recorded trips to a JSON array
                JSONArray result = new JSONArray(request.execute(new JSONObject().put("userID", UserID)).get());

                //Iterate through the JSON array and assign the values within
                //each JSON object to their respective variables
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

                    //Creates a history entry and adds it to the list
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

        // Get the user id
        UserID= User.getUserID();
        if (checkUserExist()) {
            try {
                //Create a post request to create a scheduled trip
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "createPlan");

                //Create json object
                JSONObject data = new JSONObject();

                //Add relevant data into the JSON object
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

                // Execute the async task
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

        // Get the user id
        UserID= User.getUserID();

        //check whether exist or not
        if (checkUserExist()) {
            try {
                //Create post request for the deletion of a scheduled trip
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "delPlan");

                //Create JSON object
                JSONObject data = new JSONObject();

                //Add relevant data into the JSON object
                data.put("userID",UserID);
                data.put("id",tripID);

                // Execute the async task
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


        // Get the user id
        UserID= User.getUserID();

        // Check if the user profile exists
        if (checkUserExist()) {
            try {
                // Create a post request to create the history
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

                data.put("locationRating",trip.getDestinationMark());
                data.put("tripRating",trip.getTripMark());

                // Execute the async task
                request.execute(data);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean checkHistoryTripExists(int TripID){
        // Get the user id
        UserID= User.getUserID();

        //check if the user profile exists
        if (checkUserExist()) {
            try {
                // Create a post request to get the history with the corresponding id
                HTTPPostRequest request = new HTTPPostRequest(baseURL + "getHistory");
                JSONObject data = new JSONObject();
                data.put("userID",UserID);
                data.put("id",TripID);

                // execute the async task
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

        // Get the user id
        UserID= User.getUserID();

        // Check if the user profile and history exists
        if (checkUserExist() && checkHistoryTripExists(tripID)) {
            try {
                // Create two post requests to update the ratings
                HTTPPostRequest destination = new HTTPPostRequest(baseURL + "updateHistory");
                JSONObject updateData = new JSONObject();
                updateData.put("userID",UserID);
                updateData.put("id",tripID);
                updateData.put("key","locationRating");
                updateData.put("value",destinationMark);

                // Execute the async task
                destination.execute(updateData);

                HTTPPostRequest request = new HTTPPostRequest(baseURL + "updateHistory");
                updateData.remove("key");
                updateData.remove("value");
                updateData.put("key","tripRating");
                updateData.put("value",navigationMark);

                // Execute the async task
                request.execute(updateData);

                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }



}
