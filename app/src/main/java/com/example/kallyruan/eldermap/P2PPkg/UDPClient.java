package com.example.kallyruan.eldermap.P2PPkg;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.Arrays;


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
        byte[] data = message.getBytes();
        if (data != null) {
            DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
            packet.setLength(data.length);
            udpSocket.send(packet);
        }
    }

    public void sendFile(String path, int limit) throws IOException {
        byte[] data = convertFileToByte(new File(path));
        if (data != null) {

            if (data.length > limit) {
                int count = 0;
                while (count < data.length) {
                    byte[] split;
                    if (count+ limit > data.length) {
                        split = Arrays.copyOfRange(data,count,data.length);
                    } else {
                        split = Arrays.copyOfRange(data,count,count + limit);
                    }
                    DatagramPacket packet = new DatagramPacket(split,split.length,ip,port);
                    packet.setLength(split.length);
                    udpSocket.send(packet);
                    count += limit;
                }
            } else {
                DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
                packet.setLength(data.length);
                udpSocket.send(packet);
            }

        }
    }

    public byte[] convertFileToByte(File file) {
        try {
            FileInputStream fs = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fs.read(bytes,0,(int) file.length());
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
