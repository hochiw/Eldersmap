package com.example.eldermap.NavigationPkg;

import android.hardware.GeomagneticField;
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

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberMatcher.constructor;


import java.util.ArrayList;

import javax.xml.validation.Validator;

/**
 * NavigationChecker is to check the user's current location and compares with
 * navigation instructions. getPosition is tested in the test case.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AsyncTask.class, JSONArray.class})
public class NavigationCheckerTest {

    @Mock
    private Location userLoc;

    @Mock
    private Location destLoc;

    @Mock
    private ArrayList<Position> list;


    private NavigationChecker checker;

    /**
     * SetUp for the later test usage.
     * @throws Exception
     */

    @Before
    public void setup() throws Exception{

        String url = "http://eldersmapapi.herokuapp.com/api/route";
        JSONObject object = Mockito.mock(JSONObject.class);
        PowerMockito.whenNew(JSONObject.class).withNoArguments().thenReturn(object);

        JSONArray jsonArray = PowerMockito.mock(JSONArray.class);

        userLoc = PowerMockito.mock(Location.class);
        destLoc = PowerMockito.mock(Location.class);


        MockitoAnnotations.initMocks(this);

        PowerMockito.when(userLoc.getLongitude()).thenReturn(0.0);
        PowerMockito.when(userLoc.getLatitude()).thenReturn(0.0);
        PowerMockito.when(destLoc.getLatitude()).thenReturn(0.0);
        PowerMockito.when(destLoc.getLatitude()).thenReturn(0.0);

        Mockito.doReturn(0.0).when(object).get("curLatitude");
        Mockito.doReturn(0.0).when(object).get("curLongitude");
        Mockito.doReturn(0.0).when(object).get("desLatitude");
        Mockito.doReturn(0.0).when(object).get("desLongitude");

        HTTPPostRequest request = PowerMockito.mock(HTTPPostRequest.class);
        PowerMockito.whenNew(HTTPPostRequest.class).withAnyArguments().thenReturn(request);
        AsyncTask<JSONObject, Void, String> task = Mockito.mock(AsyncTask.class);
        PowerMockito.whenNew(AsyncTask.class).withAnyArguments().thenReturn(task);
        // Initialise a mock String for task.get(), as a result.
        String testMockString = PowerMock.createMock(String.class);
        PowerMockito.whenNew(String.class).withAnyArguments().thenReturn(testMockString);

        PowerMockito.when(request.execute(object)).thenReturn(task);
        PowerMockito.when(task.get()).thenReturn("Hello");
        AsyncTask task1 = request.execute(object);

        PowerMockito.whenNew(JSONArray.class).withAnyArguments().
                thenReturn(jsonArray);
        PowerMockito.when(jsonArray.length()).thenReturn(1);

        PowerMockito.suppress(constructor(JSONArray.class,Object.class));
        PowerMockito.suppress(method(AsyncTask.class,"get"));
        PowerMockito.suppress(method(HTTPPostRequest.class,"execute",JSONObject.class));

    }
    /**
     * Test getPositions.
     * If success, It should return a list of Positions.
     */
    @Test
    public void getPositions() throws Exception{
        AsyncTask task = PowerMockito.mock(AsyncTask.class, Mockito.CALLS_REAL_METHODS);
        PowerMockito.when(task.get()).thenReturn("Hello");
        NavigationChecker checker = PowerMockito.mock(NavigationChecker.class);
        PowerMockito.when(checker.getPositions()).thenReturn(list);
        assertEquals(list, checker.getPositions());
    }
}