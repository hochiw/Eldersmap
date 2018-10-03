package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import com.example.kallyruan.eldermap.LocationPkg.Location;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

import org.mockito.BDDMockito;
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
@PrepareForTest({Location.class,JSONFactory.class})
public class JSONFactoryTest {

    @Mock
    Location userLoc;

    @Mock
    String landmarkType;

    @InjectMocks
    JSONFactory factory;

    @Test
    public void userDataJSONMaker() throws  Exception{
        JSONObject obj;
        PowerMock.mockStatic(JSONFactory.class);

        BDDMockito.given(JSONFactory.userDataJSONMaker(userLoc,landmarkType)).
                willReturn(new JSONObject());

        Mockito.when(userLoc.getLatitude()).thenReturn(0.0);
        //JSONFactory.userDataJSONMaker(userLoc,landmarkType);


        //Mockito.when(userLoc.getLongitude()).thenReturn(0.0);

        obj = JSONFactory.userDataJSONMaker(userLoc,landmarkType);


    }
}