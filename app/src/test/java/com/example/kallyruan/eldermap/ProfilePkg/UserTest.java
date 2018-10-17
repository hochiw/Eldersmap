package com.example.kallyruan.eldermap.ProfilePkg;

import android.content.Context;

import com.example.kallyruan.eldermap.DBQuery;
import com.example.kallyruan.eldermap.LocationPkg.FinishedTrip;
import com.example.kallyruan.eldermap.LocationPkg.ScheduledTrip;
import com.example.kallyruan.eldermap.NavigationPkg.AlarmReceiver;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verify;

import org.mockito.BDDMockito;
import org.mockito.internal.exceptions.MockitoLimitations;
import org.powermock.api.easymock.PowerMock;
import org.junit.Test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

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


    @Before
    public void setup(){
        PowerMockito.mockStatic(DBQuery.class);
        PowerMockito.mockStatic(User.class);

        MockitoAnnotations.initMocks(this);
        ScheduledTrip scheduledTrip = Mockito.mock(ScheduledTrip.class);
        scheduledTripArrayList.add(scheduledTrip);
        FinishedTrip finishedTrip = Mockito.mock(FinishedTrip.class);
        historyTripList.add(finishedTrip);

        Mockito.when(historyTripList.get(0)).thenReturn(finishedTrip);

    }

    @Test
    public void getScheduledTripList() {
        Mockito.when(User.getScheduledTripList()).thenReturn(scheduledTripArrayList);
        assertEquals(scheduledTripArrayList,User.getScheduledTripList());
    }

    @Test
    public void getUserID() {
        Mockito.when(User.getUserID()).thenReturn(userID);
        assertEquals(userID,User.getUserID());
    }

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

    }

    @Test
    public void notifytextSizeChange() {
        int textSizePreference = 0, expected = 0;
        Context context = Mockito.mock(Context.class);
        Mockito.when(DBQuery.updateTextsize(0)).thenReturn(true);
        PowerMockito.doNothing().when(User.class);
        User.notifytextSizeChange(context,textSizePreference);
        PowerMockito.verifyStatic();
    }

    @Test
    public void notifyPermissionChange() {
        boolean permissionPreference = true;
        Context context = Mockito.mock(Context.class);
        Mockito.when(DBQuery.updatePermission(permissionPreference)).thenReturn(true);
        User.notifyPermissionChange(context,permissionPreference);
        PowerMockito.verifyStatic();
    }

    @Test
    public void notifyWalkingChange() {
        boolean result = true;
        int walking = 0;
        Context context = Mockito.mock(Context.class);
        Mockito.when(DBQuery.updateWalking(walking)).thenReturn(result);
        User.notifyWalkingChange(context, walking);
        PowerMockito.verifyStatic();
    }

    @Test
    public void addScheduledTrip() {
        boolean result = true;
        Context context = Mockito.mock(Context.class);
        ScheduledTrip trip = Mockito.mock(ScheduledTrip.class);
        User.addScheduledTrip(context,trip);
        PowerMockito.verifyStatic();
    }

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

    @Test
    public void addUserHistory() {
    }

    @Test
    public void getUserPlan() {
        PowerMockito.when(User.getUserPlan()).thenReturn(scheduledTripArrayList);
        assertEquals(scheduledTripArrayList,User.getUserPlan());
    }

    @Test
    public void getUserHistory() {
        PowerMockito.when(User.getUserHistory()).thenReturn(historyTripList);
        assertEquals(historyTripList, User.getUserHistory());
    }

    @Test
    public void updateHistoryReview() {
    }

    @Test
    public void checkAddComingTripID() {
    }

    @Test
    public void updateComingTripID() {
    }

    @Test
    public void getTextSize() {
    }
}