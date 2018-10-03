package com.example.kallyruan.eldermap.NetworkPkg;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPPostRequest extends AsyncTask<JSONObject,Void,String> {
    private String url;

    public HTTPPostRequest(String url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(JSONObject... params) {
        try {
            URL _url = new URL(url);
            HttpURLConnection urlconnection = (HttpURLConnection) _url.openConnection();

            urlconnection.setDoInput(true);
            urlconnection.setDoOutput(true);

            urlconnection.setRequestMethod("POST");
            urlconnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlconnection.setRequestProperty("Accept", "application/json");

            OutputStreamWriter output = new OutputStreamWriter(urlconnection.getOutputStream(), "UTF-8");
            if (params.length > 0) {
                output.write(params[0].toString());
            } else {
                output.write("");
            }

            output.flush();
            output.close();

            int resultCode = urlconnection.getResponseCode();

            StringBuilder result = new StringBuilder();
            if (resultCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlconnection.getInputStream(), "utf-8"));

                String line = null;
                while ((line = br.readLine()) != null) {

                    result.append(line);
                }
                br.close();

                return result.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
