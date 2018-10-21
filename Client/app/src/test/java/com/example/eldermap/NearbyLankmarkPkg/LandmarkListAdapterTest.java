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
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 *LandmarkListAdapter holds the functionality to show the view for the landmarkList.
 * Most of the class is about ui setting.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({LandmarkListAdapter.class, View.class,
        Landmark.class, TextView.class, LandmarkListAdapter.ViewHolder.class})
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

    /**
     * Prepare for later test usage.
     */
    @Before
    public void setup(){
        this.mActivity = Mockito.mock(Activity.class);
        group = PowerMock.createMock(ArrayList.class);  // Mock an arraylist ?
        adapter = new LandmarkListAdapter(mActivity, group);
        place = PowerMock.createMock(Landmark.class);

    }

    /**
     * Test getCount
     * If success, it should return expected.
     */
    @Test
    public void getCount() {
        int expected = 0;
        assertEquals(expected,adapter.getCount());
    }

    /**
     * Test getItem
     * If success, it should return null
     */
    @Test
    public void getItem() {
        assertEquals(null, adapter.getItem(0));
    }

    /**
     * Test getItemId
     * If success, it should return 0 as expected.
     */
    @Test
    public void getItemId() {
        assertEquals(0,adapter.getItemId(0));
    }

    /**
     * Test getView.
     * Mainly about ui setting.
     * @throws Exception
     */
    @Test
    public void getView() throws Exception {
        LayoutInflater inflater = Mockito.mock(LayoutInflater.class);
        final View createdView = Mockito.mock(View.class);

        PowerMockito.whenNew(View.class).withAnyArguments().thenReturn(createdView);

        Mockito.when(mActivity.getLayoutInflater()).thenReturn(inflater);
        Mockito.when(inflater.inflate(R.layout.landmark_list_row,null)).
                thenReturn(createdView);

        PowerMockito.suppress(MemberMatcher.method(LandmarkListAdapter.class, "fillImage",
                LandmarkListAdapter.ViewHolder.class,int.class));

        PowerMockito.when(createdView.findViewById(R.id.locationName)).thenReturn(name);
        PowerMockito.suppress(MemberMatcher.method(TextView.class,"setText",String.class));
    }

}