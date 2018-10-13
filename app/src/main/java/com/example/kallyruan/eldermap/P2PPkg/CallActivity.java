package com.example.kallyruan.eldermap.P2PPkg;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kallyruan.eldermap.R;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;

import java.util.List;

public class CallActivity extends Activity{

    private SinchClient sinchClient;
    private Call call;
    private TextView waitingMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this following part are for UI view
        setContentView(R.layout.p2p_call);

        waitingMsg = findViewById(R.id.waiting_message);
        waitingMsg.setVisibility(View.VISIBLE);

        call = VoiceCall.getCall();
        call.addCallListener(new SinchCallListener());


      //  TextView callingMdg = findViewById(R.id.calling_message);

        //by default, showing waiting for connection message
      //  callingMdg.setVisibility(View.GONE);




        //here should check whether connected with help centre
        while (connectedCaller()){
            waitingMsg.setVisibility(View.GONE);
          //  callingMdg.setVisibility(View.VISIBLE);



            //p2p transferring here??
        }

    }

    private class SinchCallListener implements CallListener {

        @Override
        public void onCallProgressing(Call call) {
            waitingMsg.setText("Calling Help Centre...");
        }

        @Override
        public void onCallEstablished(Call call) {
            waitingMsg.setText("Call connected");
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
        }

        @Override
        public void onCallEnded(Call call) {
            waitingMsg.setText("Call Ended");
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            finish();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {

        }
    }

    public boolean connectedCaller(){

        //default false
        return false;
    }

    public void cancelCall(View view){
        //re-direct to previous chatroom page
        if (call != null) {
            call.hangup();
            finish();
        }

        //stop p2p connection here


    }




}
