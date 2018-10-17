package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.util.Log;

import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.NavigationPkg.CoorDist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Kah Chun on 01/09/2018.
 */

public class SearchAlg {

    //Takes a string returned from the API server
    public ArrayList<Landmark> filterList(JSONObject inList, Location userLoc) throws JSONException, NullPointerException {
        ArrayList<Landmark> returnList = new ArrayList<Landmark>();
        JSONArray array = new JSONArray(inList.get("results").toString());
        for (int i = 0; i < array.length(); i++){
            JSONObject currentObj = array.getJSONObject(i);

            Location currentLoc = Location.getInstance((Double) currentObj.getJSONObject("location").get("lat"), (Double) currentObj.getJSONObject("location").get("lng"));

            double distance = CoorDist.getDist(userLoc.getLatitude(),userLoc.getLongitude(),currentLoc.getLatitude(),currentLoc.getLongitude());



            // Need to do a null pointer check before going on.
            Landmark curLandmark;


            try {
                curLandmark = new Landmark((String)currentObj.get("name"),
                        (String)currentObj.get("address"), ((Number)currentObj.get("rating")).floatValue(), currentLoc, estWalkTime(distance));
            }catch (Exception e){
                curLandmark = new Landmark((String)currentObj.get("name"),
                        (String)currentObj.get("address"), 0.0f, currentLoc, estWalkTime(distance));
                e.printStackTrace();
            }

            returnList.add(curLandmark);
        }
        Collections.sort(returnList, new Sortbyrating());

        int k = returnList.size();

        if (k > 5) {
            returnList.subList(5,k).clear();
        }

        return returnList;

    }

    public int estWalkTime(double walkDist) {
        if (0 < walkDist && walkDist <= 250) {
            return 5;
        } else if (250 < walkDist && walkDist <= 500) {
            return 10;
        } else if (500 < walkDist && walkDist <= 750) {
            return 15;
        } else if (750 < walkDist && walkDist <= 1000) {
            return 20;
        } else {
            return 30;
        }
    }

}

