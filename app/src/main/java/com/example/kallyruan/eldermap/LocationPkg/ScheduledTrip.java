package com.example.kallyruan.eldermap.LocationPkg;

import android.support.annotation.NonNull;

public class ScheduledTrip extends Trip implements Comparable<ScheduledTrip>{
    private int targetHour;
    private int targetMinute;

    ScheduledTrip(int id, int day, int month, int year, int hour, int minute, Location address, String name){
        super(id, day,month,year,address,name);
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

    public static ScheduledTrip getInstance(int id, int day, int month, int year, int hour, int minute ,
                                            Location address, String name){
        return new ScheduledTrip(id, day,month, year, hour, minute,address, name);
    }

    @Override
    public String getDate() {
       return  String.format("%s %2d:%2d", super.getDate(), targetHour, targetMinute);
    }

    @Override
    public int compareTo(@NonNull ScheduledTrip o) {
        if(this.getTargetYear()> o.getTargetYear()){
            return 1;
        }else if(this.getTargetYear() == o.getTargetYear() &&
                this.getTargetMonth() > o.getTargetMonth()){
            return 1;
        }else if (this.getTargetYear() == o.getTargetYear() &&
                this.getTargetMonth() == o.getTargetMonth()&&
                this.getTargetDay() > o.getTargetDay()){
            return 1;
        }else if (this.getTargetYear() == o.getTargetYear() &&
                this.getTargetMonth() == o.getTargetMonth()&&
                this.getTargetDay() == o.getTargetDay()&&
                this.targetHour > o.targetHour){
            return 1;
        }else if (this.getTargetYear() == o.getTargetYear() &&
                this.getTargetMonth() == o.getTargetMonth()&&
                this.getTargetDay() == o.getTargetDay()&&
                this.targetHour == o.targetHour &&
                this.targetMinute > o.targetMinute) {
            return 1;
        }else{
            return -1;
        }

    }
}
