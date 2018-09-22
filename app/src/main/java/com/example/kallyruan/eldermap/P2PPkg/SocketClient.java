package com.example.kallyruan.eldermap.P2PPkg;


import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

public class SocketClient {

    private Boolean alive = false;
    private WebSocketClient ws;

    public SocketClient(URI address) {
        ws = new WebSocketClient(address) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                alive = true;
            }

            @Override
            public void onMessage(String message) {
                Log.d("WS",message);
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

    public void sendFile(String path) {
        byte[] data = convertFileToByte(new File(path));
        if (data != null) {
            ws.send(data);
        }
    }

    public byte[] convertFileToByte(File file) {
        try {
            FileInputStream fs = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fs.read(bytes,0,(int) file.length());
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public WebSocketClient getInstance() {
        return ws;
    }

    public Boolean getStatus() {
        return alive;
    }

}
