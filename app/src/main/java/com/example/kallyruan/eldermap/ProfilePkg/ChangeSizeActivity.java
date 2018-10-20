package com.example.kallyruan.eldermap.ProfilePkg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.kallyruan.eldermap.R;

/**
 * ChangeSizeActivity class is to get the User preference on text size preference and save/
 * update the change to Database
 */
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
        //set text size based on user click on UI
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

        //check its calling activity and redirect back to responding activity
        checkCallingActivity(textSizePreference);

    }

    /**
     * this method is to check calling activity and redirect back to responding activity
     * @param textSizePreference
     */
    public void checkCallingActivity(int textSizePreference) {
        //check whether buttons get clicked and what the calling activity is
        if(textSizePreference != BaseActivity.INVALID){
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
