package com.example.kallyruan.eldermap.NavigationPkg;

public class Position {
    private String instruction;
    private int bearing_before;
    private int bearing_after;
    private double latitude;
    private double longitude;
    private String modifier;

    Position (String instruction, String modifier, int bearing_after, int bearing_before, double latitude, double longitude) {
        this.instruction = instruction;
        this.modifier = modifier;
        this.bearing_after = bearing_after;
        this.bearing_before = bearing_before;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getInstruction(){ return instruction;}
    public String getModifier() { return modifier; }
    public int getBearing_after() { return bearing_after; }
    public int getBearing_before() { return bearing_before; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
