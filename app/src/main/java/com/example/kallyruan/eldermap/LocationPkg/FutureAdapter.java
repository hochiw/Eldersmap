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

    // Initialize the variables
    private Activity mActivity;
    private ArrayList<ScheduledTrip> futureTrips;

    FutureAdapter(Activity activity, ArrayList<ScheduledTrip> places){
        // Assign the activity and the list of scheduled trips to the corresponding variables
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

    /**
     * Custom viewholder for the list
     */
    static class ViewHolder
    {
        TextView name;
        TextView date;
        ImageView tripType;
        ImageView tripRank;
        TextView tripName;
        TextView tripDate;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // Initialize the viewholder
        ViewHolder holder;

        // Initialize the inflater
        LayoutInflater inflater = mActivity.getLayoutInflater();

        // Create a new view if it is null, else load from the tag
        if(view == null){
            // Initialize the view holder
            holder = new ViewHolder();

            // inflate the view with the layout
            view = inflater.inflate(R.layout.history_list_row,null);

            // Assign the elements to the view holder
            holder.name =  view.findViewById(R.id.destinationName);
            holder.date =  view.findViewById(R.id.date);
            holder.tripType = view.findViewById(R.id.trip_type);
            holder.tripRank = view.findViewById(R.id.trip_rank);
            holder.tripName = view.findViewById(R.id.trip_name);
            holder.tripDate = view.findViewById(R.id.trip_date);

            // Assign the holder to the view as a tag to fix the position of the items
            view.setTag(holder);
        } else {
            // Load the holder from the tag
            holder = (ViewHolder) view.getTag();
        }

        // Get the trip from the list at the give position
        final ScheduledTrip trip = futureTrips.get(position);

        // Fill the information to the holder
        fillImage(holder,position);
        fillTypeImage(holder,trip.getDestination().getType());
        holder.tripName.setText(trip.getName());
        holder.tripDate.setText(trip.getDate());
        holder.tripName.setText(trip.getName());
        holder.tripDate.setText(trip.getDate());
        holder.name.setText(trip.getName());
        holder.date.setText(trip.getDate());

        // Return the view
        return view;
    }

    /**
     * Assign the rank to the corresponding item
     * @param holder holder of the item
     * @param index position of the item
     */
    private void fillImage(ViewHolder holder,int index){
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

    /**
     * Assign the type to the corresponding item
     * @param holder holder of the item
     * @param type type of the item
     */
    private void fillTypeImage(ViewHolder holder,int type) {
        switch (type){
            case (MenuActivity.HOSPITAL):
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
