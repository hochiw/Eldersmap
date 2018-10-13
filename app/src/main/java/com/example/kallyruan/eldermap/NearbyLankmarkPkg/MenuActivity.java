package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.HashMap;

import com.example.kallyruan.eldermap.NavigationPkg.AlarmReceiver;
import com.example.kallyruan.eldermap.NavigationPkg.NotificationScheduler;
import com.example.kallyruan.eldermap.NavigationPkg.ScheduleTimeActivity;
import com.example.kallyruan.eldermap.ProfilePkg.BaseActivity;
import com.example.kallyruan.eldermap.R;





public class MenuActivity extends BaseActivity {
    private HashMap mapIndex;

    public final static int HOSPITSL = 0;
    public final static int PHARMACY = 1;
    public final static int LIBRARY = 2;
    public final static int SUPERMARKET = 3;
    public final static int BAR = 4;
    public final static int RESTAURANT = 5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landmark_menu);

        //set title size and style
        TextView title = findViewById(R.id.landmark_menu_title);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"FormalTitle.ttf"); // create a typeface from the raw ttf
        title.setTypeface(typeface);

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
                    case HOSPITSL:
                        category = "hospital";
                        break;
                    case PHARMACY:
                        category = "pharmacy";
                        break;
                    case LIBRARY:
                        category = "library";
                        break;
                    case SUPERMARKET:
                        category = "supermarket";
                        break;
                    case BAR:
                        category = "bar";
                        break;
                    case RESTAURANT:
                        category = "restaurant";
                        break;

                }
                LandmarkListActivity.category = category;
            }
        });
    }

    /**
     * Re-route user to responding list of landmark based on user's selection
     * @param landmark
     */
    private void listlandmark(int landmark){
        Intent intent = new Intent(getApplicationContext(), LandmarkListActivity.class);

        //this is now for testing notification feature, should use the above intent
        //Intent intent = new Intent(getApplicationContext(), ScheduleTimeActivity.class);
        startActivity(intent);


    }

    private void initialiseIndexMap(){
        /*R.mipmap.ic_hospital, R.mipmap.ic_pharmacy,
            R.mipmap.ic_market,R.mipmap.ic_supermarket,
            R.mipmap.ic_bar, R.mipmap.ic_restaurant,*/
        mapIndex = new HashMap<Integer, String>();
        mapIndex.put(0, "hospital");
        mapIndex.put(1, "pharmacy");
        mapIndex.put(2, "market");
        mapIndex.put(3, "supermarket");
        mapIndex.put(4, "bar");
        mapIndex.put(5, "restaurant");

    }


}
