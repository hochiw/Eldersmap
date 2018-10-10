package com.example.kallyruan.eldermap.NetworkPkg;

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
import org.powermock.modules.junit4.PowerMockRunner;
@RunWith(PowerMockRunner.class)
@PrepareForTest({OutputStreamWriter.class, URL.class, StringBuilder.class,
    BufferedReader.class, InputStreamReader.class, })
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
        URL _url = PowerMock.createMock(URL.class);
        HttpURLConnection urlConnection = PowerMock.createMock(HttpURLConnection.class);
        PowerMockito.whenNew(URL.class).withAnyArguments().thenReturn(_url);
    }
}