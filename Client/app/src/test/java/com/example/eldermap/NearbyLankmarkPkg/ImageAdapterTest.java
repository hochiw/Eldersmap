package com.example.eldermap.NearbyLankmarkPkg;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eldermap.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

/**
 * ImageAdapter has the functionality to adapt the pictures for the use of
 * LandMarkList etc. It is more like a bridge between services and frontend ui.
 * Methods tested are getCount, getItem, getItemId, getView.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(  {ImageAdapter.class} )
public class ImageAdapterTest {


    @Mock
    private Context mContext;

    @Mock
    ImageView convertView;

    @Mock
    ViewGroup parent;

    private ImageAdapter adapter;
    private int position = 0;

    private Integer[] mThumbIds = {
            R.mipmap.ic_hospital, R.mipmap.ic_pharmacy,
            R.mipmap.ic_market,R.mipmap.ic_supermarket,
            R.mipmap.ic_bar, R.mipmap.ic_restaurant,
    };

    /**
     * SetUp for the later test usage.
     */
    @Before
    public void setup(){

        adapter = new ImageAdapter(mContext);
    }

    /**
     * Test getCount.
     * If success, it should return expected.
     */
    @Test
    public void getCount() {
        int expected = 6;
        assertEquals(expected, adapter.getCount());
    }

    /**
     * Test getItem.
     * If success, it should return null.
     */
    @Test
    public void getItem() {
        assertEquals(null, adapter.getItem(0));
    }

    /**
     * Test getItemId.
     * If success, it should return 0 as expected.
     */
    @Test
    public void getItemId() {
        assertEquals(0, adapter.getItemId(0));
    }

    /**
     * Test getView.
     * If success, it should not be null.
     */
    @Test
    public void getView() {

        ImageView imageViewMock = mock(ImageView.class);
        doNothing().when(imageViewMock).setImageResource(mThumbIds[position]);
        assertNotNull(adapter.getView(position,convertView,parent));

        doNothing().when(imageViewMock).setScaleType(ImageView.ScaleType.CENTER_CROP);
        doNothing().when(imageViewMock).setPadding(8,8,8,8);
        assertNotNull(adapter.getView(position, null, parent));




    }
}