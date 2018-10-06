package com.example.kallyruan.eldermap.P2PPkg;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;



public class UDPClient {
    private InetAddress ip;
    private int port;
    private DatagramSocket udpSocket;


    public UDPClient(String ip, int port) throws UnknownHostException, SocketException {
        this.ip = InetAddress.getByName(ip);
        this.port = port;
        this.udpSocket = new DatagramSocket();
    }

    public void sendMessage(String message) throws IOException, JSONException {
        byte[] data = new byte[2048];
        data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
        packet.setLength(data.length);
        udpSocket.send(packet);
    }
}
