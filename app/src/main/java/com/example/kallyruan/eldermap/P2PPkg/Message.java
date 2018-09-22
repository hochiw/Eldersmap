package com.example.kallyruan.eldermap.P2PPkg;

import java.net.InetAddress;

public class Message {
    private InetAddress ip;
    private int port;
    private String message;


    public Message(InetAddress ip, int port, String message) {
        this.ip = ip;
        this.port = port;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getIP() {
        return ip.getHostAddress().toString();
    }

    public int getPort() {
        return port;
    }
}
