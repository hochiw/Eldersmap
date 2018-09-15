package com.example.kallyruan.eldermap.NavigationPkg;

import com.example.kallyruan.eldermap.NearbyLankmarkPkg.LandmarkListActivity;

import com.example.kallyruan.eldermap.GPSServicePkg.GPSTracker;
import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class NavigationChecker {

    private GPSTracker gps;
    /**
     * Navigation with http request
     */
    public void navigateChecker() throws JSONException, ExecutionException, InterruptedException {
        Location userLoc = gps.getLoc();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            }
        }
        ArrayList<Position> list = new ArrayList<>();
        JSONObject obj = new JSONObject();
        obj.put("curLatitude", userLoc.getLatitude());
        obj.put("curLongitude", userLoc.getLongitude());
        //get destination coordinate?
        obj.put("desLatitude", 0.000000);
        obj.put("desLongitude", 0.000000);
        JSONObject jsonObject = new HTTPPostRequest("http://eldersmapapi.herokuapp.com/api/route").execute(obj).get();
        JSONArray jsonArray =  new JSONArray(jsonObject.toString());

        for(int i = 0; i < jsonArray.length(); i++) {
            list.add(new Position(jsonArray.optJSONObject(i).getString("instruction"),
                    jsonArray.optJSONObject(i).getString("modifier"),
                    jsonArray.optJSONObject(i).getInt("bearing_after"),
                    jsonArray.optJSONObject(i).getInt("bearing_before"),
                    jsonArray.optJSONArray(i).getDouble(1),
                    jsonArray.optJSONArray(i).getDouble(0)));
        }

        Iterator it2 = list.iterator();
        while(it2.hasNext()) {
            if (userLoc.getLatitude() - list.get(0).getLatitude() < 0.001 &&
                    userLoc.getLongitude() - list.get(0).getLongitude() < 0.001) {
                it2.remove();
            }else {
                break;
            }
        }

    }

    public Location getUserLoc() {
        Location userLoc = gps.getLoc();
        return userLoc;
    }

}