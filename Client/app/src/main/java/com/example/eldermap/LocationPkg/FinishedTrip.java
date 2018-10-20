package com.example.eldermap.LocationPkg;

public class FinishedTrip extends Trip {
    private float destinationMark;
    private float navigationMark;

   public FinishedTrip(int id, int day, int month, int year, Location address, String name, float landmarkMark,
                 float navigationMark) {
        super(id, day, month, year, address, name);
        this.destinationMark = landmarkMark;
        this.navigationMark = navigationMark;
    }


    public float getDestinationMark() {
        return destinationMark;
    }

    public float getTripMark() {
        return navigationMark;
    }

}
