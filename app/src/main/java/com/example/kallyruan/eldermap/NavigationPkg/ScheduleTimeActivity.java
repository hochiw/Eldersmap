package com.example.kallyruan.eldermap.NavigationPkg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TimePicker;

import com.example.kallyruan.eldermap.NearbyLankmarkPkg.LandmarkListActivity;
import com.example.kallyruan.eldermap.R;

import java.sql.Time;
import java.util.Date;

public class ScheduleTimeActivity extends Activity{
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

    //this function is to create an alert when this scheduled time arrives
    public void setSchedule(View view){
        ScheduleTrip planTrip = new ScheduleTrip(targetDay,targetMonth, targetYear, targetHour, targetMinute,
                                                    LandmarkListActivity.getDestination());
        
    }
}
