package com.example.kallyruan.eldermap.P2PPkg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;
import com.example.kallyruan.eldermap.R;

import org.json.JSONObject;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private UDPReceiver receiver;
    private UDPClient client;
    private ArrayList<MsgItem> msgItems;
    private List<MsgItem> msgList = new ArrayList<MsgItem>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2p_chatroom);
        initMsgs();
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
                }
            }
        });

//        msgItems = new ArrayList<MsgItem>();
//        try {
//            receiver = new UDPReceiver(new OnMessageReceive() {
//                @Override
//                public void onReceive(MsgItem m) {
//                    msgItems.add(m);
//                    Log.d("UDP",m.getContent());
//                }
//            });
//            receiver.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void initMsgs() {
        MsgItem msgItem1 = new MsgItem("Hello guy.", MsgItem.TYPE_RECEIVED,MsgItem.MESSAGE_TYPE_TEXT);
        msgList.add(msgItem1);
        MsgItem msgItem2 = new MsgItem("Hello. Who is that?", MsgItem.TYPE_SENT,MsgItem.MESSAGE_TYPE_TEXT);
        msgList.add(msgItem2);
        MsgItem msgItem3 = new MsgItem("This is Tom. Nice talking to you. ", MsgItem.TYPE_RECEIVED,MsgItem.MESSAGE_TYPE_TEXT);
        msgList.add(msgItem3);
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
