package com.example.kallyruan.eldermap.ProfilePkg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.kallyruan.eldermap.R;

public class BaseActivity extends Activity {
    public final static int SMALL = 20;
    public final static int MEDIUM = 25;
    public final static int LARGE = 30;
    public final static int EXTRALARGE = 35;
    public final static int INVALID = 0;
    private int textSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textSize= User.getTextSize();
        Log.d("test textsize",Integer.toString(textSize));

        if (textSize == SMALL) {
            setTheme(R.style.Default_TextSize_Small);
        } else if (textSize == MEDIUM) {
            setTheme(R.style.Default_TextSize_Middle);
        } else if (textSize == LARGE) {
            setTheme(R.style.Default_TextSize_Big);
        } else if (textSize == EXTRALARGE) {
            setTheme(R.style.Default_TextSize_ExtraBig);
        }

    }
}
