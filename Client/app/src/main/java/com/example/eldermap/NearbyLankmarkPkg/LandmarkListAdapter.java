package com.example.eldermap.NearbyLankmarkPkg;

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
    // Initialize the variables
    private Activity mActivity;
    private ArrayList<Landmark> places;

    LandmarkListAdapter(Activity activity, ArrayList<Landmark> group){
        // Assign the activity and the list of scheduled trips to the corresponding variables
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
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * Custom viewholder for the list
     */
    static class ViewHolder
    {
        TextView name;
        TextView mark;
        TextView distance;
        ImageView rank;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // Initialize the view holder
        ViewHolder holder;

        // Initialize the inflater
        LayoutInflater inflater = mActivity.getLayoutInflater();

        // Create a holder if the view is null
        if(view == null){

            // initialize the view holder
            holder = new ViewHolder();

            // inflat the view with the layout
            view = inflater.inflate(R.layout.landmark_list_row,null);

            // Assign the view elements to the holder variables
            holder.rank = view.findViewById(R.id.icon_rank);
            holder.name = view.findViewById(R.id.locationName);
            holder.mark = view.findViewById(R.id.reviewMark);
            holder.distance = view.findViewById(R.id.distance);


            // Assign the holder to the view as a tag to fix the position of the items
            view.setTag(holder);
        } else {
            // Load the holder from the tag
            holder = (ViewHolder) view.getTag();
        }

        // Get the trip from the list at the give position
        final Landmark place = places.get(position);

        // Fill the information to the holder
        fillImage(holder,position);
        holder.name.setText(place.getName());
        holder.mark.setText("Rating: " + Float.toString(place.getRating()));
        holder.distance.setText("Estimate time(min): " + Integer.toString(place.getEstTime()));

        // Return the view
        return view;
    }


    /**
     * Assign the type to the corresponding item
     * @param holder holder of the item
     * @param index position of the item
     */
    private void fillImage(ViewHolder holder, int index){
        switch (index){
            case (0):
                holder.rank.setImageResource(R.mipmap.ic_rank_2);
                break;
            case (1):
                holder.rank.setImageResource(R.mipmap.ic_rank_3);
                break;
            case (2):
                holder.rank.setImageResource(R.mipmap.ic_rank_4);
                break;
            case (3):
                holder.rank.setImageResource(R.mipmap.ic_rank_5);
                break;
            case (4):
                holder.rank.setImageResource(R.mipmap.ic_rank_6);
                break;
            case (5):
                holder.rank.setImageResource(R.mipmap.ic_rank_6);
               break;
        }


    }





}