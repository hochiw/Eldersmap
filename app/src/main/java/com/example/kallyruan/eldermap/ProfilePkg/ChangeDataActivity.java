package com.example.kallyruan.eldermap.ProfilePkg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kallyruan.eldermap.MainActivity;
import com.example.kallyruan.eldermap.R;

public class ChangeDataActivity extends BaseActivity {
    final int AGREE = 1;
    final int DISAGREE= 2;
    final int INVALID = 0;
    final static String SURVEYACTIVITY = "com.example.kallyruan.eldermap.ProfilePkg.ChangeWalkActivity";
    int permissionPreference = INVALID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_survey_data);
    }

    /**
     * a method to get user collection preference from user action. This method would re-direct
     * to different activities afterwards depending on its calling activity.
     * @param view
     */
    public void recordPermissionPreference(View view){
        switch (view.getId()) {
            case R.id.permission_agree:
                permissionPreference = AGREE;
                break;
            case R.id.text_medium:
                permissionPreference = DISAGREE;
                break;
        }
        //check get button clicked and calling activity
        if(permissionPreference != INVALID){
            savePermissionToDatabase(permissionPreference);
            if(this.getCallingActivity().getClassName().equals(SURVEYACTIVITY)){
                //re-direct to survey - walking length page
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }else{
                //re-direct to setting page
                Intent i = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(i);
            }
        }
    }

    /** this method is to upload user preference to database
     *
     * @param permissionPreference
     */
    private void savePermissionToDatabase(int permissionPreference) {

    }


}
