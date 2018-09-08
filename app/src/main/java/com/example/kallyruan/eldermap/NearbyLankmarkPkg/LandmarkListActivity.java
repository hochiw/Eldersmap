package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LandmarkListActivity extends Activity {
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<Integer> adapter_id;
    private int action_index;
    private SearchAlg searchAlg = new SearchAlg();
    // User click.
    private String targetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landmark_list);
        //display the list of landmarks. Need to receive a string(user selection).
        try {
            showLandmarkList(targetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Shows the list of nearby landmarks that are requested by the user.
     */
    public void showLandmarkList(String target) throws  JSONException, ExecutionException, InterruptedException {

        ListView listView = (ListView) findViewById(R.id.landmark_list);
        ArrayList<Landmark> list = new ArrayList<>();
        //Data Input
        Location userLoc = new GPSTracker(getApplicationContext()).getLocation();
        // Need to get the user selection.
        JSONObject userData = JSONFactory.userDataJSONMaker(userLoc, target);
        //ArrayList<Landmark> list = searchAlg.filterList(JSONFactory.parseJSON("http://eldersmapapi.herokuapp.com/api/search"));
        JSONObject result = new HTTPPostRequest("http://eldersmapapi.herokuapp.com/api/search").execute(userData).get();
        if(result.get("status").toString().equals("OK")){
            list = searchAlg.filterList(result);
        }
        Log.d("test",list.toString());
        LandmarkListAdapter adapter = new LandmarkListAdapter(this, list);
        listView.setAdapter(adapter);
    }
}

