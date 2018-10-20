package com.example.eldermap.NearbyLankmarkPkg;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.kallyruan.eldermap.R;

public class ImageAdapter extends BaseAdapter {

    // Variable for the context
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public long getItemId(int position) {
        return 0;
    }

    public Object getItem(int position) {
        return null;
    }

    /**
     *
     * @param position position of the item
     * @param convertView the view
     * @param parent viewGroup
     * @return the view
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        // Create a new view if it is null
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(10, 10, 10, 10);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.mipmap.ic_hospital, R.mipmap.ic_pharmacy,
            R.mipmap.ic_library,R.mipmap.ic_supermarket,
            R.mipmap.ic_bar, R.mipmap.ic_restaurant,

        };
    }
