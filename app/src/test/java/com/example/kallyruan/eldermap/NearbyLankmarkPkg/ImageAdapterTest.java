package com.example.kallyruan.eldermap.NearbyLankmarkPkg;

import android.content.Context;
import android.media.Image;
import android.test.mock.MockContext;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kallyruan.eldermap.R;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import android.content.SharedPreferences;


/*@Mock needs to be used with initMock*/

@RunWith(MockitoJUnitRunner.class)
public class ImageAdapterTest {


    @Mock
    private Context context;

    private   View convertView;

    private ViewGroup parent;
    // Dependency on adapter.
    @InjectMocks
    private ImageAdapter adapter;

    @Mock
    ImageView imageView;

    private Integer[] mThumbIds = {
            R.mipmap.ic_hospital, R.mipmap.ic_pharmacy,
            R.mipmap.ic_market,R.mipmap.ic_supermarket,
            R.mipmap.ic_bar, R.mipmap.ic_restaurant,
    };

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);
        context = mock(Context.class);
        adapter = new ImageAdapter(context);


        //c = mock(Context.class);
        //adapter = new ImageAdapter(c);
        //convertView = mock(View.class);

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
        ImageView iv = mock(ImageView.class);


        ImageView expectedImaView;
        int expectedPosition = 0;

        System.out.println(context);
        System.out.println(convertView);

        ImageView imageView;
               imageView =  (ImageView) adapter.getView(expectedPosition,
                        convertView, parent);
        System.out.println(imageView);




        //assertThat(imageView, isA(View.class));

        //assertEquals(null, adapter.getView(expectedPosition, null, parent));
        //assertEquals(ImageView.ScaleType.CENTER_CROP, imageView.getScaleType());
        //assertEquals(8, imageView.getPaddingBottom());

    }
}