package com.example.kallyruan.eldermap.NavigationPkg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TimePicker;

import com.example.kallyruan.eldermap.DBQuery;
import com.example.kallyruan.eldermap.LocationPkg.ScheduledTrip;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.LandmarkListActivity;
import com.example.kallyruan.eldermap.ProfilePkg.User;
import com.example.kallyruan.eldermap.R;

public class ScheduleTimeActivity extends AppCompatActivity{
    //private static int tripID;
    public static int targetDay;
    public static int targetMonth;
    public static int targetYear;
    public static int targetHour;
    public static int targetMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //by default, start the activity with date selection page
        setDateView();
    }

    private void setDateView() {
        setContentView(R.layout.navigation_schedule_date);
        CalendarView calendarView = findViewById(R.id.calendarView);
        //get target day from UI intersection
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                targetDay = dayOfMonth;
                targetMonth = month;
                targetYear = year;
            }
        });
    }


    public void toSelectTime(View view){
        setContentView(R.layout.navigation_schedule_time);
        TimePicker timePicker = findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                targetHour = hourOfDay;
                targetMinute = minute;
            }
        });
    }


    public void backToDate(View view){
        setDateView();
    }


    public void cancelSchedule(View view){
        Intent i = new Intent(this, LandmarkListActivity.class);
        startActivity(i);
    }

    //this function is to add a schedule reminder to NotificationScheduler class
    public void setSchedule(View view){
        int tripID = DBQuery.createPlanID();
        // check whether can add this chosen trip as future trip
        Boolean result = NotificationScheduler.setReminder(this, AlarmReceiver.class, targetYear,targetMonth,
                targetDay,targetHour, targetMinute,tripID);
        Log.d("test","check add schedule result");

        //if successfully added the reminder, add this trip to user database
        if(result){
            ScheduledTrip planTrip = ScheduledTrip.getInstance(tripID,targetDay,targetMonth+1, targetYear,
                    targetHour, targetMinute,LandmarkListActivity.getDestination(),
                    LandmarkListActivity.getDestinationName());
            //add the new plan to the database and sort them by date
            User.addScheduledTrip(this, planTrip);
            User.checkAddComingTripID(tripID);
        }
        //re-direct user to the lankmark list page
        finish();
    }
}
