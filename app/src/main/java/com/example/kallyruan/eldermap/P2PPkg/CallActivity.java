package com.example.kallyruan.eldermap.P2PPkg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kallyruan.eldermap.R;

public class CallActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this following part are for UI view
        setContentView(R.layout.p2p_call);

        TextView waitingMsg = findViewById(R.id.waiting_message);
        TextView callingMdg = findViewById(R.id.calling_message);
        Button cancelCall = findViewById(R.id.cancelButton);

        //by default, showing waiting for connection message
        callingMdg.setVisibility(View.GONE);
        waitingMsg.setVisibility(View.VISIBLE);

        //here should check whether connected with help centre
        while (connectedCaller()){
            waitingMsg.setVisibility(View.GONE);
            callingMdg.setVisibility(View.VISIBLE);



            //p2p transferring here??
        }

    }

    public boolean connectedCaller(){

        //default false
        return false;
    }

    public void cancelCall(View view){
        //re-direct to previous chatroom page
        finish();

        //stop p2p connection here


    }
}
