package com.example.kallyruan.eldermap.P2PPkg;


import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

public class VoiceCall {
    private SinchClient voiceClient;
    private Call call;

    public VoiceCall(String userID,Context context) {
        voiceClient = Sinch.getSinchClientBuilder().context(context)
                .applicationKey("30a63ef6-ba48-44ef-8a74-e88f95afe18d")
                .applicationSecret("dACaiFAER0aJfl5/vPyV+w==")
                .environmentHost("clientapi.sinch.com")
                .userId(userID)
                .build();

        voiceClient.setSupportCalling(true);
        voiceClient.startListeningOnActiveConnection();

        voiceClient.start();

        voiceClient.getCallClient().addCallClientListener(new SinchCallClientListener());
    }

    public CallClient getCallClient() {
        return voiceClient.getCallClient();
    }

    private class SinchCallListener implements CallListener {

        @Override
        public void onCallProgressing(Call call) {

        }

        @Override
        public void onCallEstablished(Call call) {

        }

        @Override
        public void onCallEnded(Call call) {

        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {

        }
    }

    private class SinchCallClientListener implements CallClientListener {

        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            call = incomingCall;
            call.answer();
        }
    }

}
