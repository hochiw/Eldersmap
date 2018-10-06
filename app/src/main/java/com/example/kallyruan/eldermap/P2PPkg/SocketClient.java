package com.example.kallyruan.eldermap.P2PPkg;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;

import java.io.File;
import java.net.URI;

public class SocketClient implements Runnable{

    private Boolean alive = false;
    private WebSocketClient ws;
    private ChatActivity ca;
    private URI address;

    public SocketClient(URI address, ChatActivity ca) {
        this.address = address;
        this.ca = ca;
    }

    public void sendFile(String path) {
        File file = new File(path);
        byte[] data = FileEncoder.convertFileToByte(file);
        if (data != null) {
            try {
                MsgItem message = new MsgItem(FileEncoder.byteToBase64(data), MsgItem.TYPE_SENT, MsgItem.MESSAGE_TYPE_GRAPH);
                message.setFileName(file.getName());
                ws.send(MsgCoder.encode(message));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        this.ws = new WebSocketClient(address) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                alive = true;
            }

            @Override
            public void onMessage(String message) {
                try {
                    if (MsgCoder.decode(message) != null) {
                        ca.newMessage(MsgCoder.decode(message));
                    } else {
                        throw new JSONException("Unable to decode");
                    }
                } catch (JSONException e) {
                    ca.newMessage(new MsgItem(message, MsgItem.TYPE_RECEIVED, MsgItem.MESSAGE_TYPE_TEXT));
                }

            }

            @Override
            public void onClose(int code, String reason, boolean remote) {

            }

            @Override
            public void onError(Exception ex) {

            }
        };
        ws.connect();
    }

    public WebSocketClient getInstance() {
        return ws;
    }

    public Boolean getStatus() {
        return alive;
    }

}
