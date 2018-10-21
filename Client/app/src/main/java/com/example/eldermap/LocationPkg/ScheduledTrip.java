package com.example.eldermap.LocationPkg;

import android.support.annotation.NonNull;

/**
 * ScheduledTrip class is the class storing all information about Scheduled Trip. As a extended class
 * from Trip class, it inherents all features from Trip class and further adds trip time variables.
 * This class also implements comparable interface, which could sort by trip date.
 */
public class ScheduledTrip extends Trip implements Comparable<ScheduledTrip>{

    // Initialize the variables
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

    /**
     * this method is to return formated date
     * @return Formatted date
     */
    @Override
    public String getDate() {
       return  String.format("%s %2d:%2d", super.getDate(), targetHour, targetMinute);
    }


    /**
     * Comparator for the scheduled trip
     * @param o a given ScheduledTrip for comparsion
     * @return comparsion result
     */
    @Override
    public int compareTo(@NonNull ScheduledTrip o) {
        // Compare the scheduled trips
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
