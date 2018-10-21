package com.example.eldermap.P2PPkg;

import android.view.View;

/**
 * CustomItemClickListene interface is a customised onItemClickListener for RecycleView since it
 * only have onClickListener
 */
public interface CustomItemClickListener {
    public void onItemClick(View v, int position);
}
