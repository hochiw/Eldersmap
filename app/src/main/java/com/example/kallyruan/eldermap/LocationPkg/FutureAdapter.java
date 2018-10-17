package com.example.kallyruan.eldermap.LocationPkg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kallyruan.eldermap.NearbyLankmarkPkg.MenuActivity;
import com.example.kallyruan.eldermap.R;

import java.util.ArrayList;

public class FutureAdapter extends BaseAdapter {

    private Activity mActivity;
    private ArrayList<ScheduledTrip> futureTrips;


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
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        LayoutInflater inflater = mActivity.getLayoutInflater();

        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.history_list_row,null);
            holder.name = (TextView) view.findViewById(R.id.destinationName);
            holder.date = (TextView) view.findViewById(R.id.date);
            holder.tripType = view.findViewById(R.id.trip_type);
            holder.tripRank = view.findViewById(R.id.trip_rank);
            holder.tripName = (TextView) view.findViewById(R.id.trip_name);
            holder.tripDate = (TextView) view.findViewById(R.id.trip_date);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final ScheduledTrip trip = futureTrips.get(position);
        fillImage(holder,position);
        fillTypeImage(holder,trip.getDestination().getType());
        holder.tripName.setText(trip.getName());
        holder.tripDate.setText(trip.getDate());
        holder.tripName.setText(trip.getName());
        holder.tripDate.setText(trip.getDate());

        holder.name.setText(trip.getName());
        holder.date.setText(trip.getDate());

        return view;
    }

    static class ViewHolder
    {
        TextView name;
        TextView date;
        ImageView tripType;
        ImageView tripRank;
        TextView tripName;
        TextView tripDate;
    }

    public void fillImage(ViewHolder holder,int index){
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
        }
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

}
