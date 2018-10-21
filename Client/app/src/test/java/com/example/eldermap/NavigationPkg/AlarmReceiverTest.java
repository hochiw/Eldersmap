package com.example.eldermap.NavigationPkg;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import com.example.eldermap.ProfilePkg.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.support.membermodification.MemberMatcher.constructor;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

/**
 * AlarmReceiverTest written for AlarmReceiver class.
 * Methods tested include: getComingTripID,setComingTripId, onReceive.
 * There is another setupNotificationContent which is a private method, that will not
 * be tested(also involving ui setting.)
 * The main tools used are PowerMockito.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AlarmManager.class,
        TaskStackBuilder.class, User.class, Notification.Builder.class,
    AlarmReceiver.class})
public class AlarmReceiverTest {
    String TAG = "Hello";
    @Mock
    Notification notification;
    @Mock
    NotificationManager manager;
    @Mock
    Context context;
    @Mock
    Intent i;
    private static int comingTripID=0;

    private AlarmReceiver alarmReceiver;

    /**
     * Prepare for the later usage.
     * Initialise a new AlarmReceiver each time before the method running.
     */
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        alarmReceiver = new AlarmReceiver();
    }

    /**
     * Test getComingTripID
     * If success, it should return expected.
     */
    @Test
    public void getComingTripID() {
        int expected = 0;
        assertEquals(expected,AlarmReceiver.getComingTripID());
    }

    /**
     * Test setComingTripID
     * IF success, it should return expected.
     */
    @Test
    public void setComingTripID() {
        int expected = 10;
        AlarmReceiver.setComingTripID(expected);
        assertEquals(expected, AlarmReceiver.getComingTripID());
        AlarmReceiver.setComingTripID(0);
    }

    /**
     * Test onReceive.
     * TaskStackBuilder is mocked staticlly.
     * The calling on the private method is suppressed.
     * @throws Exception
     */
    @Test
    public void onReceive() throws Exception{
        Intent notificationIntent = PowerMockito.mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withAnyArguments().
                thenReturn(notificationIntent);
        PowerMockito.mockStatic(TaskStackBuilder.class);
        TaskStackBuilder stackBuilder = PowerMockito.mock(TaskStackBuilder.class);
        PowerMockito.when(TaskStackBuilder.create(context)).thenReturn(stackBuilder);

        PowerMockito.whenNew(TaskStackBuilder.class).withAnyArguments().
                thenReturn(stackBuilder);
        PowerMock.suppress(method(TaskStackBuilder.class,"addParentStack",
                Activity.class));
        PowerMockito.suppress(method(User.class, "updateComingTripID"));
        PowerMockito.suppress(constructor(Notification.Builder.class,Context.class));
        PowerMockito.suppress(method(AlarmReceiver.class, "setupNotificationContent"));
        alarmReceiver.onReceive(context, i);

    }
}