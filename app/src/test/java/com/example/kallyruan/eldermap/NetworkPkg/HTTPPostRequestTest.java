package com.example.kallyruan.eldermap.NetworkPkg;
/**
 * No idea hwo to wirte about creation of objects in a loop.....
 * */
import android.os.AsyncTask;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import org.mockito.BDDMockito;
import org.powermock.api.easymock.PowerMock;
import org.junit.Test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.reporter.PowerMockReporterFactory;
import org.powermock.modules.junit4.PowerMockRunner;
@RunWith(PowerMockRunner.class)
@PrepareForTest({OutputStreamWriter.class, URL.class, StringBuilder.class,
    BufferedReader.class, InputStreamReader.class, HttpURLConnection.class})
public class HTTPPostRequestTest {

    private String url = "HelloWorld";
    private HTTPPostRequest request;
    @Mock
    private URL _url;
    @Before
    public void setup(){
        //_url = PowerMockito.mock(URL.class);
        request = new HTTPPostRequest(url);
    }

    @Test
    public void doInBackground() throws Exception{
        // We can't use Mockito.mock() for URL.class since URL.class is a final class.
        int resultCode = 0;

        JSONObject object = PowerMockito.mock(JSONObject.class);
        Mockito.when(object.get("Hello")).thenReturn(1);
        URL _url = PowerMock.createMock(URL.class);
        HttpURLConnection urlConnection = Mockito.mock(HttpURLConnection.class);
        OutputStreamWriter outputStreamWriter = Mockito.mock(OutputStreamWriter.class);
        // Validate the creation of the new object.
        PowerMockito.whenNew(URL.class).withAnyArguments().thenReturn(_url);
        Mockito.when(urlConnection.getResponseCode()).thenReturn(resultCode);

        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("Hello");
        urlConnection.setRequestProperty("Hello","Hello-World");
        urlConnection.setRequestProperty("World","World-Hello");

        Mockito.verify(urlConnection).setDoOutput(true);
        Mockito.verify(urlConnection).setDoInput(true);
        Mockito.verify(urlConnection).setRequestMethod("Hello");
        Mockito.verify(urlConnection).setRequestProperty("Hello", "Hello-World");
        Mockito.verify(urlConnection).setRequestProperty("World", "World-Hello");

        PowerMockito.whenNew(OutputStreamWriter.class).withAnyArguments().
                thenReturn(outputStreamWriter);
        outputStreamWriter.flush();
        outputStreamWriter.close();

        Mockito.verify(outputStreamWriter).flush();
        Mockito.verify(outputStreamWriter).close();
    }
}