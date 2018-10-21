package com.example.eldermap.NavigationPkg;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

/**
 * A Position class should holde information of instructions/modifications and location etc.
 * This test case will cover all getters and setters.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JSONArray.class)
public class PositionTest {
    private String instruction = "Hello";
    private int bearing_before = 0;
    private int bearing_after = 0;
    private double latitude= 0.0;
    private double longitude = 0.0;
    private String modifier= "World";
    @Mock
    private JSONArray location;

    private Position position;
    @Before
    public void setup() throws JSONException{
        location = Mockito.mock(JSONArray.class);
        position = new Position(instruction, modifier, bearing_after,
                bearing_before, location);
    }

    /**
     * Test getinstruction.
     * If success, a  String with JSON format should be returned.
     */
    @Test
    public void getInstruction() {
        assertEquals(instruction, position.getInstruction());
    }

    /**
     * Test getModifier.
     * If success, a String with modification instruction should be returned.
     */
    @Test
    public void getModifier() {
        assertEquals(modifier, position.getModifier());
    }

    /**
     * Test getBearing_after
     * If success, an int should be returned.
     */
    @Test
    public void getBearing_after() {
        assertEquals(bearing_after, position.getBearing_after());
    }

    /**
     * Test getBearing_before.
     * If success, an int bearing_before should be returned.
     */
    @Test
    public void getBearing_before() {
        assertEquals(bearing_before, position.getBearing_before());
    }

    /**
     * Test getLatitude.
     * If success, a double latitude should be returned.
     */
    @Test
    public void getLatitude() {
        assertEquals(latitude, position.getLatitude(),1.0f);
    }

    /**
     * Test getLongitude.
     * If success, a double longitude should be returned.
     */
    @Test
    public void getLongitude() {
        assertEquals(longitude,position.getLongitude(),1.0f);
    }
}