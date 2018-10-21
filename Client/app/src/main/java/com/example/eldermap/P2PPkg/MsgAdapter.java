package com.example.eldermap.P2PPkg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eldermap.R;

import java.io.File;
import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<MsgItem> mMsgItemList;
    private CustomItemClickListener listener;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout leftLayout;
        private LinearLayout rightLayout;
        private TextView leftMsg;
        private TextView rightMsg;
        private ImageView leftImage;
        private ImageView rightImage;

        public ViewHolder(View view) {
            super(view);
            leftLayout = view.findViewById(R.id.left_layout);
            rightLayout = view.findViewById(R.id.right_layout);
            leftMsg = view.findViewById(R.id.left_msg);
            rightMsg = view.findViewById(R.id.right_msg);
            leftImage = view.findViewById(R.id.left_image);
            rightImage = view.findViewById(R.id.right_image);
        }
    }

    public MsgAdapter(Context mContext, List<MsgItem> msgItemList, CustomItemClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        mMsgItemList = msgItemList;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }
    /**
     *
     * Create viewholder to finalise RecycleView
     * @param parent
     * @param viewType
     * @return view
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflat the view with the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.p2p_msg_item, parent, false);

        // Create a view holder with the view
        final ViewHolder mViewHolder = new ViewHolder(view);

        // Set a listener to get the holder position
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    /**
     * initialise the ViewHolder by getting position and msgType
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // Get the message item
        MsgItem msgItem = mMsgItemList.get(position);

        // Handles the received messages
        if (msgItem.getType() ==  MsgItem.TYPE_RECEIVED) {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);

            // Handles the text messages
            if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_TEXT){
                holder.leftMsg.setText(msgItem.getContent());

            // Handles the pictures
            }else if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_GRAPH){
                // Get the path of the picture
                String path = msgItem.getContent();
                // Decode the file into the bitmap
                Bitmap bmImg = BitmapFactory.decodeFile(path);

                // Set the picture to the holder
                holder.leftImage.setImageBitmap(bmImg);
                holder.leftImage.setVisibility(View.VISIBLE);
            // Handles the video
            }else if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_VIDEO) {
                // Show an arrow
                holder.leftImage.setVisibility(View.VISIBLE);
                holder.leftImage.setImageResource(R.mipmap.ic_arrow_right);
            }

        // Handles the sent messages
        } else if (msgItem.getType() == MsgItem.TYPE_SENT) {
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            // Handles the text
            if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_TEXT) {
                holder.rightMsg.setText(msgItem.getContent());
            // Handles the picture
            } else if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_GRAPH) {
                String path = msgItem.getContent();
                // Open the file and check if it exists
                File imgFile = new File(path);
                if(imgFile.exists()){
                    // Decode the picture and show it on the chat
                    Bitmap bmImg = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    holder.rightImage.setImageBitmap(bmImg);
                    holder.rightImage.setVisibility(View.VISIBLE);
                }
            // Handles the video
            }else if (msgItem.getContentType() == MsgItem.MESSAGE_TYPE_VIDEO) {
                // Show the arrow
                holder.rightImage.setVisibility(View.VISIBLE);
                holder.rightImage.setImageResource(R.mipmap.ic_arrow_right);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMsgItemList.size();
    }

    /**
     * add a message item to the list
     * @param message message
     */
    public void add(MsgItem message) {
        mMsgItemList.add(message);
    }
}
