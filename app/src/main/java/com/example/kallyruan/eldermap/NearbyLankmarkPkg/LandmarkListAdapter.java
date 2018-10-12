package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kallyruan.eldermap.R;

import java.util.ArrayList;

public class LandmarkListAdapter extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<Landmark> places;
    private TextView name;
    private TextView mark;
    private TextView cost;
    private TextView distance;
    private ImageView rank;

    public LandmarkListAdapter(Activity activity, ArrayList<Landmark> group){
        this.mActivity = activity;
        this.places = group;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, final View view, ViewGroup viewGroup) {
        LayoutInflater inflater = mActivity.getLayoutInflater();
        final View createdView;

        if(view == null){
            createdView = inflater.inflate(R.layout.landmark_list_row,null);
            rank = (ImageView) createdView.findViewById(R.id.icon_rank);
            name = (TextView) createdView.findViewById(R.id.locationName);
            mark = (TextView) createdView.findViewById(R.id.reviewMark);
            cost = (TextView) createdView.findViewById(R.id.cost);
            distance = (TextView) createdView.findViewById(R.id.distance);
        } else {
            createdView = view;
        }

        final Landmark place = places.get(position);
        fillImage(position);
        name.setText(place.getName());
        mark.setText("Rating: "+Float.toString(place.getRating()));
        cost.setText("Average Cost: "+"0.0");
        distance.setText("Distance: "+Integer.toString(calcuateDistance(place)));

        return createdView;
    }

    /**
     * Calculate the distance based on User GPS and landmark locations
     * @param destination
     * @return
     */
    public int calcuateDistance(Landmark destination){


        return 0;
    }

    public void fillImage(int index){
        switch (index){
            case (0):
                rank.setImageResource(R.mipmap.ic_rank_1);
                break;
            case (1):
                rank.setImageResource(R.mipmap.ic_rank_2);
                break;
            case (2):
                rank.setImageResource(R.mipmap.ic_rank_3);
                break;
            case (3):
                rank.setImageResource(R.mipmap.ic_rank_4);
                break;
            case (4):
                rank.setImageResource(R.mipmap.ic_rank_5);
                break;
            case (5):
                rank.setImageResource(R.mipmap.ic_rank_6);
               break;
        }


    }
}