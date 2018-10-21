package com.example.eldermap.P2PPkg;

/**
 * MsgItem class defines all attributes about a Message including content, message type and filename.
 */
public class MsgItem {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    public static final int MESSAGE_TYPE_TEXT = 2;
    public static final int MESSAGE_TYPE_GRAPH = 3;
    public static final int MESSAGE_TYPE_VIDEO = 4;
    public static final int MESSAGE_TYPE_USER = 6;

    private String content;
    private String fileName;
    private int msgType;
    private int contentType;

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

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {return fileName;}

}
