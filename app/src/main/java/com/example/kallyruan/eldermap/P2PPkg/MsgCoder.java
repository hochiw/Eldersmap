package com.example.kallyruan.eldermap.P2PPkg;

import org.json.JSONException;
import org.json.JSONObject;

public class MsgCoder {
    public static String encode(MsgItem message) throws JSONException {
        JSONObject JsonMsg = new JSONObject();
        JsonMsg.put("type",Integer.toString(message.getContentType()));
        JsonMsg.put("content",message.getContent());
        if (message.getFileName() != null) {
            JsonMsg.put("filename",message.getFileName());
        }
        return JsonMsg.toString();
    }

    public static MsgItem decode(String message) {
        MsgItem result = null;
        try {
            JSONObject JsonMsg = new JSONObject(message);
            if (JsonMsg.has("type") && JsonMsg.has("content")) {
                if (Integer.parseInt(JsonMsg.get("type").toString()) == MsgItem.MESSAGE_TYPE_TEXT) {
                    result = new MsgItem(JsonMsg.get("content").toString(), MsgItem.TYPE_RECEIVED, MsgItem.MESSAGE_TYPE_TEXT);
                } else if (Integer.parseInt(JsonMsg.get("type").toString()) == MsgItem.MESSAGE_TYPE_GRAPH) {
                    result = new MsgItem(JsonMsg.get("content").toString(), MsgItem.TYPE_RECEIVED, MsgItem.MESSAGE_TYPE_GRAPH);
                } else if (Integer.parseInt(JsonMsg.get("type").toString()) == MsgItem.MESSAGE_TYPE_USER) {

                    result = new MsgItem(JsonMsg.get("content").toString(), MsgItem.TYPE_RECEIVED, MsgItem.MESSAGE_TYPE_USER);
                }

                if (JsonMsg.has("filename")) {
                    result.setFileName(JsonMsg.get("filename").toString());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
