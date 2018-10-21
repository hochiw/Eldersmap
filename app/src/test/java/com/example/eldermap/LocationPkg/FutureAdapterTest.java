package com.example.eldermap.LocationPkg;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eldermap.R;

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

/**
 * This test case includes the test for FutureAdapter.
 * Mocked objects are Activity, futureTrips and FutureAdapter.ViewHolder.
 * FutureAdapter is about image setting and most of it is ui setting.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ScheduledTrip.class, FutureAdapter.class})
public class FutureAdapterTest {
    @Mock
    private Activity activity;
    @Mock
    private ArrayList<ScheduledTrip> futureTrips;
    @Mock
    FutureAdapter.ViewHolder viewHolder;


    @InjectMocks
    private FutureAdapter adapter;

    /**
     * SetUp for later usage.
     * Elements in ViewHolder.inner class are mocked.
     * @throws Exception
     */
    @Before
    public void setup() throws  Exception{

        int position = 0;
        activity = PowerMockito.mock(Activity.class);
        futureTrips = new ArrayList<>();

        // Prepare Mocks for the inner static class.
        viewHolder = PowerMockito.mock(FutureAdapter.ViewHolder.class);
        PowerMockito.whenNew(FutureAdapter.ViewHolder.class).withAnyArguments().
                thenReturn(viewHolder);
        viewHolder.name = PowerMockito.mock(TextView.class);
        viewHolder.date = PowerMockito.mock(TextView.class);
        viewHolder.tripType = PowerMockito.mock(ImageView.class);
        viewHolder.tripRank = PowerMockito.mock(ImageView.class);
        viewHolder.tripName = PowerMockito.mock(TextView.class);
        viewHolder.tripDate = PowerMockito.mock(TextView.class);

        LayoutInflater inflater = PowerMockito.mock(LayoutInflater.class);
        PowerMockito.whenNew(LayoutInflater.class).withAnyArguments().thenReturn(inflater);
        View view = PowerMockito.mock(View.class);
        PowerMockito.when(inflater.inflate(R.layout.history_list_row, null)).thenReturn(view);

        PowerMockito.when(view.findViewById(R.id.destinationName)).thenReturn(viewHolder.name);
        PowerMockito.when(view.findViewById(R.id.date)).thenReturn(viewHolder.date);
        PowerMockito.when(view.findViewById(R.id.trip_type)).thenReturn(viewHolder.tripType);
        PowerMockito.when(view.findViewById(R.id.trip_rank)).thenReturn(viewHolder.tripRank);
        PowerMockito.when(view.findViewById(R.id.trip_name)).thenReturn(viewHolder.tripName);
        PowerMockito.when(view.findViewById(R.id.trip_date)).thenReturn(viewHolder.tripDate);
        PowerMockito.doNothing().when(view).setTag(viewHolder);

        // Initialise all @Mock annotations in the test class
        MockitoAnnotations.initMocks(this);
        // Initialise the adapter obejct.
        adapter = new FutureAdapter(activity, futureTrips);

    }

    /**
     * Test getCount
     * If success, it should return expected.
     */
    @Test
    public void getCount() {
        int expected= 0;
        PowerMockito.when(futureTrips.size()).thenReturn(0);
        assertEquals(expected, adapter.getCount());
    }

    /**
     * Test getItem
     * If success, it should return null.
     */
    @Test
    public void getItem() {
        assertNull(adapter.getItem(0));
    }

    /**
     * Test getItemId
     * If success, it should return expected.
     */
    @Test
    public void getItemId() {
        int expected = 0;
        assertEquals(expected, adapter.getItemId(0));
    }

    /**
     * Test getView.
     * It is hard to do a non-instrumented test with ui setting.
     * So the coverage of the test method is low.
     * @throws Exception
     */
    @Test
    public void getView() throws  Exception{
        View view = PowerMockito.mock(View.class);
        ViewGroup viewGroup = PowerMockito.mock(ViewGroup.class);
        int position = 0, type = 0;
        ScheduledTrip scheduledTrip = PowerMockito.mock(ScheduledTrip.class);
        PowerMockito.when(futureTrips.get(position)).thenReturn(scheduledTrip);

        // Here is more about UI Setting.
        // No need to test further.
        Location location = PowerMockito.mock(Location.class);
        PowerMockito.when(scheduledTrip.getDestination()).thenReturn(location);
        PowerMockito.when(location.getType()).thenReturn(type);

        FutureAdapter adapter;
        adapter = Mockito.spy(new FutureAdapter(activity, futureTrips));
        Mockito.doNothing().when(adapter).fillImage(viewHolder, position);

        viewHolder.tripRank = PowerMockito.mock(ImageView.class);
        Mockito.doNothing().when(viewHolder.tripRank).setImageResource(R.mipmap.ic_rank_1);
        Mockito.doNothing().when(viewHolder.tripRank).setImageResource(R.mipmap.ic_rank_2);
        Mockito.doNothing().when(viewHolder.tripRank).setImageResource(R.mipmap.ic_rank_3);
        Mockito.doNothing().when(viewHolder.tripRank).setImageResource(R.mipmap.ic_rank_4);
        Mockito.doNothing().when(viewHolder.tripRank).setImageResource(R.mipmap.ic_rank_5);
        Mockito.doNothing().when(viewHolder.tripRank).setImageResource(R.mipmap.ic_rank_6);

    }

    /**
     * fillImage involves UI setting. Nothing to test.
     */
    @Test
    public void fillImage() {
    }

    /**
     * fillTypeImage involves UI setting. Nothing to test.
     */
    @Test
    public void fillTypeImage() {
    }
}