package com.example.eldermap.NavigationPkg;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

/**
 * NotificationScheduler class is to set Reminder at a given time though AlarmManager
 */
public class NotificationScheduler
{
    /**
     * function to instatiate the scheduled trip notification
     * @param context Application context
     * @param cls class
     * @param year year
     * @param month month
     * @param day day
     * @param hour hour
     * @param min minute
     * @param tripID trip id
     * @return
     */
    public static boolean setReminder(Context context, Class<?> cls, int year, int month, int day,
                                      int hour, int min,int tripID)
    {
        //get a Calendar instance based on the current time
        Calendar calendar = Calendar.getInstance();

        //set the scheduled trip time
        Calendar setcalendar = Calendar.getInstance();
        setcalendar.set(year,month,day);
        setcalendar.set(Calendar.HOUR_OF_DAY, hour);
        setcalendar.set(Calendar.MINUTE, min);

        // check whether this scheduled time is in the future, show a error message if it is in the
        // past
        if(setcalendar.before(calendar)){
            Toast.makeText(context,"Wrong for creating a future trip, Please try it again!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            // Enable a receiver
            ComponentName receiver = new ComponentName(context, cls);
            PackageManager pm = context.getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

            // Create new intent
            Intent intent1 = new Intent(context, cls);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, tripID, intent1, PendingIntent.FLAG_ONE_SHOT);

            // Set an alarm
            AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            Toast.makeText(context, "A future reminder is successfully set!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
