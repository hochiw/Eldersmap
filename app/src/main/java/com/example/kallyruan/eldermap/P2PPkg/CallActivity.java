package com.example.kallyruan.eldermap.P2PPkg;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kallyruan.eldermap.R;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

public class CallActivity extends Activity{

    // Initialize the call and the waiting message variable
    private Call call;
    private TextView waitingMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this following part are for UI view
        setContentView(R.layout.p2p_call);

        // Assign the waiting message view element to the variable
        waitingMsg = findViewById(R.id.waiting_message);
        waitingMsg.setVisibility(View.VISIBLE);

        // Get the call object from the VoiceCall module
        call = VoiceCall.getCall();
        call.addCallListener(new SinchCallListener());

    }

    // Custom call listener for the call
    private class SinchCallListener implements CallListener {

        /**
         * The function that handles the text when calling
         * @param call Call object
         */
        @Override
        public void onCallProgressing(Call call) {
            waitingMsg.setText("Calling Help Centre...");
        }

        /**
         * The function that handles the text and audio setting when the call is established
         * @param call Call object
         */
        @Override
        public void onCallEstablished(Call call) {
            waitingMsg.setText("Call connected");
            // Set the audio control to CALL
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
        }

        /**
         * The function that handles the audio setting and the text when the call is ended
         * @param call Call object
         */
        @Override
        public void onCallEnded(Call call) {
            waitingMsg.setText("Call Ended");
            // Set the audio control back to default
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            // End the activity
            finish();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {

        }
    }

    /**
     * Button to decline the call
     * @param view
     */
    public void cancelCall(View view){
        if (call != null) {
            // Hang up the call
            call.hangup();
        }
    }




}
