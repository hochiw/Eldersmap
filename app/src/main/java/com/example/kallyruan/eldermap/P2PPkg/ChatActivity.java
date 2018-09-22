package com.example.kallyruan.eldermap.P2PPkg;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;
import com.example.kallyruan.eldermap.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private UDPReceiver receiver;
    private SocketClient client;
    private ArrayList<MsgItem> msgItems;
    private List<MsgItem> msgList = new ArrayList<MsgItem>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this following part are for UI view
        setContentView(R.layout.p2p_chatroom);
       // initMsgs();
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    MsgItem msgItem = new MsgItem(content, MsgItem.TYPE_SENT,MsgItem.MESSAGE_TYPE_TEXT);
                    msgList.add(msgItem);
                    // refresh the view
                    adapter.notifyItemInserted(msgList.size() - 1);
                    // nominate the view to the last message
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    // clear the input text content
                    inputText.setText("");
                    // send the message
                    if (client != null) {
                        client.getInstance().send(content);
                    }
                }
            }
        });
        try {
            client = new SocketClient(new URI("ws://10.13.238.213:8080?type=client"),this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newMessage(String message) {
        MsgItem msgItem = new MsgItem(message, MsgItem.TYPE_RECEIVED,MsgItem.MESSAGE_TYPE_TEXT);
        msgList.add(msgItem);
        for (MsgItem i:msgList) {
            Log.d("WS",i.getContent());
        }

        adapter.notifyItemInserted(msgList.size() - 1);

    }

    public void getPicture (View view) {
            Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
            gallery.setType("image/*");
            startActivityForResult(gallery, 1);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            if (resultCode == RESULT_OK) {
                //this part is to get photo data
                Uri selectedImage = intent.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                // assign to image view
                //ImageView imageView = (ImageView) findViewById(R.id.imgView);
                //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                //add to the list
                MsgItem msgItem = new MsgItem(picturePath, MsgItem.TYPE_SENT, MsgItem.MESSAGE_TYPE_GRAPH);
                msgList.add(msgItem);


                //send photo to server
                if (client.getStatus()) {
                    client.sendFile(intent.getData().getPath());
                }

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    };

        private void initMsgs() {
            MsgItem msgItem1 = new MsgItem("Hello guy.", MsgItem.TYPE_RECEIVED,MsgItem.MESSAGE_TYPE_TEXT);
            msgList.add(msgItem1);
            MsgItem msgItem2 = new MsgItem("Hello. Who is that?", MsgItem.TYPE_SENT,MsgItem.MESSAGE_TYPE_TEXT);
            msgList.add(msgItem2);
            MsgItem msgItem3 = new MsgItem("This is Tom. Nice talking to you. ", MsgItem.TYPE_RECEIVED,MsgItem.MESSAGE_TYPE_TEXT);
            msgList.add(msgItem3);
        }

}
