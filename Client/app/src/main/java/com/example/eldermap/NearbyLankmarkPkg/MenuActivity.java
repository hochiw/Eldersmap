package com.example.eldermap.NearbyLankmarkPkg;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.eldermap.ProfilePkg.BaseActivity;
import com.example.eldermap.R;
/**
 * MenuActivity class handles activities with menu features including UI display, intersection and
 * redirecting to other activities.
 */
public class MenuActivity extends BaseActivity {

    //final static int variables for use in switch
    public final static int HOSPITAL = 0;
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
        // create a typeface from the raw ttf
        Typeface typeface = Typeface.createFromAsset(getAssets(),"FormalTitle.ttf");
        title.setTypeface(typeface);

        showCategoryIcon();
    }

    /**
     * this function is to show all landmark category icons in a grid view
     */
    private void showCategoryIcon() {
        GridView gridview = findViewById(R.id.landmark_menu);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String category;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int location, long l) {
                listlandmark(location);
                switch (location){
                    case HOSPITAL:
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
        startActivity(intent);


    }
}
