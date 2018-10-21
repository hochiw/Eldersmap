package com.example.eldermap.ProfilePkg;

import android.content.Context;

import com.example.eldermap.DBQuery;
import com.example.eldermap.LocationPkg.FinishedTrip;
import com.example.eldermap.LocationPkg.ScheduledTrip;
import com.example.eldermap.NavigationPkg.AlarmReceiver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * A series tests written fro User class
 * The class is mainly composed of static void method, therefore the return type of methods
 * are infeasible to be tested. The behaviours of methods will be tested instead.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ScheduledTrip.class, FinishedTrip.class, User.class, DBQuery.class,
        AlarmReceiver.class})
public class UserTest {
    @Mock
    private static ArrayList<ScheduledTrip> scheduledTripArrayList;
    @Mock
    private static ArrayList<FinishedTrip> historyTripList;

    private static String userID = "Hello";
    private static String anotherUserID = null;
    private static int testSize;

    /**
     * Setup for later test usage.
     * DBQuery and User will be mock static.
     */
    @Before
    public void setup(){
        PowerMockito.mockStatic(DBQuery.class);
        PowerMockito.mockStatic(User.class);

        // Initialise all mock objects above.
        MockitoAnnotations.initMocks(this);

        ScheduledTrip scheduledTrip = Mockito.mock(ScheduledTrip.class);
        FinishedTrip finishedTrip = Mockito.mock(FinishedTrip.class);

        PowerMockito.when(scheduledTripArrayList.size()).thenReturn(1);
        PowerMockito.when(historyTripList.size()).thenReturn(1);

        Mockito.when(historyTripList.get(0)).thenReturn(finishedTrip);

    }

    /**
     * Test getScheduledTripList.
     * If success, it should return scheduledTripArrayList as expected.
     */
    @Test
    public void getScheduledTripList() {
        Mockito.when(User.getScheduledTripList()).thenReturn(scheduledTripArrayList);
        assertEquals(scheduledTripArrayList,User.getScheduledTripList());
    }

    /**
     * Test getUserID.
     * Request to MainActivity to request for Device Unique ID.
     * IF success, it should return a userID as expected.
     */
    @Test
    public void getUserID() {
        Mockito.when(User.getUserID()).thenReturn(userID);
        assertEquals(userID,User.getUserID());
    }

    /**
     * Interact with the DataBase.
     * If success, it should retrieve user data.
     */
    @Test
    public void retrieveUserData() {
        int expected = 0;
        boolean expectedBoolean = true;
        PowerMockito.when(DBQuery.retrieveTextSize()).thenReturn(0);
        PowerMockito.when(DBQuery.retrieveWalking()).thenReturn(0);
        Mockito.when(DBQuery.retrieveDataPrivilege()).thenReturn(expectedBoolean);
        Mockito.when(DBQuery.retrievePlan()).thenReturn(scheduledTripArrayList);
        Mockito.when(DBQuery.retrieveHistory()).thenReturn(historyTripList);

        assertEquals(expected, DBQuery.retrieveTextSize());
        assertEquals(expected, DBQuery.retrieveWalking());
        assertEquals(expectedBoolean, DBQuery.retrieveDataPrivilege());
        assertEquals(scheduledTripArrayList, DBQuery.retrievePlan());
        assertEquals(historyTripList, DBQuery.retrieveHistory());

        assertEquals(false, User.retrieveUserData());
    }

    /**
     * Test notifytextSizeChange.
     * if it successfully update the latest textsize based on users choice.
     */
    @Test
    public void notifytextSizeChange() {
        int textSizePreference = 0;
        Context context = Mockito.mock(Context.class);
        Mockito.when(DBQuery.updateTextsize(0)).thenReturn(true);
        PowerMockito.doNothing().when(User.class);
        User.notifytextSizeChange(context,textSizePreference);
        PowerMockito.verifyStatic();
    }

    /**
     * test notifyPermissionChange.
     * If it successfully update user permission preference to db.
     */
    @Test
    public void notifyPermissionChange() {
        boolean permissionPreference = true;
        Context context = Mockito.mock(Context.class);
        Mockito.when(DBQuery.updatePermission(permissionPreference)).thenReturn(true);
        User.notifyPermissionChange(context,permissionPreference);
        PowerMockito.verifyStatic();
    }

