package com.example.eldermap.P2PPkg;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;

import java.io.File;
import java.net.URI;

/**
 * SocketClient is the class to handle with connection with server and  P2P message sending.
 */
public class SocketClient implements Runnable{

    private Boolean alive = false;
    private WebSocketClient ws;
    private ChatActivity ca;
    private URI address;

    public SocketClient(URI address, ChatActivity ca) {
        this.address = address;
        this.ca = ca;
    }

    /**
     * handles the file sending
     * @param path path of the file
     */
    public void sendFile(String path, int type) {
        // Open the file
        File file = new File(path);

        // Conver the file into the data
        byte[] data = FileEncoder.convertFileToByte(file);
        if (data != null) {
            try {
                // Create the message item
                MsgItem message = new MsgItem(FileEncoder.byteToBase64(data),MsgItem.TYPE_SENT,type);
                // Set the filename
                message.setFileName(file.getName());

                // Send the rich media file as a string
                ws.send(MsgCoder.encode(message));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Start running the client
     */
    public void run() {
        // Create a websocket client
        this.ws = new WebSocketClient(address) {

            /**
             * make the service alive when it's connected
             * @param handshakedata
             */
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                alive = true;
            }

            /**
             * handles message receiving
             * @param message
             */
            @Override
            public void onMessage(String message) {
                // Attempt to decode the message
                try {
                    if (MsgCoder.decode(message) != null) {
                        ca.newMessage(MsgCoder.decode(message));
                    } else {
                        throw new JSONException("Unable to decode");
                    }
                } catch (JSONException e) {
                    // Create a text message if the decoding fails
                    ca.newMessage(new MsgItem(message,MsgItem.TYPE_RECEIVED,MsgItem.MESSAGE_TYPE_TEXT));
                }

            }

            @Override
            public void onClose(int code, String reason, boolean remote) {

            }

            @Override
            public void onError(Exception ex) {

            }
        };

        // Connect to the server
        ws.connect();
    }

    public WebSocketClient getInstance() {
        return ws;
    }

    public Boolean getStatus() {
        return alive;
    }

}
