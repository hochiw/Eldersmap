
package com.example.eldermap.NearbyLankmarkPkg;


import com.example.eldermap.LocationPkg.Location;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSONFactory class is to parse user location object and selected landmark type into a JSON Object
 */
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
