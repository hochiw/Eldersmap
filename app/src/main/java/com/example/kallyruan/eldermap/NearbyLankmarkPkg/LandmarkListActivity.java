package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.GPSTracker.binder;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class LandmarkListActivity extends Activity {
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<Integer> adapter_id;
    private int action_index;
    private SearchAlg searchAlg = new SearchAlg();
    private GPSTracker gps;
    private boolean serviceAlive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landmark_list);
        //display the list of landmarks
        Intent intent = new Intent(this, GPSTracker.class);
        startService(intent);
        bindService(intent,mServiceConn, Context.BIND_AUTO_CREATE);
    }

    

    /**
     * Shows the list of nearby landmarks
     */
    public void showLandmarkList() throws IOException, JSONException {
        ListView listView = (ListView) findViewById(R.id.landmark_list);
        String url = "http://eldersmapapi.herokuapp.com/api/search";
        JSONObject userData = new JSONObject();
        Location userLoc = gps.getLoc();
        userData.put("latitude", userLoc.getLatitude());
        userData.put("longitude", userLoc.getLongitude());
        userData.put("pType", "");
        Log.d("test", userData.toString());
        String result = null;
        try {
            result = new HTTPPostRequest(url).execute(userData).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Data Input
        JSONObject obj = new JSONObject(result);
        //    if (obj.get("status") == "OK") {
        ArrayList<Landmark> list = searchAlg.filterList(new JSONArray(obj.get("results").toString()));
        //     }

        LandmarkListAdapter adapter = new LandmarkListAdapter(this, list);

        listView.setAdapter(adapter);
    }


}

