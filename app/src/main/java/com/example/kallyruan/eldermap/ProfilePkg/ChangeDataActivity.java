package com.example.kallyruan.eldermap.ProfilePkg;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kallyruan.eldermap.DBQuery;
import com.example.kallyruan.eldermap.MainActivity;
import com.example.kallyruan.eldermap.NetworkPkg.HTTPPostRequest;
import com.example.kallyruan.eldermap.R;

import org.json.JSONObject;

public class ChangeDataActivity extends BaseActivity {
    final int INVALID = 0;
    final static String SURVEYACTIVITY = "com.example.kallyruan.eldermap.ProfilePkg.ChangeWalkActivity";
    boolean permission = true;

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
                permission = true;
                break;
            case R.id.permission_disagree:
                permission = false;
                break;
        }
        //check get button clicked and calling activity
        if(permission != false){
            User.notifyPermissionChange(this, permission);
            if(this.getCallingActivity().getClassName().equals(SURVEYACTIVITY)){
                //re-direct to main page
                DBQuery.surveyComplete();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }else{
                //re-direct to setting page
                Intent i = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(i);
            }
        }
    }


}
