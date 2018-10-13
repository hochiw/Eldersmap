package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.util.Log;

import com.example.kallyruan.eldermap.LocationPkg.Location;

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
    public ArrayList<Landmark> filterList(JSONObject inList) throws JSONException, NullPointerException {
        ArrayList<Landmark> returnList = new ArrayList<Landmark>();
        JSONArray array = new JSONArray(inList.get("results").toString());
        for (int i = 0; i < array.length(); i++){
            JSONObject currentObj = array.getJSONObject(i);

            Location currentLoc = Location.getInstance((Double) currentObj.getJSONObject("location").get("lat"), (Double) currentObj.getJSONObject("location").get("lng"),0.0f,0);

            // Need to do a null pointer check before going on.
            Landmark curLandmark;
            try {
                curLandmark = new Landmark((String)currentObj.get("name"),
                        (String)currentObj.get("address"), ((Number)currentObj.get("rating")).floatValue(), currentLoc, 0.0);
            }catch (Exception e){
                curLandmark = new Landmark((String)currentObj.get("name"),
                        (String)currentObj.get("address"), 0.0f, currentLoc, 0.0);
                e.printStackTrace();
            }

            returnList.add(curLandmark);
        }
        Collections.sort(returnList, new Sortbyrating());
        for (int i = 0;i < returnList.size();i++) {
            Log.d("test",Float.toString(returnList.get(i).getRating()));
        }
        return returnList;
    }

}

