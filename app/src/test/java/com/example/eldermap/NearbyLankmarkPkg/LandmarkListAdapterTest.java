package com.example.eldermap.NearbyLankmarkPkg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eldermap.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
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

    // getView is mostly about setting ui, which calls fillImage.
    @Test
    public void getViewWithNullView() throws Exception {
        // Since the assignment is acted on a private value, not able to test the assignment.
        LayoutInflater inflater = Mockito.mock(LayoutInflater.class);
        final View createdView = Mockito.mock(View.class);

        PowerMockito.whenNew(View.class).withAnyArguments().thenReturn(createdView);



        Mockito.when(mActivity.getLayoutInflater()).thenReturn(inflater);
        Mockito.when(inflater.inflate(R.layout.landmark_list_row,null)).
                thenReturn(createdView);

        PowerMockito.when(createdView.findViewById(R.id.locationName)).thenReturn(name);


    }

    @Test
    public void getViewWithView(){

    }
}