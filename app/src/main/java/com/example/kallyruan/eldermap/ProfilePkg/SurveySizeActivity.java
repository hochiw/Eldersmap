package com.example.kallyruan.eldermap.ProfilePkg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kallyruan.eldermap.R;

public class SurveySizeActivity extends BaseActivity {
    final static String SURVEYACTIVITY = "com.example.kallyruan.eldermap.ProfilePkg.SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_survey_textsize);
    }

    public void recordSizePreference(View view){
        int textSizePreference = BaseActivity.INVALID;
        switch (view.getId()) {
            case R.id.text_small:
                textSizePreference = BaseActivity.SMALL;
                break;
            case R.id.text_medium:
                textSizePreference = BaseActivity.MEDIUM;
                break;
            case R.id.text_large:
                textSizePreference = BaseActivity.LARGE;
                break;
            case R.id.text_extraLarge:
                textSizePreference = BaseActivity.EXTRALARGE;
                break;
        }
        //check get button clicked and calling activity
        if(textSizePreference != BaseActivity.INVALID){
            Log.d("test size: ", Integer.toString(textSizePreference));
            User.notifytextSizeChange(textSizePreference);
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


}
