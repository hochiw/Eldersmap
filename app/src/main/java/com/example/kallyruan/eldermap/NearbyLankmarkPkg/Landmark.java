package com.example.kallyruan.eldermap.NearbyLankmarkPkg;
import com.example.kallyruan.eldermap.LocationPkg.Location;

import java.util.Comparator;

public class Landmark {

    private String name;
    private String address;
    private float rating;
    private Location location;
    private int estTime;


    Landmark(String name, String address, float rating, Location location, int estTime){
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.location = location;
        this.estTime = estTime;
    }

    //Getters and setters
    public String getName(){
        return name;
    }

    public float getRating(){
        return rating;
    }

    public String getAddress(){
        return address;
    }

    public Location getLocation(){
        return location;
    }

    public void setRating(float newValue){ this.rating = newValue;}

    public int getEstTime() {return estTime;}

    public void setEstTime(int newValue) {this.estTime = newValue;}
}

class Sortbyrating implements Comparator<Landmark>{
    public int compare(Landmark land1, Landmark land2){
        Integer time1 = land1.getEstTime();
        Integer time2 = land2.getEstTime();
        int timeCompare = time1.compareTo(time2);

        if (timeCompare != 0) {
            return timeCompare;
        }

        if (Float.isNaN(land1.getRating())){
            land1.setRating(0.0f);
        }
        if (Float.isNaN(land2.getRating())){
            land2.setRating(0.0f);
        }
        return (int) ((land2.getRating()*10) - (land1.getRating()*10));
    }
}
