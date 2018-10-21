package com.example.eldermap.P2PPkg;


import android.content.Context;

import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;

/**
 * VoiceCall class is to create the connection with SinchClient.
 */
public class VoiceCall {

    private static SinchClient voiceClient = null;
    private static Call call;
    private static String userID;
    private static Context context;

    public VoiceCall(String userID, Context context) {
        this.userID = userID;
        this.context = context;
    }

    /**
     * Create the SinchClient
     * @return sinchclient instance
     */
    public static SinchClient getInstance() {
            voiceClient = Sinch.getSinchClientBuilder().context(context)
                    .applicationKey("30a63ef6-ba48-44ef-8a74-e88f95afe18d")
                    .applicationSecret("dACaiFAER0aJfl5/vPyV+w==")
                    .environmentHost("clientapi.sinch.com")
                    .userId(userID)
                    .build();


            // Settings for the sinch client
            voiceClient.setSupportCalling(true);
            voiceClient.setSupportActiveConnectionInBackground(true);
            voiceClient.startListeningOnActiveConnection();

            voiceClient.start();

        return voiceClient;
    }

    public static void setCall(Call incomingCall) {
        call = incomingCall;
    }

    public static Call getCall() {
        if (call != null) {
            return call;
        }
        return null;
    }

}
