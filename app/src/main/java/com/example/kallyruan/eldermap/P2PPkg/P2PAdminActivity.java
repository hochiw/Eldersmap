package com.example.kallyruan.eldermap.P2PPkg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;
import com.example.kallyruan.eldermap.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class P2PAdminActivity extends AppCompatActivity {
    private ToggleButton ready;
    private UDPReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2p_admin);
        ready = (ToggleButton) findViewById(R.id.toggleReady);
        ready.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ready) {
                if (ready) {
                    createReceiver();
                } else {
                    removeReceiver();
                }
            }
        });
      //  UDPClient client = new UDPClient("127.0.0.1",11234);
      //  UDPReceiver receiver = new UDPReceiver(11234);
      //  receiver.execute();
        // client.sendMessage("HI");
    }

    public void createReceiver() {
        String url = "http://eldersmapapi.herokuapp.com/api/sendQueue";
        try {
            receiver = new UDPReceiver();
            receiver.execute();
            int port = receiver.getPort();
            new HTTPPostRequest(url).execute(new JSONObject().put("port",port));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeReceiver() {
        String url = "http://eldersmapapi.herokuapp.com/api/rmQueue";
        if (receiver != null) {
            try{
                int port = receiver.getPort();
                new HTTPPostRequest(url).execute(new JSONObject().put("port",port));
                receiver = null;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }




}
