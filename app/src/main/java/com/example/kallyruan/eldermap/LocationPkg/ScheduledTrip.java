package com.example.kallyruan.eldermap.LocationPkg;

public class ScheduledTrip extends Trip {
    private int targetHour;
    private int targetMinute;

    ScheduledTrip(int day, int month, int year, int hour, int minute, Location address, String name){
        super(day,month,year,address,name);
        this.targetHour = hour;
        this.targetMinute = minute;
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

    public static ScheduledTrip getInstance(int day, int month, int year, int hour, int minute ,
                                            Location address, String name){
        return new ScheduledTrip(day,month, year, hour, minute,address, name);
    }

}
