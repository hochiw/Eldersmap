package com.example.kallyruan.eldermap.NearbyLankmarkPkg;
import com.example.kallyruan.eldermap.LocationPkg.Location;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kallyruan.eldermap.R;

import java.util.ArrayList;

public class LandmarkListActivity extends Activity {
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<Integer> adapter_id;
    private int action_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landmark_list);
        //display the list of landmarks
        showLandmarkList();
    }


    /**
     * Shows the list of nearby landmarks
     */
    public void showLandmarkList() {
        ListView listView = (ListView) findViewById(R.id.landmark_list);
        Location user = Location.getInstance(1.0,2.0);
        Landmark example = new Landmark("test", 5, 5, user);
        ArrayList<Landmark> list = new ArrayList<Landmark>();

        // for test display
        for (int i = 0; i<20 ; i++){
            list.add(example);
        }
        System.out.print(list);
        LandmarkListAdapter adapter = new LandmarkListAdapter(this, list);
        listView.setAdapter(adapter);
    }
}

