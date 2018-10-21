package com.example.eldermap.NetworkPkg;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPPostRequest extends AsyncTask<JSONObject,Void,String> {
    // Variable to store the url and the result code
    private String url;
    private int resultCode;

    // Constructor to set the url
    public HTTPPostRequest(String url) {
        this.url = url;
    }


    /**
     * An async task that executes the post request
     * @param params JSON object containing the request data
     * @return response from the server as a json string
     */
    @Override
    protected String doInBackground(JSONObject... params) {
        try {
            // Convert the url string to an url object
            URL _url = new URL(url);

            // Create a connection to the url
            HttpURLConnection urlconnection = (HttpURLConnection) _url.openConnection();

            // Parameters for the connection
            urlconnection.setDoInput(true);
            urlconnection.setDoOutput(true);

            urlconnection.setRequestMethod("POST");
            urlconnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlconnection.setRequestProperty("Accept", "application/json");

            // Write the input data to the connection
            OutputStreamWriter input = new OutputStreamWriter(urlconnection.getOutputStream(), "UTF-8");
            if (params.length > 0) {
                input.write(params[0].toString());
            } else {
                input.write("");
            }

            input.flush();
            input.close();

            // get the result code from the connection
            resultCode = urlconnection.getResponseCode();

            StringBuilder result = new StringBuilder();
            // Make sure the request is accepted
            if (resultCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(urlconnection.getInputStream(), "utf-8"));

                // Write the result into a string
                String line;
                while ((line = br.readLine()) != null) {

                    result.append(line);
                }
                br.close();

                // Return the result
                return result.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

}
