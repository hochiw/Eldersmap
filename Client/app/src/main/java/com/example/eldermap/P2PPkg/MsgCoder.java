package com.example.eldermap.P2PPkg;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * MsgCoder class is to Convert the message file to json format or Decode from json in to responding
 * file type.
 */
public class MsgCoder {
    /**
     * Encode the message item into a json
     * @param message
     * @return Json string
     * @throws JSONException
     */
    public static String encode(MsgItem message) throws JSONException {
        JSONObject JsonMsg = new JSONObject();
        // Set the message type
        JsonMsg.put("type",Integer.toString(message.getContentType()));
        // Set the content
        JsonMsg.put("content",message.getContent());
        // Check if the message is a file
        if (message.getFileName() != null) {
            JsonMsg.put("filename",message.getFileName());
        }
        // Return the json string
        return JsonMsg.toString();
    }

    /**
     * Decode and convert the json string into a message item
     * @param message
     * @return
     */
    public static MsgItem decode(String message) {
        MsgItem result = null;
        try {
            // Attempt to parse the json object
            JSONObject JsonMsg = new JSONObject(message);
            if (JsonMsg.has("type") && JsonMsg.has("content")) {
                // Handles the text message
                if (Integer.parseInt(JsonMsg.get("type").toString()) == MsgItem.MESSAGE_TYPE_TEXT) {
                    result = new MsgItem(JsonMsg.get("content").toString(), MsgItem.TYPE_RECEIVED, MsgItem.MESSAGE_TYPE_TEXT);
                // Handles the picture
                } else if (Integer.parseInt(JsonMsg.get("type").toString()) == MsgItem.MESSAGE_TYPE_GRAPH) {
                    result = new MsgItem(JsonMsg.get("content").toString(), MsgItem.TYPE_RECEIVED, MsgItem.MESSAGE_TYPE_GRAPH);
                // Handles the video message
                } else if (Integer.parseInt(JsonMsg.get("type").toString()) == MsgItem.MESSAGE_TYPE_VIDEO) {
                    result = new MsgItem(JsonMsg.get("content").toString(),MsgItem.TYPE_RECEIVED,MsgItem.MESSAGE_TYPE_VIDEO);
                // Handles the websocket server message
                } else if (Integer.parseInt(JsonMsg.get("type").toString()) == MsgItem.MESSAGE_TYPE_USER) {
                    result = new MsgItem(JsonMsg.get("content").toString(),MsgItem.TYPE_RECEIVED,MsgItem.MESSAGE_TYPE_USER);
                }

                // Assign the filename to the variable if it has one
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
