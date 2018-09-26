package com.example.kallyruan.eldermap.P2PPkg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.kallyruan.eldermap.R;

import java.io.File;
import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<MsgItem> mMsgItemList;
    ImageView image;

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftLayout;

        LinearLayout rightLayout;

        TextView leftMsg;
        TextView rightMsg;
        ImageView leftImage;
        ImageView rightImage;
        VideoView leftVideo;
        VideoView rightVideo;

        public ViewHolder(View view) {
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
            leftImage = (ImageView) view.findViewById(R.id.left_image);
            rightImage = (ImageView) view.findViewById(R.id.right_image);
            leftVideo = (VideoView) view.findViewById(R.id.left_video);
            rightVideo = (VideoView) view.findViewById(R.id.right_video);
        }
    }

    public MsgAdapter(List<MsgItem> msgItemList) {
        mMsgItemList = msgItemList;
    }

    /**
     *
     * Create viewholder to finalise RecycleView
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p2p_msg_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * initialise the ViewHolder by getting position and msgType
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MsgItem msgItem = mMsgItemList.get(position);
        // if this is a received message, then hide the right layout and show the left one
        if (msgItem.getType() ==  MsgItem.TYPE_RECEIVED) {
            Log.d("p2p ","left ");
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_TEXT){
                Log.d("p2p ","left text ");
                holder.leftMsg.setText(msgItem.getContent());
            }else if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_GRAPH){
                Log.d("p2p ","left pic ");
                String path = msgItem.getContent();
                Bitmap bmImg = BitmapFactory.decodeFile(path);
                holder.leftImage.setImageBitmap(bmImg);
                holder.leftImage.setVisibility(View.VISIBLE);
                holder.leftVideo.setVisibility(View.GONE);
            }else if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_VIDEO) {
                Log.d("p2p ", "left video ");
                String path = msgItem.getContent();
                holder.leftVideo.setVideoPath(path);
                holder.leftVideo.start();
                holder.leftImage.setVisibility(View.GONE);
                holder.leftVideo.setVisibility(View.VISIBLE);
            }

            // if this is a sent message, then hide the left layout and show the right one
        } else if (msgItem.getType() == MsgItem.TYPE_SENT) {
            Log.d("p2p ", "right ");
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_TEXT) {
                Log.d("p2p ", "right text");
                holder.rightMsg.setText(msgItem.getContent());
            } else if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_GRAPH) {
                Log.d("p2p ", "right pic");
                String path = msgItem.getContent();
                File imgFile = new  File(path);
                if(imgFile.exists()){
                    //this part is to test with pic
                    Bitmap bmImg = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    holder.rightImage.setImageBitmap(bmImg);
                    holder.rightImage.setVisibility(View.VISIBLE);
                    holder.rightVideo.setVisibility(View.GONE);
                }
            }else if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_VIDEO) {
                Log.d("p2p ", "right video ");
                String path = msgItem.getContent();
                holder.rightVideo.setVideoPath(path);
                holder.rightVideo.start();
                holder.rightImage.setVisibility(View.GONE);
                holder.rightVideo.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMsgItemList.size();
    }

    public void add(MsgItem item) {
        mMsgItemList.add(item);
    }
}