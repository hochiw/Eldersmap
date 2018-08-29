package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

public class Landmark {
    private String name;
    private int mark;
    private int cost;
    private Location location;

    Landmark(String name, int mark, int cost, Location location){
        this.name = name;
        this.mark = mark;
        this.cost = cost;
        this.location = location;
    }
    public String getName(){
        return name;
    }

    public int getMark(){
        return mark;
    }

    public int getCost(){
        return cost;
    }

    public Location getLocation(){
        return location;
    }
}
