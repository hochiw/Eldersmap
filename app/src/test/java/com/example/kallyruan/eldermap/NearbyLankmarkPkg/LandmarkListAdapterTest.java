package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
@RunWith(PowerMockRunner.class)
@PrepareForTest({LandmarkListAdapter.class, View.class, Landmark.class, TextView.class})
public class LandmarkListAdapterTest {

    @Mock
    private Activity mActivity;

    @Mock
    private ArrayList<Landmark> places;

    @Mock
    private TextView name;

    @Mock
    private  TextView mark;

    @Mock
    private TextView cost;

    @Mock
    private TextView distance;

    @Mock
    private  ArrayList<Landmark> group;

    @Mock
    private Landmark destination;

    @Mock
    private Landmark place;

    private LandmarkListAdapter adapter;

    private int position = 0;
    private final View view = null;
    private ViewGroup viewGroup;


    @Before
    public void setup(){
        this.mActivity = Mockito.mock(Activity.class);
        group = PowerMock.createMock(ArrayList.class);  // Mock an arraylist ?
        adapter = new LandmarkListAdapter(mActivity, group);
        place = PowerMock.createMock(Landmark.class);
//
//        PowerMockito.when(group.get(position)).thenReturn(place);
//        Mockito.when(place.getRating()).thenReturn(0.0f);




    }
    @Test
    public void getCount() {
        int expected = 0;
        assertEquals(expected,adapter.getCount());
    }

    @Test
    public void getItem() {
        assertEquals(null, adapter.getItem(0));
    }

    @Test
    public void getItemId() {
        assertEquals(0,adapter.getItemId(0));
    }

    // TODO: Not sure if it is feasible to write test when we need to extract data from a private
    // variable.
    @Test
    public void getViewWithNullView() throws Exception {
        // Since the assignment is acted on a private value, not able to test the assignment.
        LayoutInflater inflater = Mockito.mock(LayoutInflater.class);
        final View createdView = Mockito.mock(View.class);

        PowerMockito.whenNew(View.class).withAnyArguments().thenReturn(createdView);
        //PowerMock.expectNew(View.class).andReturn(createdView);



//        PowerMockito.when(place.getName()).thenReturn("HelloWorld");
//        place.getName();
//        PowerMock.replay(place);


        Mockito.when(mActivity.getLayoutInflater()).thenReturn(inflater);
        Mockito.when(inflater.inflate(R.layout.landmark_list_row,null)).
                thenReturn(createdView);

//        Mockito.when(adapter.getView(position, null,viewGroup)).thenReturn(createdView);

        PowerMockito.when(createdView.findViewById(R.id.locationName)).thenReturn(name);

//        Mockito.verify(createdView.findViewById(R.id.locationName));
//        Mockito.verify(createdView.findViewById(R.id.reviewMark));
//        Mockito.verify(createdView.findViewById(R.id.cost));
//        Mockito.verify(createdView.findViewById(R.id.distance));
        // Test if the method is called.
        //Mockito.verify(adapter).getView(position, null, viewGroup);
    }

    @Test
    public void getViewWithView(){

    }

    @Test
    public void calcuateDistance() {   // TODO: Calculate Distance is no more supported.
        //assertEquals(0,adapter.calcuateDistance(destination));
    }
}