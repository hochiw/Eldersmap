package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

<<<<<<< HEAD
=======
import android.util.Log;

import com.example.kallyruan.eldermap.LocationPkg.Location;

>>>>>>> iteration1
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Kah Chun on 01/09/2018.
 */

public class SearchAlg {
<<<<<<< HEAD
    //Variable Declaration
    private ArrayList<Landmark> returnList;


    public SearchAlg(){
        returnList = new ArrayList<>();
    }

    //Takes a string returned from the API server
    public ArrayList<Landmark> filterList(JSONArray inList) throws JSONException {
        for (int i = 0; i < inList.length(); i++){
            JSONObject currentObj = inList.getJSONObject(i);
            Location currentLoc = new Location((Double) currentObj.getJSONObject("location").get("lat"), (Double) currentObj.getJSONObject("location").get("lng"));
            Landmark curLandmark = new Landmark((String)currentObj.get("name"), (String)currentObj.get("address"), (float)currentObj.get("rating"), currentLoc, 0.0);
            returnList.add(curLandmark);
        }
        Collections.sort(returnList, new Sortbyrating());
=======

    //Takes a string returned from the API server
    public ArrayList<Landmark> filterList(JSONObject inList) throws JSONException {
        ArrayList<Landmark> returnList = new ArrayList<Landmark>();
        JSONArray array = new JSONArray(inList.get("results").toString());
        for (int i = 0; i < array.length(); i++){
            JSONObject currentObj = array.getJSONObject(i);
            Location currentLoc = Location.getInstance((Double) currentObj.getJSONObject("location").get("lat"), (Double) currentObj.getJSONObject("location").get("lng"));
            Landmark curLandmark = new Landmark((String)currentObj.get("name"), (String)currentObj.get("address"), ((Number)currentObj.get("rating")).floatValue(), currentLoc, 0.0);
            returnList.add(curLandmark);
        }
        Collections.sort(returnList, new Sortbyrating());
        for (int i = 0;i < returnList.size();i++) {
            Log.d("test",Float.toString(returnList.get(i).getRating()));
        }
>>>>>>> iteration1
        return returnList;
    }

}
<<<<<<< HEAD
=======

>>>>>>> iteration1
