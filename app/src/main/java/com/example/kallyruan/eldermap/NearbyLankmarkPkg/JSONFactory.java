
package com.example.kallyruan.eldermap.NearbyLankmarkPkg;


import com.example.kallyruan.eldermap.LocationPkg.Location;

import org.json.JSONException;
import org.json.JSONObject;


public final class JSONFactory {
    /**
     * Parse user location object and selected landmark type into a JSON Object
     * @param userLoc
     * @param landmarkType
     * @return
     * @throws JSONException
     */
    public static JSONObject userDataJSONMaker(Location userLoc, String landmarkType) throws JSONException {
        JSONObject userData = new JSONObject();
        userData.put("latitude", userLoc.getLatitude());
        userData.put("longitude", userLoc.getLongitude());
        userData.put("pType", landmarkType);
        return userData;
    }

}
