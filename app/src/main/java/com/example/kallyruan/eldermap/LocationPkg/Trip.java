package com.example.kallyruan.eldermap.LocationPkg;

public class Trip {
    private int targetDay;
    private int targetMonth;
    private int targetYear;
    private Location destination;
    private String name;

    Trip(int day, int month, int year, Location address, String name){
        this.targetDay = day;
        this.targetMonth = month;
        this.targetYear = year;
        this.destination = address;
        this.name = name;
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

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
