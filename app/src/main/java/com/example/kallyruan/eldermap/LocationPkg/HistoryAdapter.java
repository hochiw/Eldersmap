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

public class HistoryAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<FinishedTrip> history;


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
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        LayoutInflater inflater = mActivity.getLayoutInflater();
        

        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.history_list_row,null);
            holder.name = (TextView) view.findViewById(R.id.destinationName);
            holder.date = (TextView) view.findViewById(R.id.date);
            holder.locationMark = (TextView) view.findViewById(R.id.reviewMark);
            holder.navigationMark = (TextView) view.findViewById(R.id.navigationMark);

            holder.tripType = view.findViewById(R.id.trip_type);
            holder.tripRank = view.findViewById(R.id.trip_rank);
            holder.tripName = (TextView) view.findViewById(R.id.trip_name);
            holder.tripDate = (TextView) view.findViewById(R.id.trip_date);
            holder.tripReview = (TextView) view.findViewById(R.id.trip_review);
            holder.tripNavigation = (TextView) view.findViewById(R.id.trip_navigation);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final FinishedTrip trip = history.get(position);
        holder.name.setText(Float.toString(trip.getdestinationMark()));
        holder.date.setText(trip.getDate());
        holder.locationMark.setText(Float.toString(trip.getdestinationMark()));
        holder.navigationMark.setText(Float.toString(trip.getTripMark()));

        fillNumberImage(holder,position);
        fillTypeImage(holder,trip.getDestination().getType());
        holder.tripName.setText(trip.getName());
        holder.tripDate.setText(trip.getDate());
        holder.tripReview.setText("Destination mark: "+Float.toString(trip.getdestinationMark()));
        holder.tripNavigation.setText("Navigation mark: "+Float.toString(trip.getTripMark()));

        return view;
    }

    static class ViewHolder
    {
        TextView name;
        TextView date;
        TextView locationMark;
        TextView navigationMark;

        ImageView tripType;
        ImageView tripRank;
        TextView tripName;
        TextView tripDate;
        TextView tripReview;
        TextView tripNavigation;
    }

    public void fillTypeImage(ViewHolder holder,int type) {
        holder.tripType.setImageResource(R.mipmap.ic_launcher_app);
        switch (type){
            case (MenuActivity.HOSPITSL):
                holder.tripType.setImageResource(R.mipmap.ic_hospital);
                break;
            case (MenuActivity.PHARMACY):
                holder.tripType.setImageResource(R.mipmap.ic_pharmacy);
                break;
            case (MenuActivity.LIBRARY):
                holder.tripType.setImageResource(R.mipmap.ic_library);
                break;
            case (MenuActivity.SUPERMARKET):
                holder.tripType.setImageResource(R.mipmap.ic_supermarket);
                break;
            case (MenuActivity.BAR):
                holder.tripType.setImageResource(R.mipmap.ic_bar);
                break;
            case (MenuActivity.RESTAURANT):
                holder.tripType.setImageResource(R.mipmap.ic_restaurant);
                break;
            default: holder.tripType.setImageResource(R.mipmap.ic_launcher_app);
                break;
        }

    }

    public void fillNumberImage(ViewHolder holder,int index){
        switch (index){
            case (0):
                holder.tripRank.setImageResource(R.mipmap.ic_rank_1);
                break;
            case (1):
                holder.tripRank.setImageResource(R.mipmap.ic_rank_2);
                break;
            case (2):
                holder.tripRank.setImageResource(R.mipmap.ic_rank_3);
                break;
            case (3):
                holder.tripRank.setImageResource(R.mipmap.ic_rank_4);
                break;
            case (4):
                holder.tripRank.setImageResource(R.mipmap.ic_rank_5);
                break;
            case (5):
                holder.tripRank.setImageResource(R.mipmap.ic_rank_6);
                break;
            default: holder.tripRank.setImageResource(R.mipmap.ic_rank_6);
                break;
        }


    }

}
