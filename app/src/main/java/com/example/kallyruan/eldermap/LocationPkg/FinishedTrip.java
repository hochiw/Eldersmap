package com.example.kallyruan.eldermap.LocationPkg;

public class FinishedTrip extends Trip {
    private float reviewMark;
    private float navigationMark;

    FinishedTrip(int day, int month, int year, Location address, String name, float landmarkMark,
                 float navigationMark) {
        super(day, month, year, address, name);
        this.reviewMark = landmarkMark;
        this.navigationMark = navigationMark;
    }


    public float getReviewMark() {
        return reviewMark;
    }

    public void setReviewMark(int reviewMark) {
        this.reviewMark = reviewMark;
    }

    public float getTripMark() {
        return navigationMark;
    }

    public void setTripMark(float navigationMark) {
        this.navigationMark = navigationMark;
    }
}
