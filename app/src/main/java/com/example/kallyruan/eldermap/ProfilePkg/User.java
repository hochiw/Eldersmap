package com.example.kallyruan.eldermap.ProfilePkg;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.kallyruan.eldermap.DBQuery;
import com.example.kallyruan.eldermap.LocationPkg.FinishedTrip;
import com.example.kallyruan.eldermap.LocationPkg.ScheduledTrip;
import com.example.kallyruan.eldermap.MainActivity;
import com.example.kallyruan.eldermap.NavigationPkg.AlarmReceiver;

import java.util.ArrayList;
import java.util.Collections;

public class User {
    public static int USER = 0;
    public static int ADMIN = 1;
    public final static int INVALID = -1;

    private static ArrayList<ScheduledTrip> scheduledTripList = new ArrayList<>();
    private static ArrayList<FinishedTrip> historyTripList = new ArrayList<>();

    //user setting
    private static int textSize;
    private static int walking;
    private static boolean dataPermission;
    private static String userID = null;//null is invalid UserID

    public static ArrayList<ScheduledTrip> getScheduledTripList() {
        return scheduledTripList;
    }

    public static String getUserID(){
        if (userID==null){
            // here get MEID id here by somehow

            userID = MainActivity.ANDROID_ID; //here 1 just for demo

            return userID;
        }else{
            return userID;
        }
    }

    public static boolean retrieveUserData(){
        textSize = DBQuery.retrieveTextSize();
        walking = DBQuery.retrieveWalking();
        dataPermission = DBQuery.retrieveDataPrivilege();
        scheduledTripList = DBQuery.retrievePlan();
        historyTripList = DBQuery.retrieveHistory();
        return true;
    }



    /**
     * this method is to update the latest textsize based on user option
     * @param textSizePreference
     */
    public static void notifytextSizeChange(Context context, int textSizePreference){
        boolean result = DBQuery.updateTextsize(textSizePreference);
        //if succeeded to update in user database, update the local variable
        if(result){
            textSize = textSizePreference;
        //if failed, show an error message
        }else{
            Toast.makeText(context,"Failed to update textsize preference to Database, please try again."
                    ,Toast.LENGTH_SHORT).show();
        }
    }

    /** this method is to update user preference to database
     * @param permissionPreference
     */
    public static void notifyPermissionChange(Context context,boolean permissionPreference) {
        boolean result = DBQuery.updatePermission(permissionPreference);
        //if succeeded to update  in user database, update the local variable
        if(result){
            dataPermission = permissionPreference;
            //if failed, show an error message
        }else{
            Toast.makeText(context,"Failed to update textsize preference to Database, please try again."
                    ,Toast.LENGTH_SHORT).show();
        }
    }

    /** this method is to update user walking preference to database
     * @param walking
     */
    public static void notifyWalkingChange(Context context,int walking) {
        boolean result = DBQuery.updateWalking(walking);
        //if succeeded to update in user database, update the local variable
        if(result){
            walking = walking;
            //if failed, show an error message
        }else{
            Toast.makeText(context,"Failed to update walking preference to Database, please try again."
                    ,Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * this method is to add a ScheduledTrip to the scheduledTripList ArrayList and database
     * @param trip
     */
    public static void addScheduledTrip(Context context, ScheduledTrip trip){
        boolean result = DBQuery.addUserPlan(trip);
        if(result){
            scheduledTripList.add(trip);
            //sort after each adding according on departing time
            Collections.sort(scheduledTripList);
            //update the next coming notification tripID
            checkAddComingTripID(trip.getTripID());
        //if failed, show an error message
        }else{
            Toast.makeText(context,"Failed to add Trip plan to Database, please try again."
                    ,Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * this method is to delete a planned future trip plan from user database
     * @param tripID
     */
    public static void deleteScheduledTrip(Context context,int tripID,int index){
        boolean nextID = false;

        if(tripID == AlarmReceiver.getComingTripID()){
            nextID = true;
        }
        boolean result = DBQuery.deleteUserPlan(tripID);
        //if going to delete the coming trip, update the alarm receiver request code to next trip
        if(result && nextID) {
            try{
                AlarmReceiver.setComingTripID(scheduledTripList.get(index + 1).getTripID());
                scheduledTripList.remove(index);
            } catch (Exception e) {
                e.printStackTrace();
            }


        //if the removed one is in the past or not the next coming trip, it will not affect, so just
        //remove it
        }else if (result){
            scheduledTripList.remove(index);

        //if failed, show an error message
        }else{
            Toast.makeText(context,"Failed to remove Trip plan from Database, please try again."
                    ,Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * this method is to add a finished trip plan to user database history
     * @param trip
     */
    public static void addUserHistory(Context context, FinishedTrip trip){
        boolean result = DBQuery.addUserHistory(trip);
        if(result){
            historyTripList.add(trip);

            //if failed, show an error message
        }else{
            Toast.makeText(context,"Failed to add the finished trip to Database, please try again."
                    ,Toast.LENGTH_SHORT).show();
        }
    }


    public static ArrayList<ScheduledTrip> getUserPlan(){

        return scheduledTripList;
    }

    public static ArrayList<FinishedTrip> getUserHistory(){

        return historyTripList;
    }


    public static void updateHistoryReview(Context context, int tripID, int tripIndex,float destinationMark, float  navigationMark) {
        //if successfully delete from DB
        if(DBQuery.updateHistoryReview(tripID,destinationMark, navigationMark)){

            historyTripList.remove(tripIndex);
        }else{
            Toast.makeText(context,"Failed to update history review to Database, please try again."
                    ,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * this method is called when there is a deletion or addition to the previous scheduledTripList.
     * Need to check whether the change brought a more closing trip for notification or deleted the
     * coming trip for notification
     */

    //assume for addition, after sort
    public static void checkAddComingTripID(int tripID){
        //if not scheduled plan before, then this would be the first reminder
        if(scheduledTripList.size()==1){
            AlarmReceiver.setComingTripID(tripID);
        }else{
            //get next coming trip index
            int lastID = AlarmReceiver.getComingTripID();
            Log.d("test before add: ",Integer.toString(lastID));
            int currentIndex = -1;
            int addedIndex= -1;
            //get the index of these two in the list
            for(int i = 0; i < scheduledTripList.size();i++){
                if (scheduledTripList.get(i).getTripID() == lastID){
                    currentIndex = i;
                }
                if(scheduledTripList.get(i).getTripID() == tripID){
                    addedIndex = i;
                }
                if(currentIndex !=-1 && addedIndex != -1){
                    break;
                }
            }

            // if the new added trip is closer than the previous coming trip, replace it with new tripID
            if(addedIndex<currentIndex){
                AlarmReceiver.setComingTripID(tripID);
            }

            int test = AlarmReceiver.getComingTripID();
            Log.d("test after add: ",Integer.toString(test));
        }



    }

    /**
     * this method is called when the the notification of lastID trip is already popped up, and
     * hence need to find the next trip for reminding
     */
    public static void updateComingTripID(){
        int lastID = AlarmReceiver.getComingTripID();
        Log.d("test initally: ",Integer.toString(lastID));
        int i;
        for(i = 0; i < scheduledTripList.size();i++){
            if (scheduledTripList.get(i).getTripID() == lastID){
                break;
            }
        }
        i+=1;
        Log.d("test next not index is ",Integer.toString(i));
        try{
            int nextID = scheduledTripList.get(i).getTripID();
            AlarmReceiver.setComingTripID(nextID);
            Log.d("!test: next coming is: ",Integer.toString(nextID));
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static int getTextSize() {
        return textSize;
    }
}
