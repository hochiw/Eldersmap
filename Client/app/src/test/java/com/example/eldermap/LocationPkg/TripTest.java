package com.example.eldermap.LocationPkg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

/**
 * TripTest class contains the unit test for Trip class.
 * The Trip class has mainly getters and setters.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Location.class)
public class TripTest {
    private int tripID = 0;
    private int targetDay = 0;
    private int targetMonth = 0;
    private int targetYear = 0;
    // Mock a Location object.
    @Mock
    private Location location;

    private String name = "Hello";
    private Trip trip ;

    /**
     * SetUp for the test. Every method will have a fresh new Trip object.
     */
    @Before
    public void setup(){
        location = PowerMockito.mock(Location.class);
        trip = new Trip(0,0,0,0,
                location,name);
    }

    /**
     * Test getTargetDay.
     * If success, it should return targetDay.
     */
    @Test
    public void getTargetDay() {
        assertEquals(targetDay, trip.getTargetDay());
    }

    /**
     * Test setTargetDay.
     * If success, it should return expected.
     */
    @Test
    public void setTargetDay() {
        int expected = 10;
        trip.setTargetDay(expected);
        assertEquals(expected,trip.getTargetDay());
    }

    /**
     * Test getTargetMonth.
     * If success, it should return targetMonth.
     */
    @Test
    public void getTargetMonth() {
        assertEquals(targetMonth, trip.getTargetMonth());
    }

    /**
     * Test setTargetMonth.
     * If success, it should return expected.
     */
    @Test
    public void setTargetMonth() {
        int expected = 10;
        trip.setTargetMonth(expected);
        assertEquals(expected,trip.getTargetMonth());
    }

    /**
     * Test getTargetYear.
     * If success, it should return targetYear.
     */
    @Test
    public void getTargetYear() {
        assertEquals(targetYear, trip.getTargetYear());
    }

    @Test
    public void setTargetYear() {
        int expected =10;
        trip.setTargetYear(expected);
        assertEquals(expected,trip.getTargetYear());
    }

    /**
     * Test getDestination.
     * If success, it should return location.
     */
    @Test
    public void getDestination() {
        assertEquals(location,trip.getDestination());
    }

    /**
     * Test setDestination.
     * If success, it should return a new location.
     */
    @Test
    public void setDestination() {
        Location location1 = new Location(0.0,0.0);
        trip.setDestination(location1);
        assertEquals(location1,trip.getDestination());
    }

    /**
     * Test getName.
     * If success, it should return name.
     */
    @Test
    public void getName() {
        assertEquals(name, trip.getName());
    }

    /**
     * Test setName.
     * If success, it should return a new setted name.
     */
    @Test
    public void setName() {
        String mockName = "Hello";
        trip.setName(mockName);
        assertEquals(mockName,trip.getName());
    }

    /**
     * Test getDate.
     * If success, it should return a date.
     */
    @Test
    public void getDate() {
        String mockString = String.format("%2d/%2d/%4d",
                targetDay, targetMonth,targetYear);
        assertEquals(mockString,trip.getDate());
    }

    /**
     * Test getTripID
     * If success, it should return a TripID.
     */
    @Test
    public void getTripID() {
        assertEquals(tripID,trip.getTripID());
    }

    /**
     * Test setTripID.
     * If success, it should return a tripID.
     */
    @Test
    public void setTripID() {
        int expected = 10;
        trip.setTripID(expected);
        assertEquals(expected,trip.getTripID());
    }
}