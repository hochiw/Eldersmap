package com.example.kallyruan.eldermap.LocationPkg;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

import com.example.kallyruan.eldermap.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.objenesis.instantiator.basic.FailingInstantiator;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


// TODO: HistoryAdapter Has been changed. Needs further unit test.
@RunWith(PowerMockRunner.class)
@PrepareForTest({TextView.class, FinishedTrip.class, FutureAdapter.ViewHolder.class
    ,Float.class})
public class HistoryAdapterTest {
    @Mock
    private Activity mActivity;

    @Mock
    private TextView name;
    @Mock
    private TextView date;
    @Mock
    private TextView locationMark;
    @Mock
    private TextView navigationMark;
    @Mock
    private FinishedTrip trip;
    @Mock
    private ViewGroup viewGroup;
    @Mock
    private View view;
    @Mock
    private ArrayList<FinishedTrip> history;
    @InjectMocks
    private HistoryAdapter adapter;

    @Before
    public void setup(){
        mActivity = Mockito.mock(Activity.class);
        trip = Mockito.mock(FinishedTrip.class);
        history = new ArrayList<>();
        history.add(trip);     // Cannot add to the list if the list is a mock.
        view = Mockito.mock(View.class);
        viewGroup = Mockito.mock(ViewGroup.class);
        adapter = new HistoryAdapter(mActivity, history);
        name = Mockito.mock(TextView.class);

        PowerMockito.mockStatic(FutureAdapter.ViewHolder.class);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCount() {
        int expected = 0;
        System.out.println(history.get(0));
        System.out.println(trip);
        //assertEquals(expected, adapter.getCount());
    }

    @Test
    public void getItem() {
        assertNull(adapter.getItem(1));
    }

    @Test
    public void getItemId() {
        assertNull(adapter.getItem(0));
    }

    @Test
    public void getView() {
        int position = 0;
        LayoutInflater inflater = Mockito.mock(LayoutInflater.class);
        //View view = Mockito.mock(View.class);
        Mockito.when(mActivity.getLayoutInflater()).thenReturn(inflater);
        Mockito.when(inflater.inflate(R.layout.history_list_row,null)).
                thenReturn(view);
        Mockito.when(view.findViewById(R.id.destinationName)).
                thenReturn(name);
        Mockito.when(view.findViewById(R.id.date)).thenReturn(date);
        Mockito.when(view.findViewById(R.id.reviewMark)).thenReturn(locationMark);
        Mockito.when(view.findViewById(R.id.navigationMark)).thenReturn(navigationMark);

        FinishedTrip mockTrip = Mockito.mock(FinishedTrip.class);

        Mockito.when(history.get(position)).thenReturn(mockTrip);


        Mockito.when(mockTrip.getdestinationMark()).thenReturn(0.0f);
        Mockito.when(mockTrip.getDate()).thenReturn("Hello");
        Mockito.when(mockTrip.getTripMark()).thenReturn(0.0f);

        View mockView = mock(View.class);

        // NullPointerException: holder.name.setText();

        final FinishedTrip trip = PowerMockito.mock(FinishedTrip.class);
        Mockito.when(history.get(position)).thenReturn(trip);
        float destinationMark = 0.0f;
        String destinationMarkString = "Hello";
        Mockito.when(trip.getdestinationMark()).thenReturn(destinationMark);
        PowerMockito.mockStatic(Float.class);
        Mockito.when(Float.toString(destinationMark)).thenReturn(destinationMarkString);

//        Mockito.verify(name.setText(Float.toString(trip.getdestinationMark())));
        //
        // TODO: HistoryAdapter Needs more Mock on. 
//        adapter.getView(position,view,viewGroup);

        navigationMark.setText(Float.toString(mockTrip.getdestinationMark()));
        locationMark.setText(Float.toString(mockTrip.getdestinationMark()));
        date.setText(mockTrip.getDate());
        name.setText(Float.toString(mockTrip.getdestinationMark()));


        Mockito.verify(navigationMark).setText(Float.toString(mockTrip.getTripMark()));
        Mockito.verify(locationMark).setText(Float.toString(mockTrip.getdestinationMark()));
        Mockito.verify(date).setText(mockTrip.getDate());
        Mockito.verify(name).setText(Float.toString(mockTrip.getdestinationMark()));


    }
}