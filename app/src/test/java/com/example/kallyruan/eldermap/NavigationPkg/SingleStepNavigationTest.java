package com.example.kallyruan.eldermap.NavigationPkg;

import org.junit.Test;
import com.example.kallyruan.eldermap.R;
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

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.*;
@RunWith(PowerMockRunner.class)
@PrepareForTest(SingleStepNavigation.class)
public class SingleStepNavigationTest {
    private String expectMove = "HelloWorld";
    private String expectDirection = "HelloWorld";
    private String expectTransportationMethod = "HelloWorld";
    private int expectPeroid = 0;
    private String expectUnit = "HelloWorld";
    private SingleStepNavigation navigation = new SingleStepNavigation(expectMove,expectDirection,
            expectTransportationMethod,
            expectPeroid, expectUnit);


    @Test
    public void getMove() {
        assertEquals(expectMove,navigation.getMove());
    }

    @Test
    public void getDirection() {
        assertEquals(expectDirection,navigation.getDirection());
    }

    @Test
    public void getTransportationMethod() {
        assertEquals(expectTransportationMethod,navigation.getTransportationMethod());
    }

    @Test
    public void getPeriod() {
        assertEquals(expectPeroid,navigation.getPeriod());
    }

    @Test
    public void getUnit() {
        assertEquals(expectUnit,navigation.getUnit());
    }
}