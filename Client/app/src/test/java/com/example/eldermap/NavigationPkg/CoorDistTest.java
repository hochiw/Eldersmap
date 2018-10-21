package com.example.eldermap.NavigationPkg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

/**
 * CoorDist Class contains the math calculation for the distance
 * between two points(current location and destination/checkpoints).
 * This test case will mainly focus on the mathmetical correctness.
 * This test case uses PowerMockito.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(CoorDist.class)
public class CoorDistTest {

    private double dist;
    private double userLat=11.0;
    private double userLon=21.0;
    private double destLat=31.0;
    private double destLon=41.0;


    private CoorDist coorDist;

    /**
     * SetUp the static class to be tested.
     */
    @Before
    public void setup(){
        PowerMockito.mockStatic(CoorDist.class);
    }

    /**
     * Test getDis.
     * If the calculation is correct, it should return as expected.
     */
    @Test
    public void getDist() {
        double expected = 3034407.101993954;
        PowerMockito.when(CoorDist.getDist(userLat,userLon,destLat,destLon)).thenReturn(3034407.101993954);
        assertEquals(expected, CoorDist.getDist(userLat,userLon,destLat,destLon),1.0);
    }

}

