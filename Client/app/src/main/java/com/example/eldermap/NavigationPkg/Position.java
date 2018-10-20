package com.example.eldermap.NavigationPkg;

import org.json.JSONArray;
import org.json.JSONException;

public class Position {
    private String instruction;
    private int bearing_before;
    private int bearing_after;
    private double latitude;
    private double longitude;
    private String modifier;
    private JSONArray location;

    Position (String instruction, String modifier, int bearing_after, int bearing_before, JSONArray location) throws JSONException{
        this.instruction = instruction;
        this.modifier = modifier;
        this.bearing_after = bearing_after;
        this.bearing_before = bearing_before;
        this.location = location;
        parseJSON(this.location);
    }

    public String getInstruction(){ return instruction;}
    public String getModifier() { return modifier; }
    public int getBearing_after() { return bearing_after; }
    public int getBearing_before() { return bearing_before; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    /**
     * Parses location JSON array into a position object
     * @param location
     * @throws JSONException
     */
    private void parseJSON(JSONArray location) throws JSONException {
        this.latitude = location.getDouble(1);
        this.longitude = location.getDouble(0);
    }
}