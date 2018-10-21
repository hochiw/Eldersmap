package com.example.eldermap.NearbyLankmarkPkg;

import com.example.eldermap.LocationPkg.Location;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
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