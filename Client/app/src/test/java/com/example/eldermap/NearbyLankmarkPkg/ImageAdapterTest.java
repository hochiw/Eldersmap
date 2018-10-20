package com.example.eldermap.NearbyLankmarkPkg;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.eldermap.NearbyLankmarkPkg.ImageAdapter;
import com.example.kallyruan.eldermap.R;

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

/*@Mock needs to be used with initMock*/
@RunWith(PowerMockRunner.class)
@PrepareForTest(  {ImageAdapter.class} )
public class ImageAdapterTest {


    @Mock
    private Context mContext;



    @Mock
    ImageView convertView;

    @Mock
    ViewGroup parent;



    // Dependency on adapter.

    private ImageAdapter adapter;
    private int position = 0;



    private Integer[] mThumbIds = {
            R.mipmap.ic_hospital, R.mipmap.ic_pharmacy,
            R.mipmap.ic_market,R.mipmap.ic_supermarket,
            R.mipmap.ic_bar, R.mipmap.ic_restaurant,
    };

    @Before
    public void setup(){

        //MockitoAnnotations.initMocks(this);
        //context = mock(Context.class);
        adapter = new ImageAdapter(mContext);
    }

    @Test
    public void getCount() {
        int expected = 6;
        assertEquals(expected, adapter.getCount());
    }

    @Test
    public void getItem() {
        assertEquals(null, adapter.getItem(0));
    }

    @Test
    public void getItemId() {
        assertEquals(0, adapter.getItemId(0));
    }

    // Consider the converage of the test. Need two tests
    // to cover two conditions.
    @Test
    public void getView() {
        //ImageView mockImageView = PowerMock.createMock(ImageView.class);
        //ImageView mockImageView = Mockito.mock(ImageView.class);


        ImageView imageViewMock = mock(ImageView.class);
        doNothing().when(imageViewMock).setImageResource(mThumbIds[position]);
        assertNotNull(adapter.getView(position,convertView,parent));

        doNothing().when(imageViewMock).setScaleType(ImageView.ScaleType.CENTER_CROP);
        doNothing().when(imageViewMock).setPadding(8,8,8,8);
        assertNotNull(adapter.getView(position, null, parent));




    }
}