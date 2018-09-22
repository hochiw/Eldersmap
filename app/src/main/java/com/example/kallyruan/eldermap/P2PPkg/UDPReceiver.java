package com.example.kallyruan.eldermap.P2PPkg;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPReceiver extends AsyncTask<Void,Void,Void> {
    private DatagramSocket socket;
    private OnMessageReceive callback;

    public UDPReceiver(OnMessageReceive callback) throws SocketException {
        this.socket = new DatagramSocket();
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (true) {
            try {
                byte[] data = new byte[2048];

                DatagramPacket packet = new DatagramPacket(data,data.length);

                socket.receive(packet);

                String receivedMessage = new String(packet.getData(),0,packet.getLength());

                if (receivedMessage.length() > 0) {
                    // comment this for ui testing, should uncomment afterwards.
                    //callback.onReceive(new MsgItem(packet.getAddress(),packet.getPort(),
                                //receivedMessage,MsgItem.TYPE_RECEIVED,MsgItem.MESSAGE_TYPE_TEXT));

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPort() {
        return socket.getLocalPort();
    }
}
