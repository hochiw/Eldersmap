package com.example.eldermap.LocationPkg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

/**
 * ScheduledTripTest is mainly consisted the functionality for
 * the ScheduledTrip activity. This test case will focus on its
 * getter/setter.
 */
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

    /**
     * SetUp before the test.
     * ScheduledTrip.getInstance is static and we need to mockStatic for the class.
     */
    @Before
    public void setup(){
        location = PowerMock.createMock(Location.class);
        PowerMockito.mockStatic(ScheduledTrip.class);
    }

    /**
     * Test getTargetHour.
     * If success, it should return targetHour.
     */
    @Test
    public void getTargetHour() {
        assertEquals(targetHour,trip.getTargetHour());
    }

    /**
     * Test setTargetHour.
     * If success, it should return
     */
    @Test
    public void setTargetHour() {
        int expected = 10;
        trip.setTargetHour(expected);
        assertEquals(expected, trip.getTargetHour());

    }

    /**
     * Test getTargetMinute.
     * If success, it shoulde return targetMinute.
     */
    @Test
    public void getTargetMinute() {
        assertEquals(targetMinute,trip.getTargetMinute());
    }

    /**
     * Test setTargetMinute.
     * If success, it should return expected.
     */
    @Test
    public void setTargetMinute() {
        int expected = 10;
        trip.setTargetMinute(expected);
        assertEquals(expected,trip.getTargetMinute());
    }

    /**
     * Test getInstance.
     * If success, it should return a new ScheduledTrip.
     */
    @Test
    public void getInstance() {

        PowerMockito.when(ScheduledTrip.getInstance(0,0,0,0,
                0,0,location,name)).thenReturn(trip);
        assertEquals(trip, ScheduledTrip.getInstance(0,0,0,0,
                0,0,location,name));

    }
}