package com.example.kallyruan.eldermap.NavigationPkg;

import com.example.kallyruan.eldermap.LocationPkg.Location;

public class ScheduleTrip {
    private int targetDay;
    private int targetMonth;
    private int targetYear;
    private int targetHour;
    private int targetMinute;
    private Location destination;

    ScheduleTrip(int day,int month, int year, int hour, int minute, Location address){
        this.targetDay = day;
        this.targetMonth = month;
        this.targetYear = year;
        this.targetHour = hour;
        this.targetMinute = minute;
        this.destination = address;
    }

    public int getTargetDay() {
        return targetDay;
    }

    public void setTargetDay(int targetDay) {
        this.targetDay = targetDay;
    }

    public int getTargetMonth() {
        return targetMonth;
    }

    public void setTargetMonth(int targetMonth) {
        this.targetMonth = targetMonth;
    }

    public int getTargetYear() {
        return targetYear;
    }

    public void setTargetYear(int targetYear) {
        this.targetYear = targetYear;
    }

    public int getTargetHour() {
        return targetHour;
    }

    public void setTargetHour(int targetHour) {
        this.targetHour = targetHour;
    }

    public int getTargetMinute() {
        return targetMinute;
    }

    public void setTargetMinute(int targetMinute) {
        this.targetMinute = targetMinute;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }
}
