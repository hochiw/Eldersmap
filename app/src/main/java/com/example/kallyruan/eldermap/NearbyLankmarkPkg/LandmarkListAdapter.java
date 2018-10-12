package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
            name = (TextView) createdView.findViewById(R.id.locationName);
            mark = (TextView) createdView.findViewById(R.id.reviewMark);
            cost = (TextView) createdView.findViewById(R.id.cost);
            distance = (TextView) createdView.findViewById(R.id.distance);
        } else {
            createdView = view;
        }

        final Landmark place = places.get(position);
        System.out.print(place.getName());
        name.setText(place.getName());
        mark.setText(Float.toString(place.getRating()));
        cost.setText("0.0");
        distance.setText(Integer.toString(place.getEstTime()));

        return createdView;
    }

}