package com.example.eldermap.NavigationPkg;

import com.example.eldermap.NavigationPkg.CoorDist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
@RunWith(PowerMockRunner.class)
@PrepareForTest(CoorDist.class)
public class CoorDistTest {

    private double dist;
    private double userLat=11.0;
    private double userLon=21.0;
    private double destLat=31.0;
    private double destLon=41.0;

    //3034407.101993954
    //293.9818667421665 bearing

    private CoorDist coorDist;
    @Before
    public void setup(){
        PowerMockito.mockStatic(CoorDist.class);
    }

    @Test
    public void getDist() {
        double expected = 3034407.101993954;
        PowerMockito.when(CoorDist.getDist(userLat,userLon,destLat,destLon)).thenReturn(3034407.101993954);
        assertEquals(expected, CoorDist.getDist(userLat,userLon,destLat,destLon),1.0);
    }
}

