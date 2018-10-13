package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kallyruan.eldermap.R;

import java.util.ArrayList;

public class LandmarkListAdapter extends BaseAdapter {
    private Activity mActivity;
    private ArrayList<Landmark> places;





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
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        LayoutInflater inflater = mActivity.getLayoutInflater();

        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.landmark_list_row,null);

            holder.rank = (ImageView) view.findViewById(R.id.icon_rank);
            holder.name = (TextView) view.findViewById(R.id.locationName);
            holder.mark = (TextView) view.findViewById(R.id.reviewMark);
            holder.distance = (TextView) view.findViewById(R.id.distance);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Landmark place = places.get(position);
        fillImage(position,holder.rank);
        holder.name.setText(place.getName());
        holder.mark.setText("Rating: "+Float.toString(place.getRating()));
        holder.distance.setText("Estimate time(min): "+Integer.toString(place.getEstTime()));

        return view;
    }


    public void fillImage(int index, ImageView rank){
        switch (index){
            case (0):
                rank.setImageResource(R.mipmap.ic_rank_2);
                break;
            case (1):
                rank.setImageResource(R.mipmap.ic_rank_3);
                break;
            case (2):
                rank.setImageResource(R.mipmap.ic_rank_4);
                break;
            case (3):
                rank.setImageResource(R.mipmap.ic_rank_5);
                break;
            case (4):
                rank.setImageResource(R.mipmap.ic_rank_6);
                break;
            case (5):
                rank.setImageResource(R.mipmap.ic_rank_6);
               break;
        }


    }

    static class ViewHolder
    {
        TextView name;
        TextView mark;
        TextView distance;
        ImageView rank;
    }



}