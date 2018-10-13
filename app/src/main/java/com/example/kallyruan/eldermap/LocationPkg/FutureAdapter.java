package com.example.kallyruan.eldermap.LocationPkg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kallyruan.eldermap.NearbyLankmarkPkg.Landmark;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.MenuActivity;
import com.example.kallyruan.eldermap.R;

import java.util.ArrayList;

public class FutureAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<ScheduledTrip> futureTrips;
    private TextView name;
    private TextView date;

    private ImageView tripType;
    private ImageView tripRank;
    private TextView tripName;
    private TextView tripDate;
    private TextView tripTime;
    private TextView tripReview;
    private TextView tripNavigation;

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
            createdView = inflater.inflate(R.layout.history_list_row,null);
            name = (TextView) createdView.findViewById(R.id.destinationName);
            date = (TextView) createdView.findViewById(R.id.date);
            tripType = createdView.findViewById(R.id.trip_type);
            tripRank = createdView.findViewById(R.id.trip_rank);
            tripName = (TextView) createdView.findViewById(R.id.trip_name);
            tripDate = (TextView) createdView.findViewById(R.id.trip_date);
        } else {
            createdView = view;
        }

        final ScheduledTrip trip = futureTrips.get(position);
        fillImage(position);
        fillTypeImage(trip.getDestination().getType());
        tripName.setText(trip.getName());
        tripDate.setText(trip.getDate());
        tripName.setText(trip.getName());
        tripDate.setText(trip.getDate());

        name.setText(trip.getName());
        date.setText(trip.getDate());

        return createdView;
    }

    public void fillImage(int index){
        switch (index){
            case (0):
                tripRank.setImageResource(R.mipmap.ic_rank_1);
                break;
            case (1):
                tripRank.setImageResource(R.mipmap.ic_rank_2);
                break;
            case (2):
                tripRank.setImageResource(R.mipmap.ic_rank_3);
                break;
            case (3):
                tripRank.setImageResource(R.mipmap.ic_rank_4);
                break;
            case (4):
                tripRank.setImageResource(R.mipmap.ic_rank_5);
                break;
            case (5):
                tripRank.setImageResource(R.mipmap.ic_rank_6);
                break;
        }
    }

    public void fillTypeImage(int type) {
        tripType.setImageResource(R.mipmap.ic_launcher_app);
        switch (type){
            case (MenuActivity.HOSPITSL):
                tripType.setImageResource(R.mipmap.ic_hospital);
                break;
            case (MenuActivity.PHARMACY):
                tripType.setImageResource(R.mipmap.ic_pharmacy);
                break;
            case (MenuActivity.LIBRARY):
                tripType.setImageResource(R.mipmap.ic_library);
                break;
            case (MenuActivity.SUPERMARKET):
                tripType.setImageResource(R.mipmap.ic_supermarket);
                break;
            case (MenuActivity.BAR):
                tripType.setImageResource(R.mipmap.ic_bar);
                break;
            case (MenuActivity.RESTAURANT):
                tripType.setImageResource(R.mipmap.ic_restaurant);
                break;
            default: tripType.setImageResource(R.mipmap.ic_launcher_app);
                break;
        }
    }

}
