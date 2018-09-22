package com.example.kallyruan.eldermap.P2PPkg;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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

public class ChatActivity extends AppCompatActivity {

    private UDPReceiver receiver;
    private SocketClient client;
    private ArrayList<Message> messages;
    private Button send, picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2p_chatroom);
        send = findViewById(R.id.sendButton);
        send.setOnClickListener(sendButton);
        picture = findViewById(R.id.pic);
        picture.setOnClickListener(getPicture);

        messages = new ArrayList<Message>();
        try {
            client = new SocketClient(new URI("ws://10.13.238.213:8080?type=client"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener sendButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (client != null) {
                EditText text = (EditText)findViewById(R.id.text);
                try {
                    if (client.getStatus()) {
                        client.getInstance().send(text.getText().toString());
                    }
                    text.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private View.OnClickListener getPicture = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
            gallery.setType("image/*");
            startActivityForResult(gallery, 1);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            if (resultCode == RESULT_OK) {
                if (client.getStatus()) {
                    client.sendFile(intent.getData().getPath());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public void requestForHelp() {
        String url = "http://eldersmapapi.herokuapp.com/api/getQueue";
        try {
            JSONObject admin = new HTTPPostRequest(url).execute().get();
            Log.d("UDP",admin.toString());
            if (admin.length() != 0) {
                Log.d("UDP",admin.getString("ip"));
                Log.d("UDP",Integer.toString(admin.getInt("port")));
              //  client = new UDPClient(admin.getString("ip"),admin.getInt("port"));
               // client.sendMessage("HI");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InetAddress getDeviceIP() {
        try {
            for (Enumeration<NetworkInterface> enumNet = NetworkInterface.getNetworkInterfaces();
                 enumNet.hasMoreElements(); ) {
                NetworkInterface netI = enumNet.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI.getInetAddresses();
                     enumIpAddr.hasMoreElements(); ) {
                    InetAddress intAds = enumIpAddr.nextElement();
                    if (!intAds.isLoopbackAddress()) {
                        if (intAds instanceof Inet4Address) {
                            return intAds;
                        }
                    }
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }


}
