package com.example.eldermap.NearbyLankmarkPkg;

import com.example.eldermap.LocationPkg.Location;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Landmark holds the data for each landmark. Those stored in landmark will later
 * be used in algorithm to sort the landmarklist.
 * The class is mainly about setter and getter.
 */
public class LandmarkTest {
    private Location location = Location.getInstance(0.0, 0.0);
    private Landmark landmark = new Landmark("test", "test", 0.0f,location, 0);

    /**
     * Test getName
     * If success, it should return expected.
     */
    @Test
    public void getName() {
        String expected = "test";
        assertEquals(expected, landmark.getName());
    }
    /**
     * Test getRating.
     * If success, it should return rating as expected.
     */
    @Test
    public void getRating() {
        float expected = 0.0f;
        assertEquals(expected, landmark.getRating(), 0.01);
    }

    /**
     * Test gerAddress.
     * If success, it should return the address as expected.
     */
    @Test
    public void getAddress() {
        String expected = "test";
        assertEquals(expected, landmark.getAddress());
    }

    /**
     * TEst getLocation.
     * If success, it should return the Location as expected.
     */
    @Test
    public void getLocation() {
        assertThat(landmark.getLocation(), isA(Location.class));
        assertEquals(0.0,landmark.getLocation().getLatitude(), 0.1);
        assertEquals(0.0,landmark.getLocation().getLongitude(), 0.1);
    }

    /**
     * Test setRating.
     * If success, it should be able to change the rating.
     */
    @Test
    public void setRating() {
        float expected = 1.0f;
        landmark.setRating(expected);
        assertEquals(expected,landmark.getRating(), 0.1);
    }

    /**
     * Test comparator.
     * If success, the sorting should be correct.
     */
    @Test
    public void comparatorTest(){
        float expected1Float = 10.0f;
        float expected2Float = 5.0f;
        Landmark expected1 = new Landmark("expected1", "expected1",
                expected1Float, location, 0);
        Landmark expected2 = new Landmark("expected1", "expected1",
                expected2Float, location, 0);
        assertEquals(5,
                expected1.getRating()-expected2.getRating(),1.0);
    }
}