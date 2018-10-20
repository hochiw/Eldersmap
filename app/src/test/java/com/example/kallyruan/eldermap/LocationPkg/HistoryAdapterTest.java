/**
 *  History Adapter is more about setting the UI picture for the user's travelling history.
 *  Only logic part will be tested.
 *  fillNumberImage(ViewHOlder holder, int index)
 *  fillTypeImage(ViewHolder holder, int type)
 *  are not tested.
 * */

package com.example.kallyruan.eldermap.LocationPkg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kallyruan.eldermap.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;


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
        PowerMockito.when(history.size()).thenReturn(0);
        assertEquals(expected, adapter.getCount());
    }

    @Test
    public void getItem() {
        assertNull(adapter.getItem(1));
    }

    @Test
    public void getItemId() {
        assertEquals((long)0, adapter.getItemId(0));
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


        Mockito.when(mockTrip.getDestinationMark()).thenReturn(0.0f);
        Mockito.when(mockTrip.getDate()).thenReturn("Hello");
        Mockito.when(mockTrip.getTripMark()).thenReturn(0.0f);

        View mockView = mock(View.class);

        // NullPointerException: holder.name.setText();

        final FinishedTrip trip = PowerMockito.mock(FinishedTrip.class);
        Mockito.when(history.get(position)).thenReturn(trip);
        float destinationMark = 0.0f;
        String destinationMarkString = "Hello";
        Mockito.when(trip.getDestinationMark()).thenReturn(destinationMark);
        PowerMockito.mockStatic(Float.class);
        Mockito.when(Float.toString(destinationMark)).thenReturn(destinationMarkString);


        navigationMark.setText(Float.toString(mockTrip.getDestinationMark()));
        locationMark.setText(Float.toString(mockTrip.getDestinationMark()));
        date.setText(mockTrip.getDate());
        name.setText(Float.toString(mockTrip.getDestinationMark()));


        Mockito.verify(navigationMark).setText(Float.toString(mockTrip.getTripMark()));
        Mockito.verify(locationMark).setText(Float.toString(mockTrip.getDestinationMark()));
        Mockito.verify(date).setText(mockTrip.getDate());
        Mockito.verify(name).setText(Float.toString(mockTrip.getDestinationMark()));


    }
}