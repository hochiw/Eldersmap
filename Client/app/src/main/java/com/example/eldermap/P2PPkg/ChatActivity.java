package com.example.eldermap.P2PPkg;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eldermap.DBQuery;
import com.example.kallyruan.eldermap.R;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;

import org.json.JSONException;

import java.io.File;
import java.net.URI;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    public final int RESULT_LOAD_IMG = 1;
    public final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 999;

    private SocketClient client; // Socket client
    private List<MsgItem> msgList = new ArrayList<>(); // Message list

    private EditText inputText; // User input text

    private Button send; // Text send button

    private RecyclerView msgRecyclerView;  // Recycler view and adapter

    private MsgAdapter adapter;

    private String userID;

    private String userType = "admin";

    private SinchClient sinchClient; // Sinch Call client




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this following part are for UI view
        setContentView(R.layout.p2p_chatroom);

        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        msgRecyclerView = findViewById(R.id.msg_recycle_view);

        // Get user type
        userType = DBQuery.checkUserType() == 0 ? "client" : "admin";



        // Check if the element is a video then start playing it on click
        adapter = new MsgAdapter(this,msgList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                // Get the video path
                String path = msgList.get(position).getContent();
                // Check if the item is a video
                if( isVideoFile(path)){
                    // Display the video
                    VideoDisplayActivity.setDir(path);
                    Intent i = new Intent(getApplicationContext(),VideoDisplayActivity.class);
                    startActivityForResult(i,1);
                }
            }
        });

        // Layout settings
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setHasFixedSize(true);
        msgRecyclerView.setLayoutManager(layoutManager);
        msgRecyclerView.setAdapter(adapter);

        // Listener for the send button
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the input field
                String content = inputText.getText().toString();
                // Check if the text is empty
                if (!"".equals(content)) {
                    // Create a message item
                    MsgItem msgItem = new MsgItem(content, MsgItem.TYPE_SENT, MsgItem.MESSAGE_TYPE_TEXT);
                    // Add the item to the adapter list for displaying
                    msgList.add(msgItem);
                    // refresh the view
                    adapter.notifyItemInserted(msgList.size() - 1);
                    // nominate the view to the last message
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    // clear the input text content
                    inputText.setText("");
                    // encode the message and send it
                    if (client != null) {
                        try {
                            client.getInstance().send(MsgCoder.encode(msgItem));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });

        // Connect to the websocket server
        try {
            if (client == null) {
                client = new SocketClient(new URI("ws://eldermapswebsocket.herokuapp.com/?type=" + userType),this);
                Thread wsClient = new Thread(client);
                wsClient.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the listener for the quit button to finish the activity on click
        Button quitButton = findViewById(R.id.quit_button);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * Refresh the list
     */
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    /**
     * Button to start the activity for the user to pick a file
     * @param view
     */
    public void getRichMedia(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/* video/*");
        startActivityForResult(intent, RESULT_LOAD_IMG);
    }


    /**
     * Button to start the call with the opponent
     * @param view
     */
    public void startCall(View view){
        // Make sure the user has an id and the sinch client is up
        if (userID != null && sinchClient.isStarted()) {
            // Create an intent for the calling class
            Intent i = new Intent(getApplicationContext(), CallActivity.class);

            // Get the id of the opponent
            // If the user is a client, get the admin with the same id and vice-versa
            String CallID = "";
            switch(userType) {
                case "client":
                    CallID = "admin" + userID;
                    break;
                case "admin":
                    CallID = "client" + userID;
                    break;
            }

            // Start the call
            if (!CallID.isEmpty()) {
                VoiceCall.setCall(sinchClient.getCallClient().callUser(CallID));
                startActivity(i);
            }

        }

    }

    /**
     * Accept call button when there is an incoming call
     * @param view
     */
    public void acceptCall(View view) {
        if (VoiceCall.getCall() != null) {
            // Answer the call
            VoiceCall.getCall().answer();
            // Set the layout back to the chat layout
            setContentView(R.layout.p2p_chatroom);

            // Start the call activity
            Intent i = new Intent(getApplicationContext(), CallActivity.class);
            startActivityForResult(i,1);

        }
    }

    /**
     * Decline call button when there is an incoming call
     * @param view
     */
    public void declineCall(View view) {
        // Hang up the call
        if (VoiceCall.getCall() != null) {
            VoiceCall.getCall().hangup();
        }
        // Set the layout back to the chat layout
        setContentView(R.layout.p2p_chatroom);
    }

    /**
     * Custom Call client listener for the sinch client
     */
    private class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            // Assign the incoming call to the call variable in the voice call module
            VoiceCall.setCall(incomingCall);

            // Show the incoming call page
            setContentView(R.layout.p2p_incoming_call);
        }
    }

    /**
     * Function that calls when a new message is received
     * @param message incoming message
     */
    public void newMessage(final MsgItem message){
        try {
            // Process the message if it is a picture or video
            if ((message.getContentType() == MsgItem.MESSAGE_TYPE_GRAPH || message.getContentType() == MsgItem.MESSAGE_TYPE_VIDEO) &&
                    message.getFileName() != null) {
                // Convert the file into bytes
                byte[] byteArray = FileEncoder.base64ToByte(message.getContent());

                // Save the bytes as a file
                message.setContent(FileEncoder.writeToFile(byteArray, message.getFileName()));

            // Process the message if it is a websocket server message
            } else if (message.getContentType() == MsgItem.MESSAGE_TYPE_USER) {
                // Set the incoming message as the user id
                userID = message.getContent();
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // Create a new voice call client
                        new VoiceCall(userType + userID,getApplicationContext());
                        sinchClient = VoiceCall.getInstance();
                        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
                    }
                });
            }

            // Add the message to the adapter list as long as it's not from the websocket server
            if (message.getContentType() != MsgItem.MESSAGE_TYPE_USER) {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // add the message to the adapter list
                        adapter.add(message);
                        // refresh the view
                        adapter.notifyDataSetChanged();
                        //  adapter.notifyItemInserted(adapter.getItemCount());
                        msgRecyclerView.smoothScrollToPosition(adapter.getItemCount());
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the path from the URI
     * @param contentURI URI
     * @return file path
     */
    private String getRealPathFromURI (Uri contentURI){
        // Initialize the string
        String result;

        // Create a resolver for the URI
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);

        // Attempt to get the path from the URI
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            result = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            cursor.close();
        }

        // Return the path
        return result;
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent intent){

        if (resultCode == RESULT_OK) {
            //get file path
            final Uri fileUri = intent.getData();
            String path = getRealPathFromURI(fileUri);

            // check whether read Permission is granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                checkMediaFileType(path, intent);
            } else {
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Show an explanation to the user asynchronously
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

    /**
     * Check the media type
     * @param path path of the file
     * @param intent intent
     */
    public void checkMediaFileType(String path,Intent intent){
        // Initiailize the file
        File richMediaFile = new File(path);
        if (richMediaFile.exists()) {
            // Check if it is an image or video
            if(isImageFile(path)){
                MsgItem msgItem = new MsgItem(path, MsgItem.TYPE_SENT, MsgItem.MESSAGE_TYPE_GRAPH);
                sendMedia(msgItem,intent);
            }else if (isVideoFile(path)){
                MsgItem msgItem = new MsgItem(path, MsgItem.TYPE_SENT, MsgItem.MESSAGE_TYPE_VIDEO);
                sendMedia(msgItem,intent);
            }
        }
    }

    /**
     * Add the message item to the list
     * @param msgItem message item
     * @param intent intent
     */
    public void sendMedia(MsgItem msgItem, Intent intent){
        // Add the message to the
        msgList.add(msgItem);
        adapter.notifyItemInserted(msgList.size() - 1);
        //send to server
        if (client.getStatus()) {

            client.sendFile(getRealPathFromURI(intent.getData()),msgItem.getContentType());
        }
    }

    /**
     * Check whether the file is an image
     * @param path path of the file
     * @return result
     */
    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    /**
     * Check whether the file is an video
     * @param path path of the file
     * @return result
     */
    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }

    /**
     * Button to quit the chat
     * @param view
     */
    public void quitChat(View view) {
        // Finish the activity
        finish();
    }




}
