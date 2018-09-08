package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

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
    //Variable Declaration
    private ArrayList<Landmark> returnList;


    public SearchAlg(){
        returnList = new ArrayList<>();
    }

//    //Takes a string returned from the API server
//    public ArrayList<Landmark> filterList(JSONArray inList) throws JSONException {
//        for (int i = 0; i < inList.length(); i++){
//            JSONObject currentObj = inList.getJSONObject(i);
//            Location currentLoc = new Location((Double) currentObj.getJSONObject("location").get("lat"), (Double) currentObj.getJSONObject("location").get("lng"));
//            Landmark curLandmark = new Landmark((String)currentObj.get("name"), (String)currentObj.get("address"), (float)currentObj.get("rating"), currentLoc, 0.0);
//            returnList.add(curLandmark);
//        }
//        Collections.sort(returnList, new Sortbyrating());
//        return returnList;
//    }
        //Takes a string returned from the API server

}
