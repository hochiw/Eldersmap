package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kallyruan.eldermap.R;

import java.util.ArrayList;
import java.util.List;

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
        Landmark example = new Landmark("test", 5, 5, new Location(1.0,2.0));
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

