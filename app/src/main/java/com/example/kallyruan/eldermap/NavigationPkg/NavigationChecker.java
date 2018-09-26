package com.example.kallyruan.eldermap.NavigationPkg;

import android.util.Log;

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

    ArrayList<Position> list = new ArrayList<>();
    /**
     * This class is used to check user current location and compares with navigation instruction,
     * doing so with timer task will provide a checker that constantly checking user location
     * to make sure the app delivers accurate navigation
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


    /**
     * use method as a timer task, so the checker constantly get acknowledged of user latest location
     * and run a check with the navigation list
     */
    public void getUserLoc() {
        Location userLoc = gps.getLoc();
        Iterator it1 = getPositions().iterator();
        while(it1.hasNext()) {
            if (offRoute(userLoc, list.get(0))) {
                break;
            }
            if (userLoc.getLatitude() - list.get(0).getLatitude() < 0.00001 &&
                    userLoc.getLongitude() - list.get(0).getLongitude() < 0.00001) {
                it1.remove();
            }else {
                break;
            }
        }
        Log.d("testing", list.toString());
    }

    /***
     * method used to check if user has gone the wrong way
     *
     */
    public Boolean offRoute(Location userLoc, Position position) {
        CoorDist calCoor = new CoorDist(userLoc.getLatitude(), userLoc.getLongitude(),
                position.getLatitude(), position.getLongitude());
        // pre-set off road distant, we will detect if user is 'this far away' from the destined
        // position
        if (calCoor.getDist() > 30.00000 && userLoc.getBearing() - position.getBearing_before() > 90) {
            Log.d("offRoute", "Wrong direction, please remain course");
            return true;
        }
        return false;
    }

    /***
     * get method
     * @return arraylist
     */
    public ArrayList<Position> getPositions() {
        return list;
    }

}
