package com.example.eldermap.NearbyLankmarkPkg;

import com.example.eldermap.LocationPkg.Location;
import com.example.eldermap.NavigationPkg.CoorDist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
/**
 * SearchAlg class defines the applied landmark searching algorithm and revert distance to estimated
 * walking time.
 */
public class SearchAlg {
    public final static int FIVEMINUTES = 5;
    public final static int TENMINUTES = 10;
    public final static int FIFTEENMINUTES = 15;
    public final static int TWENTYMINUTES = 20;
    public final static int THIRTYMINUTES = 30;

    /**
     * Takes a string returned from the API server nad returns the top 5 recommended landmarks
     * @param inList list of landmarks returned from the API server
     * @param userLoc user's currect location
     * @return
     * @throws JSONException
     * @throws NullPointerException
     */
    public ArrayList<Landmark> filterList(JSONObject inList, Location userLoc) throws JSONException, NullPointerException {
        ArrayList<Landmark> returnList = new ArrayList();
        JSONArray array = new JSONArray(inList.get("results").toString());

        //iterates through the list of landmarks and converts them into a location object
        //then place into arraylist
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

        //Sort the list
        Collections.sort(returnList, new Sortbyrating());

        //Only return the top 5 of the list
        int k = returnList.size();
        if (k > 5) {
            returnList.subList(5,k).clear();
        }

        return returnList;

    }

    /**
     * Converts distance to an estimated time of travel
     * @param walkDist Distance from user to landmark in meters
     * @return estimates walking time as int
     */
    public int estWalkTime(double walkDist) {
        if (0 < walkDist && walkDist <= 250) {
            return FIVEMINUTES;
        } else if (250 < walkDist && walkDist <= 500) {
            return TENMINUTES;
        } else if (500 < walkDist && walkDist <= 750) {
            return FIFTEENMINUTES;
        } else if (750 < walkDist && walkDist <= 1000) {
            return TWENTYMINUTES;
        } else {
            return THIRTYMINUTES;
        }
    }

}

