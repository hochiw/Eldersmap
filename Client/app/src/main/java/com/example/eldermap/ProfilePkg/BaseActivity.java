package com.example.eldermap.ProfilePkg;

import android.app.Activity;
import android.os.Bundle;

import com.example.eldermap.R;

/**
 * BaseActicity class is to define the text size for all class extending from this class
 */
public class BaseActivity extends Activity {
    public final static int SMALL = 20;
    public final static int MEDIUM = 25;
    public final static int LARGE = 30;
    public final static int EXTRALARGE = 35;
    public final static int INVALID = 0;
    private int textSize;

    /**
     * this method is to set the display pattern with four available text sizes. Each specific font
     * style is defined in res/values/styles.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the user text size preference from User class
        textSize= User.getTextSize();

        //if no text size set, use the medium size as the default size
        if (textSize == INVALID) {
            textSize = MEDIUM;
        }
        //otherwise, set the text size as defined in res/values/styles.xml
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
