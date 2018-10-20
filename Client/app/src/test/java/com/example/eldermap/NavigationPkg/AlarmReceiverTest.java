package com.example.eldermap.NavigationPkg;
// TODO: NotificaitonScheduler and AlarmReceiver are the last two tests.Both classes are static.
// TODO: Then the only remaining package is P2PPkg,
// TODO: FileEncode, MsgCoder, MsgItem, SocketClient, VoiceCall. five more on P2P Tests.
// TODO: One more DBQuery.(Static)
// TODO: In total 8 more tests to go...
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import com.example.eldermap.NavigationPkg.AlarmReceiver;
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

import static org.junit.Assert.*;
import static org.powermock.api.support.membermodification.MemberMatcher.constructor;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

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

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        alarmReceiver = new AlarmReceiver();
    }


    @Test
    public void getComingTripID() {
        int expected = 0;
        assertEquals(expected,AlarmReceiver.getComingTripID());
    }

    @Test
    public void setComingTripID() {
        int expected = 10;
        AlarmReceiver.setComingTripID(expected);
        assertEquals(expected, AlarmReceiver.getComingTripID());
    }


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
        // TODO: HAHAHAHA Suppress finnaly works!!!!!!!!!!!!!!!!
        PowerMock.suppress(method(TaskStackBuilder.class,"addParentStack",
                Activity.class));
        PowerMockito.suppress(method(User.class, "updateComingTripID"));
        PowerMockito.suppress(constructor(Notification.Builder.class,Context.class));
        PowerMockito.suppress(method(AlarmReceiver.class, "setUpNotificationContent"));
        alarmReceiver.onReceive(context, i);

    }
}