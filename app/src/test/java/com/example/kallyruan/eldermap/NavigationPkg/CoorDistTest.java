package com.example.kallyruan.eldermap.NavigationPkg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
@RunWith(PowerMockRunner.class)
public class CoorDistTest {

    private double dist;
    private double userLat=10.0;
    private double userLon=12.0;
    private double destLat=31.0;
    private double destLon=41.0;

    private CoorDist coorDist;
    @Before
    public void setup(){
        coorDist = new CoorDist(userLat, userLon, destLat, destLon);
    }

    @Test
    public void getDist() {
        double expected = 3799.62;
        assertEquals(expected, coorDist.getDist(), 1.0f);

    }
}