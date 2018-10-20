package com.example.eldermap.LocationPkg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eldermap.NearbyLankmarkPkg.MenuActivity;
import com.example.kallyruan.eldermap.R;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {

    // Initialize the variables
    private Activity mActivity;
    private ArrayList<FinishedTrip> history;


    HistoryAdapter(Activity activity, ArrayList<FinishedTrip> places){
        // Assign the activity and the list of finished trips to the corresponding variables
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


    /**
     * Custom viewholder for the list
     */
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
            holder.name = view.findViewById(R.id.destinationName);
            holder.date = view.findViewById(R.id.date);
            holder.locationMark = view.findViewById(R.id.reviewMark);
            holder.navigationMark = view.findViewById(R.id.navigationMark);

            holder.tripType = view.findViewById(R.id.trip_type);
            holder.tripRank = view.findViewById(R.id.trip_rank);
            holder.tripName = view.findViewById(R.id.trip_name);
            holder.tripDate = view.findViewById(R.id.trip_date);
            holder.tripReview = view.findViewById(R.id.trip_review);
            holder.tripNavigation = view.findViewById(R.id.trip_navigation);

            // Assign the holder to the view as a tag to fix the position of the items
            view.setTag(holder);
        } else {
            // Load the holder from the tag
            holder = (ViewHolder) view.getTag();
        }

        // Get the trip from the list at the give position
        final FinishedTrip trip = history.get(position);

        // Fill the information to the holder
        fillNumberImage(holder,position);
        fillTypeImage(holder,trip.getDestination().getType());
        holder.name.setText(Float.toString(trip.getDestinationMark()));
        holder.date.setText(trip.getDate());
        holder.locationMark.setText(Float.toString(trip.getDestinationMark()));
        holder.navigationMark.setText(Float.toString(trip.getTripMark()));
        holder.tripName.setText(trip.getName());
        holder.tripDate.setText(trip.getDate());
        holder.tripReview.setText("Destination Rating: " + Float.toString(trip.getDestinationMark()));
        holder.tripNavigation.setText("Navigation Rating: " + Float.toString(trip.getTripMark()));

        // Return the view
        return view;
    }

    /**
     * Assign the type to the corresponding item
     * @param holder holder of the item
     * @param index position of the item
     */

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

    /**
     * Assign the rank to the corresponding item
     * @param holder holder of the item
     * @param type type of the item
     */
    public void fillTypeImage(ViewHolder holder,int type) {
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
