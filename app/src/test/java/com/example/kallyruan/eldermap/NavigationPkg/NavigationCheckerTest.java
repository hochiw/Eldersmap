package com.example.kallyruan.eldermap.NavigationPkg;

import android.os.AsyncTask;

import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Location.class, JSONObject.class,
        Position.class, JSONArray.class})
public class NavigationCheckerTest {

    @Mock
    private Location userLoc;

    @Mock
    private Location destLoc;

    @Mock
    private ArrayList<Position> list;


    private NavigationChecker checker;


    // TODO: 1. Need to fix later. The newer version requires newer constructors.
    // TODO: 2. There is no gps kept in the checker. Functionality is divided more precisely.
    @Test
    public void setup() throws Exception{

        String url = "http://eldersmapapi.herokuapp.com/api/route";
        JSONObject object = Mockito.mock(JSONObject.class);
        PowerMockito.whenNew(JSONObject.class).withNoArguments().thenReturn(object);

        JSONArray jsonArray = Mockito.mock(JSONArray.class);
        HTTPPostRequest request = Mockito.mock(HTTPPostRequest.class);
        PowerMockito.whenNew(HTTPPostRequest.class).withAnyArguments().thenReturn(request);
        // Mock for jsonobejct read and write.

        userLoc = Mockito.mock(Location.class);
        destLoc = Mockito.mock(Location.class);

        MockitoAnnotations.initMocks(this);

        PowerMockito.when(userLoc.getLongitude()).thenReturn(0.0);
        PowerMockito.when(userLoc.getLatitude()).thenReturn(0.0);
        PowerMockito.when(destLoc.getLatitude()).thenReturn(0.0);
        PowerMockito.when(destLoc.getLatitude()).thenReturn(0.0);

        Mockito.doReturn(0.0).when(object).get("curLatitude");
        Mockito.doReturn(0.0).when(object).get("curLongitude");
        Mockito.doReturn(0.0).when(object).get("desLatitude");
        Mockito.doReturn(0.0).when(object).get("desLongitude");

        // OK Fine I found the problem
        // The return type of JSONObject.put(String, Value) is JSONObject.
        // Cannot use doNothing() here.

        // .get() return a String. execute will send the json via a http request.
        // The retury type of HttpPostRequest.execute(json) is android.os.AsyncTask
        AsyncTask task = PowerMockito.mock(AsyncTask.class);
//        JSONObject returnObject = PowerMockito.mock(JSONObject.class);
        String returnString = "HELLO WORLD";

//        Mockito.when(task.get()).thenReturn(jsonObject1);
//        Mockito.when(request.execute(object)).thenReturn(task);
        PowerMockito.when(request.execute(object)).thenReturn(task);
        PowerMockito.when(request.execute(object).get()).thenReturn(returnString);
        System.out.println(request.execute(object).get());
        //Up to this point, The string seems to be returned correctly.
//        PowerMockito.whenNew(JSONArray.class).withParameterTypes(String.class).
//                withArguments(request.execute(object).get()).thenReturn(jsonArray);
        PowerMockito.whenNew(JSONArray.class).withParameterTypes(String.class).
                withArguments(new HTTPPostRequest(url).execute(object).get()).
                thenReturn(jsonArray);
        PowerMockito.when(jsonArray.length()).thenReturn(1);
        System.out.println(jsonArray.opt(0));


        // This shows that the         PowerMockito.whenNew(HTTPPostRequest.class).withAnyArguments().thenReturn(request);
        // Is Working
//        System.out.println(new HTTPPostRequest(url));



//        PowerMockito.whenNew(JSONArray.class).withArguments(jsonObject1).thenReturn(jsonArray);
        checker = new NavigationChecker(userLoc, destLoc);
    }

    @Test
    public void getPositions() {
//        Mockito.when(checker.getPositions()).thenReturn(list);
//        assertEquals(list, checker.getPositions());
    }
}