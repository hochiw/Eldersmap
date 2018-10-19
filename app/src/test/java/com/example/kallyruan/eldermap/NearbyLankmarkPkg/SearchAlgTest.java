package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import org.junit.Test;

import static org.junit.Assert.*;
import com.example.kallyruan.eldermap.LocationPkg.Location;
import com.example.kallyruan.eldermap.NavigationPkg.CoorDist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

import org.mockito.BDDMockito;
import org.mockito.internal.matchers.Null;
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
public class SearchAlgTest {

    private SearchAlg searchAlg;

    @Before
    public void setup() throws Exception{
        searchAlg = new SearchAlg();
    }

    @Test
    public void filterList() throws Exception{

        Landmark landmark = PowerMockito.mock(Landmark.class);
        JSONObject object = PowerMockito.mock(JSONObject.class);
        Location userLoc = PowerMockito.mock(Location.class);

        ArrayList<Landmark> returnList = PowerMockito.mock(ArrayList.class);
        PowerMockito.whenNew(ArrayList.class).withAnyArguments().thenReturn(returnList);

        JSONArray array = PowerMockito.mock(JSONArray.class);
        PowerMockito.whenNew(JSONArray.class).withAnyArguments().thenReturn(array);

        String mockString = PowerMockito.mock(String.class);
        PowerMockito.when(object.get("results")).thenReturn(mockString);

        searchAlg.filterList(object, userLoc);
    }

    @Test
    public void estWalkTime() {
        double test = 10.0;
        int expected = 5;
        assertEquals(expected,searchAlg.estWalkTime(test));

    }
}