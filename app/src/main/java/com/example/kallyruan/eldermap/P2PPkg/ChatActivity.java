package com.example.kallyruan.eldermap.P2PPkg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;
import com.example.kallyruan.eldermap.R;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private UDPReceiver receiver;
    private UDPClient client;
    private ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2p_chatroom);
        messages = new ArrayList<Message>();
        try {
            receiver = new UDPReceiver(new OnMessageReceive() {
                @Override
                public void onReceive(Message m) {
                    messages.add(m);
                    Log.d("UDP",m.getMessage());
                }
            });
            receiver.execute();
        } catch (Exception e) {
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
