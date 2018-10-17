package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import com.example.kallyruan.eldermap.LocationPkg.Location;

import org.json.JSONObject;
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
@PrepareForTest({JSONFactory.class, JSONObject.class})
public class JSONFactoryTest {

    @Mock
    Location userLoc;

    String landmarkType = "HelloWorld";

    @Before
    public void setup() throws  Exception{
        PowerMockito.mockStatic(JSONFactory.class);

        PowerMockito.when(JSONFactory.userDataJSONMaker(userLoc,landmarkType)).
                thenReturn(new JSONObject());
        userLoc = Mockito.mock(Location.class);

        Mockito.when(userLoc.getLatitude()).thenReturn(0.0);
        Mockito.when(userLoc.getLongitude()).thenReturn(0.0);

    }

    @Test
    public void userDataJSONMaker() throws  Exception{

        //JSONObject jsonObject = Mockito.mock(JSONObject.class);
        //Alternately
        JSONObject jsonObject = PowerMock.createMock(JSONObject.class);


//        userLoc.setLatitude(0.0);
//        userLoc.setLongitude(0.0);
        //TODO: 10.6 Saturday
        // Verify construction of object.
        PowerMockito.whenNew(JSONObject.class).withNoArguments().thenReturn(jsonObject);
//        Mockito.when(userLoc.getLatitude()).thenReturn(0.0);
//        Mockito.when(userLoc.getLongitude()).thenReturn(0.0);

        // Maybe two equavilant way of writing new object construction?
        //PowerMockito.whenNew(JSONObject.class).withNoArguments().thenReturn(jsonObject);
        //PowerMock.expectNew(JSONObject.class).andReturn(jsonObject);

        //PowerMockito.whenNew(JSONObject.class).withNoArguments().thenReturn(jsonObject);
        PowerMockito.when(JSONFactory.userDataJSONMaker(userLoc,landmarkType)).thenReturn(jsonObject);
        assertEquals(jsonObject, JSONFactory.userDataJSONMaker(userLoc,landmarkType));
        assertEquals(0.0, userLoc.getLongitude(),0.1);
        assertEquals(0.0, userLoc.getLatitude(), 0.1);

        // Verify construction of object.
        replay(jsonObject,JSONObject.class);
        verify(jsonObject,JSONObject.class);

    }
}