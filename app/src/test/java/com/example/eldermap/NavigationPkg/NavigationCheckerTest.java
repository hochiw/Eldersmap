package com.example.eldermap.NavigationPkg;

import android.os.AsyncTask;

import com.example.eldermap.LocationPkg.Location;
import com.example.eldermap.NetworkPkg.HTTPPostRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

@RunWith(PowerMockRunner.class)
@PrepareForTest({AsyncTask.class})
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
    @Before
    public void setup() throws Exception{

        String url = "http://eldersmapapi.herokuapp.com/api/route";
        JSONObject object = Mockito.mock(JSONObject.class);
        PowerMockito.whenNew(JSONObject.class).withNoArguments().thenReturn(object);

        JSONArray jsonArray = PowerMockito.mock(JSONArray.class);

        // Mock for jsonobejct read and write.

        userLoc = PowerMockito.mock(Location.class);
        destLoc = PowerMockito.mock(Location.class);

//        Location mocktest = PowerMock.createMock(Location.class);
//        System.out.println(mocktest);
//        Location mockLocation = PowerMockito.mock(Location.class);
//        System.out.println(mockLocation);
//        Location mockLocation2 = Mockito.mock(Location.class);
//        System.out.println(mockLocation2);

        MockitoAnnotations.initMocks(this);

        PowerMockito.when(userLoc.getLongitude()).thenReturn(0.0);
        PowerMockito.when(userLoc.getLatitude()).thenReturn(0.0);
        PowerMockito.when(destLoc.getLatitude()).thenReturn(0.0);
        PowerMockito.when(destLoc.getLatitude()).thenReturn(0.0);

        Mockito.doReturn(0.0).when(object).get("curLatitude");
        Mockito.doReturn(0.0).when(object).get("curLongitude");
        Mockito.doReturn(0.0).when(object).get("desLatitude");
        Mockito.doReturn(0.0).when(object).get("desLongitude");

        // .get() return a String. execute will send the json via a http request.
        // The retury type of HttpPostRequest.execute(json) is android.os.AsyncTask<JSON, Void, String>
        // AsyncTask is an abstract class.
        HTTPPostRequest request = PowerMockito.mock(HTTPPostRequest.class);
        PowerMockito.whenNew(HTTPPostRequest.class).withAnyArguments().thenReturn(request);
        AsyncTask<JSONObject, Void, String> task = Mockito.mock(AsyncTask.class);
        PowerMockito.whenNew(AsyncTask.class).withAnyArguments().thenReturn(task);
        // Initialise a mock String for task.get(), as a result.
        String testMockString = PowerMock.createMock(String.class);
        PowerMockito.whenNew(String.class).withAnyArguments().thenReturn(testMockString);

        // TODO: NullPointerException on the call task.get()... Still working on fixing it...
        // TODO: NullPointerExce[tion on task.get(). Pretty sure that this is something related with final class mock.
        // TODO: Go and check how JSONarray are mocked in SearchAlgTest.
        // That one worked.
        PowerMockito.when(request.execute(object)).thenReturn(task);
        PowerMockito.when(task.get()).thenReturn("Hello");
//        PowerMockito.when(request.execute(object).get()).thenReturn(returnString);
        System.out.println(request.execute(object).get()); // request.execute(obj) works and return a task.
        AsyncTask task1 = request.execute(object);
        String mockString = (String )task1.get();

        System.out.println(mockString + "2222");
        System.out.println(task.get());
        System.out.println("-----");


        PowerMockito.whenNew(JSONArray.class).withAnyArguments().
                thenReturn(jsonArray);
        PowerMockito.when(jsonArray.length()).thenReturn(1);

        checker = new NavigationChecker(userLoc, destLoc);
    }

    @Test
    public void getPositions() throws Exception{
//        Mockito.when(checker.getPositions()).thenReturn(list);
//        assertEquals(list, checker.getPositions());
        AsyncTask task = PowerMockito.mock(AsyncTask.class, Mockito.CALLS_REAL_METHODS);
        PowerMockito.when(task.get()).thenReturn("Hello");
    }
}