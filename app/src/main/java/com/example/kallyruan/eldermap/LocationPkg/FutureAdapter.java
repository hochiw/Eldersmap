package com.example.kallyruan.eldermap.LocationPkg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kallyruan.eldermap.NearbyLankmarkPkg.Landmark;
import com.example.kallyruan.eldermap.R;

import java.util.ArrayList;

public class FutureAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<ScheduledTrip> futureTrips;
    private TextView name;
    private TextView date;

    public FutureAdapter(Activity activity, ArrayList<ScheduledTrip> places){
        this.mActivity = activity;
        this.futureTrips = places;
    }

    @Override
    public int getCount() {
        return futureTrips.size();
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
            createdView = inflater.inflate(R.layout.future_list_row,null);
            name = (TextView) createdView.findViewById(R.id.destinationName);
            date = (TextView) createdView.findViewById(R.id.date);
        } else {
            createdView = view;
        }

        final ScheduledTrip trip = futureTrips.get(position);
        name.setText(trip.getName());
        date.setText(trip.getDate());

        return createdView;
    }

}
