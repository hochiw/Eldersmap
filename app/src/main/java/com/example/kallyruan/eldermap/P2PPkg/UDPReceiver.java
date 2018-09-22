package com.example.kallyruan.eldermap.P2PPkg;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPReceiver extends Thread {
    private DatagramSocket socket;
    private OnMessageReceive callback;
    private Boolean running = false;

    public UDPReceiver(OnMessageReceive callback) throws SocketException {
        this.socket = new DatagramSocket();
        this.callback = callback;
        this.running = true;
    }

    public void run() {
        byte[] data = new byte[5000000];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            while (running) {
                socket.receive(packet);
                    String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                    if (receivedMessage.length() > 0) {
                        callback.onReceive(new Message(packet.getAddress(), packet.getPort(), receivedMessage));
                    }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void kill() {
        running = false;
    }

    public int getPort() {
        return socket.getLocalPort();
    }
}
