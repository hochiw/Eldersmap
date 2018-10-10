package com.example.kallyruan.eldermap.ProfilePkg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kallyruan.eldermap.R;

public class ChangeSizeActivity extends BaseActivity {
    final static String SURVEYACTIVITY = "com.example.kallyruan.eldermap.ProfilePkg.SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_survey_textsize);
    }

    /**
     * this method is to record user textsize preference based on their click. This would also notify
     * User class about textsize change to affect following UI display. After all changes, it will
     * re-direct to the responding activity page according to calling activity class.
     * @param view
     */
    public void recordSizePreference(View view){
        int textSizePreference = BaseActivity.INVALID;
        //set textsize style based on user click
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

        //check whether buttons get clicked and what the calling activity is
        if(textSizePreference != BaseActivity.INVALID){
            Log.d("test size: ", Integer.toString(textSizePreference));
            User.notifytextSizeChange(this,textSizePreference);
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
