package com.example.kallyruan.eldermap.NavigationPkg;

import com.example.kallyruan.eldermap.GPSServicePkg.GPSTracker;
import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Timer;

import static org.junit.Assert.assertEquals;
@RunWith(PowerMockRunner.class)
@PrepareForTest({Location.class, JSONObject.class, Position.class})
public class NavigationCheckerTest {

    @Mock
    private GPSTracker gps;

    @Mock
    private ArrayList<Position> list;

    @Mock
    private Position position;

//    @Mock
//    private JSONObject object;

    @InjectMocks
    private NavigationChecker checker;

    @Before
    public void setup() throws Exception{
        // Mock list.
        String url = "helloWorld";
        gps = Mockito.mock(GPSTracker.class);
        position = Mockito.mock(Position.class);
        list = new ArrayList<>();
        list.add(position);

        // Mock used in construcgtor.
        JSONObject object = Mockito.mock(JSONObject.class);
        Location mockLocation = Mockito.mock(Location.class);
        JSONArray jsonArray = Mockito.mock(JSONArray.class);
        HTTPPostRequest request = Mockito.mock(HTTPPostRequest.class);
//        Timer timer = Mockito.mock(Timer.class);
//        Time
        // .get() return a String. execute will send the json via a http request.
        Mockito.doReturn(0.0).when(object).get("curLatitude");
        Mockito.doReturn(0.0).when(object).get("curLongitude");
        Mockito.doReturn(0.0).when(object).get("desLatitude");
        Mockito.doReturn(0.0).when(object).get("desLongitude");

//        Mockito.when(request.execute(object)).thenReturn();
        PowerMockito.whenNew(JSONObject.class).withNoArguments().thenReturn(object);
        PowerMockito.whenNew(HTTPPostRequest.class).withArguments(url).thenReturn(request);

        PowerMockito.whenNew(JSONArray.class).withArguments(request.execute(object).get())
                .thenReturn(jsonArray);


//        PowerMockito.whenNew(Timer.class).withAnyArguments().thenReturn(timer);

        Mockito.when(gps.getLoc()).thenReturn(mockLocation);
//        Mockito.when()


        checker = new NavigationChecker(gps);
    }

    @Test
    public void getPositions() {
        Mockito.when(checker.getPositions()).thenReturn(list);
        assertEquals(list, checker.getPositions());
    }
}