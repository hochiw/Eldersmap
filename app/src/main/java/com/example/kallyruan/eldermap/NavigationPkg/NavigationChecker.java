package com.example.kallyruan.eldermap.NavigationPkg;

import android.hardware.GeomagneticField;
import android.util.Log;

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

    Location userLoc;
    Location destLoc;

    ArrayList<Position> list = new ArrayList<>();
    /**
     * This class is used to check user current location and compares with navigation instruction,
     * doing so with timer task will provide a checker that constantly checking user location
     * to make sure the app delivers accurate navigation
     */
    NavigationChecker(Location userLoc,Location destLoc) throws JSONException, ExecutionException, InterruptedException {
        this.userLoc = userLoc;
        this.destLoc = destLoc;
        
        // user destination coordinate still needed, currently hard-coded
        JSONObject obj = new JSONObject();
        obj.put("curLatitude", userLoc.getLatitude());
        obj.put("curLongitude", userLoc.getLongitude());
        //get destination coordinate after user chooses
        obj.put("desLatitude", destLoc.getLatitude());
        obj.put("desLongitude", destLoc.getLongitude());
        JSONArray jsonArray = new JSONArray(new HTTPPostRequest("http://eldersmapapi.herokuapp.com/api/route").execute(obj).get());

        for(int i = 0; i < jsonArray.length(); i++) {
            Log.d("json_testing", jsonArray.get(i).toString());
            list.add(new Position(jsonArray.optJSONObject(i).getString("instruction"),
                    jsonArray.optJSONObject(i).has("modifier") ? jsonArray.optJSONObject(i).getString("modifier") : "None",
                    jsonArray.optJSONObject(i).getInt("bearing_after"),
                    jsonArray.optJSONObject(i).getInt("bearing_before"),
                    jsonArray.optJSONObject(i).getJSONArray("location")));
            Log.d("list_testing", list.get(i).getInstruction());
        }

    }


    /**
     * use method as a timer task, so the checker constantly get acknowledged of user latest location
     * and run a check with the navigation list
     */
    public void getUserLoc() {
        Iterator it1 = getPositions().iterator();
        while(it1.hasNext()) {
            //if (offRoute(userLoc, list.get(0))) {
            //    Log.d("offRoute", "Wrong direction, please remain course");
             //   break;
           // }
            Log.d("Angle",Double.toString(CoorDist.getAngle(userLoc.getLatitude(),userLoc.getLongitude(),list.get(0).getLatitude(),list.get(0).getLongitude())));
            if (CoorDist.getDist(userLoc.getLatitude(),userLoc.getLongitude(),list.get(0).getLatitude(),list.get(0).getLongitude()
            ) < 10) {
                Log.d("WEW","REMOVING YOUR SHIT");
                list.remove(0);
            }else {
                break;
            }
        }
        Log.d("testing", list.toString());
    }

    public double getAngle() {
     //  double angle = CoorDist.getAngle(userLoc.getLatitude(),userLoc.getLongitude(),list.get(0).getLatitude(),list.get(0).getLongitude());

        android.location.Location userLocation = new android.location.Location("User");
        android.location.Location desLocation = new android.location.Location("Destination");
        userLocation.setLatitude(userLoc.getLatitude());
        userLocation.setLongitude(userLoc.getLongitude());
        desLocation.setLatitude(list.get(0).getLatitude());
        desLocation.setLongitude(list.get(0).getLongitude());

        return userLocation.bearingTo(desLocation);
    }

    public GeomagneticField getGeoField() {
        return new GeomagneticField( Double.valueOf( userLoc.getLatitude() ).floatValue(), Double
                .valueOf( userLoc.getLongitude() ).floatValue(),
                Double.valueOf( userLoc.getAltitude() ).floatValue(),
                System.currentTimeMillis());
    }

    public double getDistance() {
        return CoorDist.getDist(userLoc.getLatitude(),userLoc.getLongitude(),list.get(0).getLatitude(),list.get(0).getLongitude());
    }

    /***
     * method used to check if user has gone the wrong way
     * and run a check with the navigation list
     */
    private Boolean offRoute(Location userLoc, Position position) {
        Double distance = CoorDist.getDist(userLoc.getLatitude(), userLoc.getLongitude(),
                position.getLatitude(), position.getLongitude());
        // pre-set off road distant, we will detect if user is 'this far away' from the destined
        // position
        if (distance > 30.00000) {
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

    public void setUserLoc(Location userLoc) {
        this.userLoc = userLoc;
    }

}
