package com.example.kallyruan.eldermap.LocationPkg;

public class Location {
    private Double latitude;
    private Double longitude;

    Location(Double latitude, Double Longitude){
        this.latitude = latitude;
        this.longitude = Longitude;
    }

    public void setLatitude(Double x){
        this.latitude = x;
    }

    public void setLongitude(Double y){
        this.longitude = y;
    }

    public Double getLatitude(){
        return latitude;
    }

    public Double getLongitude(){
        return longitude;
    }

    /**
     * This function is to let classes in other package instantiate and get a Location object.
     * @param x latitude
     * @param y longitude
     * @return Location object
     */
    public static Location getInstance(Double x, Double y){
        Location test = new Location(x, y);
        return test;
    }


}
