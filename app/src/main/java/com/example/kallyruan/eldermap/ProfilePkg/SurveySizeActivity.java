package com.example.kallyruan.eldermap.ProfilePkg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kallyruan.eldermap.R;

public class SurveySizeActivity extends Activity {
    public final static int SMALL = 25;
    public final static int MEDIUM = 30;
    public final static int LARGE = 35;
    public final static int EXTRALARGE = 40;
    public final static int INVALID = 0;
    final static String SURVEYACTIVITY = "com.example.kallyruan.eldermap.ProfilePkg.SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_survey_textsize);
    }

    public void recordSizePreference(View view){
        int textSizePreference = INVALID;
        switch (view.getId()) {
            case R.id.text_small:
                textSizePreference = SMALL;
                break;
            case R.id.text_medium:
                textSizePreference = MEDIUM;
                break;
            case R.id.text_large:
                textSizePreference = LARGE;
                break;
            case R.id.text_extraLarge:
                textSizePreference = EXTRALARGE;
                break;
        }
        //check get button clicked and calling activity
        if(textSizePreference != INVALID){
            Log.d("test size: ", Integer.toString(textSizePreference));
            saveSizeToDatabase(textSizePreference);
            if(this.getCallingActivity().getClassName().equals(SURVEYACTIVITY)){
                //re-direct to survey - walking length page
                Intent i = new Intent(getApplicationContext(), ChangeWalkActivity.class);
                startActivityForResult(i,1);
            }else{
                //re-direct to setting page
                Intent i = new Intent(getApplicationContext(), SettingActivity.class);
                startActivityForResult(i,1);
            }
        }

    }

    // this method is to upload user preference to database
    private void saveSizeToDatabase(int textSize) {

    }


}
