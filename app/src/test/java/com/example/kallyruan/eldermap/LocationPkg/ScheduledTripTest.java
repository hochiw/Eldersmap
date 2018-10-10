package com.example.kallyruan.eldermap.LocationPkg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ScheduledTrip.class})
public class ScheduledTripTest {
    private int targetHour = 0;
    private int targetMinute = 0;
    private String name = "Hello";
    @Mock
    Location location;

    ScheduledTrip trip = new ScheduledTrip(0,0,0,0,0,
            0,location,name);
    @Before
    public void setup(){
        location = PowerMock.createMock(Location.class);
        PowerMockito.mockStatic(ScheduledTrip.class);
    }

    @Test
    public void getTargetHour() {
        assertEquals(targetHour,trip.getTargetHour());
    }

    @Test
    public void setTargetHour() {
        int expected = 10;
        trip.setTargetHour(expected);
        assertEquals(expected, trip.getTargetHour());

    }

    @Test
    public void getTargetMinute() {
        assertEquals(targetMinute,trip.getTargetMinute());
    }

    @Test
    public void setTargetMinute() {
        int expected = 10;
        trip.setTargetMinute(expected);
        assertEquals(expected,trip.getTargetMinute());
    }

    @Test
    public void getInstance() {

        PowerMockito.when(ScheduledTrip.getInstance(0,0,0,0,
                0,0,location,name)).thenReturn(trip);
        assertEquals(trip, ScheduledTrip.getInstance(0,0,0,0,
                0,0,location,name));

    }
}