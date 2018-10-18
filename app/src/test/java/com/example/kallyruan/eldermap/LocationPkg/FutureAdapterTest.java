package com.example.kallyruan.eldermap.LocationPkg;
import com.example.kallyruan.eldermap.R;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

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
import org.w3c.dom.Text;

import java.util.ArrayList;

import static org.junit.Assert.*;
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

    @Before
    public void setup() throws  Exception{
        MockitoAnnotations.initMocks(this);

        int position = 0;
        activity = PowerMockito.mock(Activity.class);

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

//        Not sure if it should be locally declared and used. Just in case.
//        TextView name = viewHolder.name;
//        TextView date = viewHolder.date;
//        ImageView tripType = viewHolder.tripType;
//        ImageView tripRank = viewHolder.tripRank;
//        TextView tripName = viewHolder.tripName;
//        TextView tripDate = viewHolder.tripDate;

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

        adapter = new FutureAdapter(activity, futureTrips);


    }

    @Test
    public void getCount() {

    }

    @Test
    public void getItem() {
    }

    @Test
    public void getItemId() {
    }

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
        Mockito.doNothing().when(viewHolder.tripRank).setImageResource(R.mipmap.ic_rank_1);
        Mockito.doNothing().when(viewHolder.tripRank).setImageResource(R.mipmap.ic_rank_2);
        Mockito.doNothing().when(viewHolder.tripRank).setImageResource(R.mipmap.ic_rank_3);
        Mockito.doNothing().when(viewHolder.tripRank).setImageResource(R.mipmap.ic_rank_4);
        Mockito.doNothing().when(viewHolder.tripRank).setImageResource(R.mipmap.ic_rank_5);
        Mockito.doNothing().when(viewHolder.tripRank).setImageResource(R.mipmap.ic_rank_6);

    }

    /**
     * fillImage involves UI setting.
     */
    @Test
    public void fillImage() {
    }

    /**
     * fillTypeImage involves UI setting. 
     */
    @Test
    public void fillTypeImage() {
    }
}