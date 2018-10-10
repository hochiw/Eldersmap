package com.example.kallyruan.eldermap.LocationPkg;

public class Location {
    private Double latitude;
    private Double longitude;
    private Double altitude;

    Location(Double latitude, Double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = 0.0;
    }

    public void setLatitude(Double x){
        this.latitude = x;
    }

    public void setLongitude(Double y){
        this.longitude = y;
    }

    public void setAltitude(Double z) {this.altitude = z;}

    public Double getLatitude(){
        return latitude;
    }

    public Double getLongitude(){
        return longitude;
    }

    public Double getAltitude() {return altitude;}



    /**
     * This function is to let classes in other package instantiate and get a Location object.
     * @param x latitude
     * @param y longitude
     * @return Location object
     */
    public static Location getInstance(Double x, Double y){
        return new Location(x, y);
    }


}
