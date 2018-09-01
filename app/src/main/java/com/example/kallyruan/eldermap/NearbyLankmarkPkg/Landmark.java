package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

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
}

class Sortbyrating implements Comparator<Landmark>{
    public int compare(Landmark land1, Landmark land2){
        return (int) (land2.getRating()-land1.getRating());
    }
}
