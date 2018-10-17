package com.example.kallyruan.eldermap.LocationPkg;

import android.util.Log;

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
@PrepareForTest(Location.class)
public class TripTest {
    private int tripID = 0;
    private int targetDay = 0;
    private int targetMonth = 0;
    private int targetYear = 0;

    @Mock
    private Location location;

    private String name = "Hello";
    private Trip trip ;

    @Before
    public void setup(){
        location = PowerMockito.mock(Location.class);
        trip = new Trip(0,0,0,0,
                location,name);
    }

    @Test
    public void getTargetDay() {
        assertEquals(targetDay, trip.getTargetDay());
    }

    @Test
    public void setTargetDay() {
        int expected = 10;
        trip.setTargetDay(expected);
        assertEquals(expected,trip.getTargetDay());
    }

    @Test
    public void getTargetMonth() {
        assertEquals(targetMonth, trip.getTargetMonth());
    }

    @Test
    public void setTargetMonth() {
        int expected = 10;
        trip.setTargetMonth(expected);
        assertEquals(expected,trip.getTargetMonth());
    }

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

    @Test
    public void getDestination() {
        assertEquals(location,trip.getDestination());
    }

    @Test
    public void setDestination() {
        Location location1 = new Location(0.0,0.0);
        trip.setDestination(location1);
        assertEquals(location1,trip.getDestination());
    }

    @Test
    public void getName() {
        assertEquals(name, trip.getName());
    }

    @Test
    public void setName() {
        String mockName = "Hello";
        trip.setName(mockName);
        assertEquals(mockName,trip.getName());
    }

    @Test
    public void getDate() {
        String mockString = String.format("%2d/%2d/%4d",
                targetDay, targetMonth,targetYear);
        assertEquals(mockString,trip.getDate());
    }

    @Test
    public void getTripID() {
        assertEquals(tripID,trip.getTripID());
    }

    @Test
    public void setTripID() {
        int expected = 10;
        trip.setTripID(expected);
        assertEquals(expected,trip.getTripID());
    }
}