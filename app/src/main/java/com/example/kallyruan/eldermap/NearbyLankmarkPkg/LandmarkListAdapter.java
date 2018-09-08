package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.R;

import java.util.ArrayList;

public class LandmarkListAdapter extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<Landmark> places;
    private TextView name;
    private TextView address;
    private TextView rating;
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
            address = (TextView) createdView.findViewById(R.id.address);
            rating = (TextView) createdView.findViewById(R.id.rating);
            distance = (TextView) createdView.findViewById(R.id.distance);
        } else {
            createdView = view;
        }

        final Landmark place = places.get(position);
        name.setText(place.getName());
        address.setText(place.getAddress());
        rating.setText(Float.toString(place.getRating()));
        distance.setText(Double.toString(place.getDistance()));

        return createdView;
    }

}