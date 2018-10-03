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

public class HistoryAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<FinishedTrip> history;
    private TextView name;
    private TextView date;
    private TextView locationMark;
    private TextView navigationMark;

    public HistoryAdapter(Activity activity, ArrayList<FinishedTrip> places){
        this.mActivity = activity;
        this.history = places;
    }

    @Override
    public int getCount() {
        return history.size();
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
            createdView = inflater.inflate(R.layout.history_list_row,null);
            name = (TextView) createdView.findViewById(R.id.destinationName);
            date = (TextView) createdView.findViewById(R.id.date);
            locationMark = (TextView) createdView.findViewById(R.id.reviewMark);
            navigationMark = (TextView) createdView.findViewById(R.id.navigationMark);
        } else {
            createdView = view;
        }

        final FinishedTrip trip = history.get(position);
        name.setText(Float.toString(trip.getdestinationMark()));
        date.setText(trip.getDate());
        locationMark.setText(Float.toString(trip.getdestinationMark()));
        navigationMark.setText(Float.toString(trip.getTripMark()));

        return createdView;
    }

}
