package com.example.kallyruan.eldermap.P2PPkg;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kallyruan.eldermap.R;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<MsgItem> mMsgItemList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftLayout;

        LinearLayout rightLayout;

        TextView leftMsg;
        TextView rightMsg;
        ImageView leftImage;
        ImageView rightImage;

        public ViewHolder(View view) {
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
            leftImage = (ImageView) view.findViewById(R.id.left_image);
            rightImage = (ImageView) view.findViewById(R.id.right_image);
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
        if (msgItem.getType() == MsgItem.TYPE_RECEIVED) {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_TEXT){
                holder.leftMsg.setText(msgItem.getContent());
            }else if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_GRAPH){
                String path = msgItem.getContent();
                holder.leftImage.setImageBitmap(BitmapFactory.decodeFile(path));
            }

       // if this is a sent message, then hide the left layout and show the right one
        } else if (msgItem.getType() == MsgItem.TYPE_SENT) {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_TEXT){
                holder.rightMsg.setText(msgItem.getContent());
            }else if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_GRAPH){
                String path = msgItem.getContent();
                holder.rightImage.setImageBitmap(BitmapFactory.decodeFile(path));
            }

        }
    }

    @Override
    public int getItemCount() {
        return mMsgItemList.size();
    }
}
