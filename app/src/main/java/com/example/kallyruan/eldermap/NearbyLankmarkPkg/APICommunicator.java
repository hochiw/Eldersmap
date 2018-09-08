package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Kah Chun on 01/09/2018.
 */

public final class APICommunicator {

    //Parses JSON string into JSON object. Code adapted from "https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java"
    public static JSONArray parseJSON(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray jsonarray = new JSONArray(jsonText);
            return jsonarray;
        } finally {
            is.close();
        }
    }

    //Helper function for parseJSON. Code adapted from "https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java"
    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
