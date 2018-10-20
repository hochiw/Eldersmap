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
import java.util.concurrent.ExecutionException;

public class NavigationChecker {

    private Location userLoc;

    private ArrayList<Position> list = new ArrayList<>();
    /**
     * This class is used to check user current location and compares with navigation instruction,
     * doing so with timer task will provide a checker that constantly checking user location
     * to make sure the app delivers accurate navigation
     */
    NavigationChecker(Location userLoc,Location destLoc) throws JSONException, ExecutionException, InterruptedException {
        this.userLoc = userLoc;
        
        // user destination coordinate still needed, currently hard-coded
        JSONObject obj = new JSONObject();
        obj.put("curLatitude", userLoc.getLatitude());
        obj.put("curLongitude", userLoc.getLongitude());
        //get destination coordinate after user chooses
        obj.put("desLatitude", destLoc.getLatitude());
        obj.put("desLongitude", destLoc.getLongitude());
        JSONArray jsonArray = new JSONArray(new HTTPPostRequest("http://eldersmapapi.herokuapp.com/api/route").execute(obj).get());

        //parsing the JSON array into a list that contains the checkpoints of the path
        for(int i = 0; i < jsonArray.length(); i++) {
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
    public void checkpointDetection() {
        Iterator it1 = getPositions().iterator();
        //if user is within a 10m radius of the current checkpoint, move onto the next checkpoint
        while(it1.hasNext()) {
            if (CoorDist.getDist(userLoc.getLatitude(),userLoc.getLongitude(),list.get(0).getLatitude(),list.get(0).getLongitude()
            ) < 10) {
                //removes the checkpoint
                list.remove(0);
            }else {
                break;
            }
        }
    }

    /**
     * Calculates angle between the coordinates
     * @return
     */
    public double calculateAngle() {
        android.location.Location userLocation = new android.location.Location("User");
        android.location.Location desLocation = new android.location.Location("Destination");
        userLocation.setLatitude(userLoc.getLatitude());
        userLocation.setLongitude(userLoc.getLongitude());
        desLocation.setLatitude(list.get(0).getLatitude());
        desLocation.setLongitude(list.get(0).getLongitude());

        return userLocation.bearingTo(desLocation);
    }

    /**
     * Calculates Geomagnetic field of the device
     * @return
     */
    public GeomagneticField calcGeofield() {
        return new GeomagneticField( Double.valueOf( userLoc.getLatitude() ).floatValue(), Double
                .valueOf( userLoc.getLongitude() ).floatValue(),
                Double.valueOf( userLoc.getAltitude() ).floatValue(),
                System.currentTimeMillis());
    }

    public double getDistance() {
        return CoorDist.getDist(userLoc.getLatitude(),userLoc.getLongitude(),list.get(0).getLatitude(),list.get(0).getLongitude());
    }

    public ArrayList<Position> getPositions() {
        return list;
    }

    public void setUserLoc(Location userLoc) {
        this.userLoc = userLoc;
    }

}
