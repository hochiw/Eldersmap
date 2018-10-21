package com.example.eldermap.LocationPkg;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Unit Test for Location class.
 * The Location class is mainly consisted of getters and setters.
 */
public class LocationTest {
    private Double latitude;
    private Double longtitide;
    Location testLoc ;
    double inputNum = 0.0;
    double expected = 100.0;
    private int type=0;

    /**
     * SetUp for tests. Each method will have a fresh Location object.
     */
    @Before
    public void setup(){
        testLoc =  new Location(100.0, 100.0);
    }

    /**
     * Test setLatitude.
     * If success, it should return expected.
     */
    @Test
    public void setLatitude() {
        testLoc.setLatitude(100.0);
        assertEquals(expected, (double)testLoc.getLatitude(), 0.01);
    }

    /**
     * Test setLongitude.
     * If success, it should return expected.
     */
    @Test
    public void setLongitude() {
        testLoc.setLongitude(100.0);
        assertEquals(expected, (double)testLoc.getLongitude(), 0.01);
    }

    /**
     * Test getLatitude.
     * If success, it should return expected.
     */
    @Test
    public void getLatitude() {
        assertEquals(expected, (double)testLoc.getLatitude(), 0.01);
    }

    /**
     * Test getLongitude.
     * If success, it should return expected.
     */
    @Test
    public void getLongitude() {
        assertEquals(expected, (double)testLoc.getLongitude(), 0.01);
    }

    /**
     * Test getInstance.
     * If success, it should return a Location object.
     */
    @Test
    public void getInstance() {
        assertThat(testLoc.getInstance(0.0, 0.0), isA(Location.class));
    }

    /**
     * Test setAltitude.
     * If success, it should return expected.
     */
    @Test
    public void setAltitude(){
        testLoc.setAltitude(100.0);
        assertEquals(expected, testLoc.getAltitude(), 1.0);
    }

    /**
     * Test getAltitude.
     * If success, it should return expected.
     */
    @Test
    public void getAltitude(){
        testLoc.setAltitude(100.0);
        assertEquals(expected, testLoc.getAltitude(), 1.0);
    }

    /**
     * Test getType.
     * If success, it should return 0.
     */
    @Test
    public void getType(){
        assertEquals(0, testLoc.getType());
    }
}