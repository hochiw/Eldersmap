package com.example.kallyruan.eldermap.P2PPkg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;
import com.example.kallyruan.eldermap.R;

import org.json.JSONObject;

import java.net.SocketException;

public class ChatActivity extends AppCompatActivity {

    private UDPReceiver receiver;
    private UDPClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2p_chatroom);
        try {
            receiver = new UDPReceiver();
            receiver.execute();
            requestForHelp();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void requestForHelp() {
        String url = "http://eldersmapapi.herokuapp.com/api/getQueue";
        try {
            JSONObject admin = new HTTPPostRequest(url).execute().get();
            if (admin.length() != 0) {
                client = new UDPClient(admin.getString("ip"),admin.getInt("port"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
