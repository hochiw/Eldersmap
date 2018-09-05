package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.util.Log;

import com.example.kallyruan.eldermap.LocationPkg.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Kah Chun on 01/09/2018.
 */

public final class JSONFactory {

    public static JSONObject userDataJSONMaker(Location userLoc, String landmarkType) throws JSONException {
        JSONObject userData = new JSONObject();
        Log.d("test",Double.toString(userLoc.getLatitude()) );
        userData.put("latitude", userLoc.getLatitude());
        userData.put("longitude", userLoc.getLongitude());
        userData.put("pType", landmarkType);
        return userData;
    }

}
