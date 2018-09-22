package com.example.kallyruan.eldermap.NavigationPkg;

import android.util.Log;

import com.example.kallyruan.eldermap.GPSServicePkg.GPSTracker;
import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class NavigationChecker {

    private GPSTracker gps;

    ArrayList<Position> list = new ArrayList<>();
    /**
     * Navigation with http request
     */
    NavigationChecker(GPSTracker gps) throws JSONException, ExecutionException, InterruptedException {
        this.gps = gps;
        Location userLoc = gps.getLoc();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getUserLoc();
            }
        };
        timer.schedule(task, 5000);

        JSONObject obj = new JSONObject();
        obj.put("curLatitude", userLoc.getLatitude());
        obj.put("curLongitude", userLoc.getLongitude());
        //get destination coordinate after user chooses
        obj.put("desLatitude", userLoc.getLatitude());
        obj.put("desLongitude", userLoc.getLongitude()-0.001);
        JSONArray jsonArray = new JSONArray(new HTTPPostRequest("http://eldersmapapi.herokuapp.com/api/route").execute(obj).get());

        for(int i = 0; i < jsonArray.length(); i++) {
            Log.d("json_testing", jsonArray.get(i).toString());
            list.add(new Position(jsonArray.optJSONObject(i).getString("instruction"),
                    jsonArray.optJSONObject(i).getString("modifier"),
                    jsonArray.optJSONObject(i).getInt("bearing_after"),
                    jsonArray.optJSONObject(i).getInt("bearing_before"),
                    jsonArray.optJSONObject(i).getJSONArray("location")));
        }

    }

    public void getUserLoc() {
        Location userLoc = gps.getLoc();
        Iterator it1 = getPostions().iterator();
        while(it1.hasNext()) {
            if (userLoc.getLatitude() - list.get(0).getLatitude() < 0.00001 &&
                    userLoc.getLongitude() - list.get(0).getLongitude() < 0.00001) {
                it1.remove();
            }else {
                break;
            }
        }
        Log.d("testing", list.toString());
    }

    public ArrayList<Position> getPostions() {
        return list;
    }

}