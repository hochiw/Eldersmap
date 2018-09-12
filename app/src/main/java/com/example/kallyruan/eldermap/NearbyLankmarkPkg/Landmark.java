package com.example.kallyruan.eldermap.NearbyLankmarkPkg;
import com.example.kallyruan.eldermap.LocationPkg.Location;

import java.util.Comparator;

public class Landmark {

    private String name;
    private String address;
    private float rating;
    private Location location;
    private double distance;


    Landmark(String name, String address, float rating, Location location, Double distance){
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.location = location;
        this.distance = distance;
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
}

class Sortbyrating implements Comparator<Landmark>{
    public int compare(Landmark land1, Landmark land2){
        if (Float.isNaN(land1.getRating())){
            land1.setRating(0.0f);
        }
        if (Float.isNaN(land2.getRating())){
            land2.setRating(0.0f);
        }
        return (int) ((land2.getRating()*10) - (land1.getRating()*10));
    }
}
