package com.example.kallyruan.eldermap.LocationPkg;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class LocationTest {
    private Double latitude;
    private Double longtitide;
    Location testLoc ;
    double inputNum = 0.0;
    double expected = 100.0;

    @Before
    public void setup(){
        testLoc =  new Location(100.0, 100.0,0.0f);

    }

    @Test
    public void setLatitude() {
        testLoc.setLatitude(100.0);
        assertEquals(expected, (double)testLoc.getLatitude(), 0.01);
    }
    @Test
    public void setLongitude() {
        testLoc.setLongitude(100.0);
        assertEquals(expected, (double)testLoc.getLongitude(), 0.01);
    }

    @Test
    public void getLatitude() {
        assertEquals(expected, (double)testLoc.getLatitude(), 0.01);
    }

    @Test
    public void getLongitude() {
        assertEquals(expected, (double)testLoc.getLongitude(), 0.01);
    }

    @Test
    public void getInstance() {
        assertThat(testLoc.getInstance(0.0, 0.0,0.0f), isA(Location.class));
    }
}