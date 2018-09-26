package com.example.kallyruan.eldermap.P2PPkg;

import java.net.InetAddress;

public class MsgItem {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    public static final int MESSAGE_TYPE_TEXT = 2;
    public static final int MESSAGE_TYPE_GRAPH = 3;
    public static final int MESSAGE_TYPE_VIDEO = 4;
    public static final int MESSAGE_TYPE_VOICE = 5;

    private InetAddress ip;
    private int port;
    private String content;
    private int msgType;
    private int contentType;
    private String fileName;

    //this constructor is used now for demo purpose since I (Kally) have no idea about
    // InetAddress and port format
    public MsgItem(String content, int type, int msgType) {
        this.content = content;
        this.msgType = type;
        this.contentType = msgType;
        this.fileName = null;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return msgType;
    }

    public void setType(int type) {
        this.msgType = type;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {return fileName;}

}