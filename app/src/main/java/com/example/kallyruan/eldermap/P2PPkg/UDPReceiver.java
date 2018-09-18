package com.example.kallyruan.eldermap.P2PPkg;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPReceiver extends AsyncTask<Void,Void,Void> {
    private DatagramSocket socket;

    public UDPReceiver() throws SocketException {
        socket = new DatagramSocket();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (true) {
            try {
                byte[] data = new byte[2048];

                DatagramPacket packet = new DatagramPacket(data,data.length);

                socket.receive(packet);

                String receivedMessage = new String(packet.getData(),0,packet.getLength());

                Log.d("UDP",receivedMessage + " " + packet.getAddress().toString() + " " + packet.getPort());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPort() {
        return socket.getLocalPort();
    }
}
