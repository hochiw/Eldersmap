package com.example.kallyruan.eldermap.P2PPkg;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;
import com.example.kallyruan.eldermap.R;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private SocketClient client;
    private ArrayList<MsgItem> msgItems;
    private List<MsgItem> msgList = new ArrayList<MsgItem>();
    public final int RESULT_LOAD_IMG = 1;
    public final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 999;

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    private Thread chatThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this following part are for UI view
        setContentView(R.layout.p2p_chatroom);
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

                    if (client != null) {
                        client.getInstance().send(content);
                    }
                }
            }
        });

        //the following is with server connection..
        try {
            client = new SocketClient(new URI("ws://eldermapswebsocket.herokuapp.com/?type=admin"),this);
            chatThread = new Thread(client);
            chatThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newMessage(MsgItem message){
        try {
            if ((message.getContentType() == MsgItem.MESSAGE_TYPE_GRAPH || message.getContentType() == MsgItem.MESSAGE_TYPE_VIDEO) &&
                    message.getFileName() != null) {
                byte[] byteArray = FileEncoder.base64ToByte(message.getContent());
                message.setContent(FileEncoder.writeToFile(byteArray,message.getFileName()));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        msgList.add(message);
        // refresh the view
        adapter.notifyItemInserted(msgList.size() - 1);
        // nominate the view to the last message
        msgRecyclerView.scrollToPosition(msgList.size() - 1);
    }

    public void getRichMedia(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/* video/*");
        startActivityForResult(intent, RESULT_LOAD_IMG);
    }


    public void getCall(View view){

    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            //get file path
            final Uri fileUri = intent.getData();
            String path = getRealPathFromURI(fileUri);
            Log.d("File",path);

            // check whether read Permission is granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                checkMediaFileType(path, intent);
            } else {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    //show toast to let user try send again
                    Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    public void checkMediaFileType(String path,Intent intent){
        File richMediaFile = new File(path);
        if (richMediaFile.exists()) {
            if(isImageFile(path)){
                MsgItem msgItem = new MsgItem(path, MsgItem.TYPE_SENT, MsgItem.MESSAGE_TYPE_GRAPH);
                addToList(msgItem,intent);
            }else if (isVideoFile(path)){
                MsgItem msgItem = new MsgItem(path, MsgItem.TYPE_SENT, MsgItem.MESSAGE_TYPE_VIDEO);
                addToList(msgItem,intent);
            }

        }
    }

    public void addToList(MsgItem msgItem,Intent intent){
        msgList.add(msgItem);
        adapter.notifyItemInserted(msgList.size() - 1);
        //send to server
        if (client.getStatus()) {
            client.sendFile(getRealPathFromURI(intent.getData()));

        }
    }

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }


}