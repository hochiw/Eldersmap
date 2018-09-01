package com.example.kallyruan.eldermap.NearbyLankmarkPkg;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kallyruan.eldermap.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class LandmarkListActivity extends Activity {
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<Integer> adapter_id;
    private int action_index;
    private SearchAlg searchAlg = new SearchAlg();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landmark_list);
        //display the list of landmarks
        try {
            showLandmarkList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Shows the list of nearby landmarks
     */
    public void showLandmarkList() throws IOException, JSONException {
        ListView listView = (ListView) findViewById(R.id.landmark_list);
        //Data Input
        ArrayList<Landmark> list = searchAlg.filterList(APICommunicator.parseJSON("http://eldersmapapi.herokuapp.com/api/search?location=-33.8670522%2C151.1957362&radius=1500&pType=library")) ;


        LandmarkListAdapter adapter = new LandmarkListAdapter(this, list);
        listView.setAdapter(adapter);
    }
}

