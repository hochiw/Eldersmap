package com.example.eldermap.NearbyLankmarkPkg;

import com.example.eldermap.LocationPkg.Location;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

/**
 * JSONFactory class should hold the responsibility to parse user data(Location, LandMarkType)
 * to a JSONObject. It works as a Singleton.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({JSONFactory.class, JSONObject.class})
public class JSONFactoryTest {

    @Mock
    Location userLoc;

    String landmarkType = "HelloWorld";

    /**
     * The class uses static method, so prepare the test by making the mock static.
     * @throws Exception
     */
    @Before
    public void setup() throws  Exception{
        PowerMockito.mockStatic(JSONFactory.class);

        userLoc = Mockito.mock(Location.class);

        Mockito.when(userLoc.getLatitude()).thenReturn(0.0);
        Mockito.when(userLoc.getLongitude()).thenReturn(0.0);

    }

    /**
     * Test userDataJSONMaker.
     * Since the actions are defined by JSONObject, we just need to test if the
     * call are indeed called.
     * @throws Exception
     */
    @Test
    public void userDataJSONMaker() throws  Exception{

        JSONObject jsonObject = PowerMock.createMock(JSONObject.class);

        PowerMockito.whenNew(JSONObject.class).withNoArguments().thenReturn(jsonObject);

        PowerMockito.when(JSONFactory.userDataJSONMaker(userLoc,landmarkType)).thenReturn(jsonObject);
        assertEquals(jsonObject, JSONFactory.userDataJSONMaker(userLoc,landmarkType));
        assertEquals(0.0, userLoc.getLongitude(),0.1);
        assertEquals(0.0, userLoc.getLatitude(), 0.1);

        replay(jsonObject,JSONObject.class);
        verify(jsonObject,JSONObject.class);

    }
}