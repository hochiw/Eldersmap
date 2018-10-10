package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import com.example.kallyruan.eldermap.LocationPkg.Location;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.Comparator;


public class LandmarkTest {
    private Location location = Location.getInstance(0.0, 0.0,0.0f);
    private Landmark landmark = new Landmark("test", "test", 0.0f,location, 0.0);

    @Test
    public void getName() {
        String expected = "test";
        assertEquals(expected, landmark.getName());
    }

    @Test
    public void getRating() {
        float expected = 0.0f;
        assertEquals(expected, landmark.getRating(), 0.01);
    }

    @Test
    public void getAddress() {
        String expected = "test";
        assertEquals(expected, landmark.getAddress());
    }

    @Test
    public void getLocation() {
        assertThat(landmark.getLocation(), isA(Location.class));
        assertEquals(0.0,landmark.getLocation().getLatitude(), 0.1);
        assertEquals(0.0,landmark.getLocation().getLongitude(), 0.1);
    }

    @Test
    public void setRating() {
        float expected = 1.0f;
        landmark.setRating(expected);
        assertEquals(expected,landmark.getRating(), 0.1);
    }

    @Test
    public void comparatorTest(){
        float expected1Float = 10.0f;
        float expected2Float = 5.0f;
        Landmark expected1 = new Landmark("expected1", "expected1",
                expected1Float, location, 0.0);
        Landmark expected2 = new Landmark("expected1", "expected1",
                expected2Float, location, 0.0);
        assertEquals(5,
                expected1.getRating()-expected2.getRating(),1.0);
    }
}