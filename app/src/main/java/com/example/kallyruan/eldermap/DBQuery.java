package com.example.kallyruan.eldermap;

import com.example.kallyruan.eldermap.LocationPkg.FinishedTrip;
import com.example.kallyruan.eldermap.LocationPkg.ScheduledTrip;
import com.example.kallyruan.eldermap.ProfilePkg.BaseActivity;
import com.example.kallyruan.eldermap.ProfilePkg.ChangeWalkActivity;
import com.example.kallyruan.eldermap.ProfilePkg.User;

import java.util.ArrayList;

import static com.example.kallyruan.eldermap.ProfilePkg.User.USER;

public class DBQuery {

    static String UserID;
    static int HistoryID = 0;//for demo purpose
    static int PlanID = 0; //for demo purpose

    /**
     * this method is to check whether user exists in the database
     */
    public static boolean checkUserExist() {
        //get MEID number
        UserID= User.getUserID();

        //check whether exist or not


        //default user exist
        return true;
    }

    /**
     * this method is to check user type from database
     * @return int CustomerType (USER = 0, ADMIN = 1)
     */
    public static int checkUserType() {


        //default user type is USER
        return USER;
    }

    /**
     * this method is to get User textsize preference from database
     * @return int Textsize
     */
    public static int retrieveTextSize() {


        //otherwise return default textsize
        return BaseActivity.MEDIUM;
    }

    /**
     * this method is to get User walking preference from database
     * @return int walking preference
     */
    public static int retrieveWalking() {


        //otherwise return default walking preference
        return ChangeWalkActivity.FIFTEENMINUTES;
    }

    /**
     * this method is to get User walking preference from database
     * @return int walking preference
     */
    public static boolean retrieveDataPrivilege() {


        //otherwise return default
        return true;
    }

    public static boolean updateTextsize(int textSize) {


        return true;
    }

    public static boolean updatePermission(boolean permissionPreference){

        return true;
    }

    public static boolean updateWalking(int walking){

        return true;
    }

    public static ArrayList<ScheduledTrip> retrievePlan() {
        ArrayList<ScheduledTrip> list = new ArrayList<>();


        return list;
    }

    public static ArrayList<FinishedTrip> retrieveHistory() {
        ArrayList<FinishedTrip> historyTripList = new ArrayList<>();


        return historyTripList;
    }



    /**
     * this method is to add a future trip plan to user database
     * @param Trip
     */
    public static boolean addUserPlan(ScheduledTrip Trip){


        return true;
    }

    /**
     * this method is to add a future trip plan to user database
     * @param tripID
     */
    public static boolean deleteUserPlan(int tripID){


        return true;
    }

    /**
     * this method is to add a finished trip to user history database
     * @param Trip
     */
    public static boolean addUserHistory(FinishedTrip Trip){


        return true;
    }

    public static boolean checkHistoryTripExists(int TripID){

        //default true
        return true;
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

        return true;
    }



}
