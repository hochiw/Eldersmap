package com.example.kallyruan.eldermap.LocationPkg;

public class Location {
    private Double latitude;
    private Double longitude;
    private int type;
    private float bearing;

    Location(Double latitude, Double longitude,float bearing, int type){
        this.latitude = latitude;
        this.longitude = longitude;
        this.bearing = bearing;
        this.type = type;
    }

    public void setLatitude(Double x){
        this.latitude = x;
    }

    public void setLongitude(Double y){
        this.longitude = y;
    }

    public void setBearing(float z){
        this.bearing = z;
    }

    public Double getLatitude(){
        return latitude;
    }

    public Double getLongitude(){
        return longitude;
    }

    public float getBearing(){
        return bearing;
    }


    /**
     * This function is to let classes in other package instantiate and get a Location object.
     * @param x latitude
     * @param y longitude
     * @return Location object
     */
    public static Location getInstance(Double x, Double y, float z,int type){
        return new Location(x, y, z,type);
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