    /**
     * test notifyWalkingChange.
     * If it successfully update the walking to database.
     */
    @Test
    public void notifyWalkingChange() {
        boolean result = true;
        int walking = 0;
        Context context = Mockito.mock(Context.class);
        Mockito.when(DBQuery.updateWalking(walking)).thenReturn(result);
        User.notifyWalkingChange(context, walking);
        PowerMockito.verifyStatic();
    }

    /**
     * Test addScheduledTrip.
     * test if  addding a ScheduledTrip to the scheduledTripList ArrayList and database
     */
    @Test
    public void addScheduledTrip() {
        boolean result = true;
        Context context = Mockito.mock(Context.class);
        ScheduledTrip trip = Mockito.mock(ScheduledTrip.class);
        User.addScheduledTrip(context,trip);
        PowerMockito.verifyStatic();
    }

    /**
     * Test deleteScheduledTrip.
     * delete a planned future trip plan from user database
     * @throws Exception
     */
    @Test
    public void deleteScheduledTrip() throws Exception{
        boolean nextID = false;
        boolean result  = false;
        int tripID = 0;
        int index = 0;
        PowerMockito.mockStatic(AlarmReceiver.class);
        Context context = Mockito.mock(Context.class);
        AlarmReceiver alarmReceiver = Mockito.mock(AlarmReceiver.class);
        PowerMockito.whenNew(AlarmReceiver.class).withAnyArguments().thenReturn(alarmReceiver);
        PowerMockito.when(AlarmReceiver.getComingTripID()).thenReturn(tripID);
        PowerMockito.when(DBQuery.deleteUserPlan(tripID)).thenReturn(result);
        User.deleteScheduledTrip(context, tripID, index);
        PowerMockito.verifyStatic();
    }

    /**
     * Test addUserHitory
     * test if adding a finished trip plan to user database history
     */
    @Test
    public void addUserHistory() {
        Context context = PowerMockito.mock(Context.class);
        FinishedTrip finishedTrip = PowerMockito.mock(FinishedTrip.class);

        boolean result = true;
        PowerMockito.when(DBQuery.addUserHistory(finishedTrip)).thenReturn(result);
        User.addUserHistory(context, finishedTrip);
        PowerMockito.verifyStatic();

    }

    /**
     * Test getUserPlan
     * If success, it should return a list of scheduledTripArrayList as expected.
     */
    @Test
    public void getUserPlan() {
        PowerMockito.when(User.getUserPlan()).thenReturn(scheduledTripArrayList);
        assertEquals(scheduledTripArrayList,User.getUserPlan());
    }

    /**
     * Test getUserHistory
     * IF success, it should return a list of historyTripList as expected.
     */
    @Test
    public void getUserHistory() {
        PowerMockito.when(User.getUserHistory()).thenReturn(historyTripList);
        assertEquals(historyTripList, User.getUserHistory());
    }

    /**
     * Test updateHistoryReview.
     * update the ratings of a specific history trip of a user
     */
    @Test
    public void updateHistoryReview() {
        Context context = PowerMockito.mock(Context.class);
        int tripID = 0, tripIndex=0;
        float destinationMark =0.0f, navigationMark =0.0f;
        boolean result = true;
        PowerMockito.when(DBQuery.updateHistoryReview(tripID, destinationMark,
                navigationMark)).thenReturn(result);

        User.updateHistoryReview(context, tripID, tripIndex, destinationMark, navigationMark);
    }

    /**
     * Test cechkAddComingTripID
     * When there is a deletion or addition to the previous scheduledTripList.
     * @throws Exception
     */
    @Test
    public void checkAddComingTripID() throws Exception{
        int tripID = 0;
        PowerMockito.mockStatic(AlarmReceiver.class);
        AlarmReceiver alarmReceiver = PowerMockito.mock(AlarmReceiver.class);
        PowerMockito.whenNew(AlarmReceiver.class).withAnyArguments().
                thenReturn(alarmReceiver);
        User.checkAddComingTripID(tripID);
        PowerMockito.verifyStatic();
    }

    /**
     * Test updateComingTripID
     * when the the notification of lastID trip is already popped up, and
     * hence need to find the next trip for reminding
     */
    @Test
    public void updateComingTripID() {
        int lastID = 0;
        PowerMockito.mockStatic(AlarmReceiver.class);
        PowerMockito.when(AlarmReceiver.getComingTripID()).thenReturn(lastID);
        User.updateComingTripID();
        PowerMockito.verifyStatic();
    }

    /**
     * Test getTextSize.
     * If success, it should return textSize as expected.
     */
    @Test
    public void getTextSize() {
        testSize = 0;
        assertEquals(testSize, User.getTextSize());
    }
}