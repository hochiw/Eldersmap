package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.kallyruan.eldermap.R;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landmark_menu);
        showCategoryIcon();
    }

    /**
     * this function is to show all landmark category icons in a grid view
     */
    private void showCategoryIcon() {
        GridView gridview = (GridView) findViewById(R.id.landmark_menu);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String category;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int location, long l) {
                listlandmark(location);
                switch (location){
                    case 0:
                        category = "hospital";
                        break;
                }
                LandmarkListActivity.destination = category;
            }
        });
    }

    /**
     * Re-route user to responding list of landmark based on user's selection
     * @param landmark
     */
    private void listlandmark(int landmark){
        Intent intent = new Intent(this, LandmarkListActivity.class);
        startActivity(intent);
    }


}
