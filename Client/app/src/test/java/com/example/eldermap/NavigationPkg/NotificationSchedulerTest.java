package com.example.eldermap.NavigationPkg;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * notificationScheduler is an object with a notification functionality.
 * Methods tested is setReminder. The test convers both branch of if-else.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({NotificationScheduler.class, PendingIntent.class})
public class NotificationSchedulerTest {

    public static String TAG = "HelloWorld";

    @Mock
    Context context;

    /**
     * SetUp for later test.
     * NotificationScheduler used a static method, need to prepare for static.
     */
    @Before
    public void setup(){
        PowerMockito.mockStatic(NotificationScheduler.class);
        context = PowerMockito.mock(Context.class);
    }

    /**
     * Test setReminderFalse.
     * This should hold for the first true part.
     * @throws Exception
     */
    @Test
    public void setReminderFalse() throws  Exception{
        int year=0, month=0, day=0, hour=0, min=0, tripID= 0;
        // Calendar is an abstract class. getInstance is a static method.
        PowerMockito.mockStatic(Calendar.class);
        Calendar calendar = PowerMockito.mock(Calendar.class);
        PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);

        Calendar setcalendar = PowerMockito.mock(Calendar.class);
        PowerMockito.when(Calendar.getInstance()).thenReturn(setcalendar);
        PowerMockito.doNothing().when(setcalendar).set(year,month,day);
        PowerMockito.doNothing().when(setcalendar).set(Calendar.HOUR_OF_DAY,hour);
        PowerMockito.doNothing().when(setcalendar).set(Calendar.MINUTE,min);
        // Should be used in true part only.


        Class<?> haha = String.class;
        NotificationScheduler.setReminder(context, haha, year, month, day,hour,min,tripID);
        // Branch one.
        boolean bol = false;
        PowerMockito.when(setcalendar.before(calendar)).thenReturn(true);

        assertEquals(bol, NotificationScheduler.setReminder(context, haha, year, month, day,hour, min,tripID));
    }

    /**
     * Another branch tested on setReminder.
     * @throws Exception
     */
    @Test
    public void setReminderTrue() throws  Exception{
        boolean bool = false;
        int year=0, month=0, day=0, hour=0, min=0, tripID= 0;
        PowerMockito.mockStatic(Calendar.class);
        Calendar calendar = PowerMockito.mock(Calendar.class);
        PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);

        Calendar setcalendar = PowerMockito.mock(Calendar.class);
        PowerMockito.when(Calendar.getInstance()).thenReturn(setcalendar);
        PowerMockito.when(setcalendar.before(calendar)).thenReturn(false);

        ComponentName receiver = PowerMockito.mock(ComponentName.class);
        PowerMockito.whenNew(ComponentName.class).withAnyArguments().
                thenReturn(receiver);
        PackageManager pm = PowerMockito.mock(PackageManager.class);
        PowerMockito.when(context.getPackageManager()).thenReturn(pm);
        PowerMockito.doNothing().when(pm).setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        Intent intent1 = PowerMockito.mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withAnyArguments().thenReturn(intent1);

        PowerMockito.mockStatic(PendingIntent.class);
        PendingIntent pendingIntent = PowerMockito.mock(PendingIntent.class);
        PowerMockito.when(PendingIntent.getBroadcast(context,tripID,
                intent1, PendingIntent.FLAG_ONE_SHOT)).thenReturn(pendingIntent);
        AlarmManager am = PowerMockito.mock(AlarmManager.class);
        PowerMockito.when(context.getSystemService(Context.
                ALARM_SERVICE)).thenReturn(am);

        Class<?> haha = String.class;
        assertEquals(bool, NotificationScheduler.setReminder(context, haha, year,month,day,hour,min,tripID));
    }

}