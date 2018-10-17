
package com.example.kallyruan.eldermap.LocationPkg;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.powermock.api.easymock.PowerMock;

import static org.junit.Assert.assertEquals;

public class FinishedTripTest {
    private float destinationMark = 0.0f;
    private float navigationMark = 0.0f;
    private  String name = "Hello";

    @Mock
    Location location;

    @Mock
    private FinishedTrip trip = new FinishedTrip(0,0,0,0,
            location,name,0.0f,0.0f);
    @Before
    public void setup(){
        location = PowerMock.createMock(Location.class);
        trip= PowerMock.createMock(FinishedTrip.class);
    }
    @Test
    public void getdestinationMark() {
        assertEquals(destinationMark,trip.getdestinationMark(), 1.0f);
    }
    @Test
    public void getTripMark() {
        assertEquals(navigationMark,trip.getTripMark(),1.0f);
    }
}
