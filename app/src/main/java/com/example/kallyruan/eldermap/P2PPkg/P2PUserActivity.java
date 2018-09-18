package com.example.kallyruan.eldermap.P2PPkg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;
import com.example.kallyruan.eldermap.R;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.SocketException;

public class P2PUserActivity extends AppCompatActivity {
    private UDPReceiver receiver;
    private UDPClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2p_admin);
        try {
            receiver = new UDPReceiver();
            receiver.execute();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void requestForAdmin() {
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
