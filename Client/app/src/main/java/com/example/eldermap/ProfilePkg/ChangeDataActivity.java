package com.example.eldermap.ProfilePkg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.eldermap.DBQuery;
import com.example.eldermap.MainActivity;
import com.example.kallyruan.eldermap.R;


/**
 * ChangeDataActivity class is to get the User preference on Data collection permission and save/
 * update the change to Database
 */
public class ChangeDataActivity extends BaseActivity {
    final static String SURVEYACTIVITY = "ChangeWalkActivity";
    boolean permission = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_survey_data);
    }

    /**
     * A method to get user collection preference from user action. This method would re-direct
     * to different activities afterwards depending on its calling activity.
     * @param view
     */
    public void recordPermissionPreference(View view){
        //set data permission preference based on user click on UI
        switch (view.getId()) {
            case R.id.permission_agree:
                permission = true;
                break;
            case R.id.permission_disagree:
                permission = false;
                break;
        }
        //check whether get data permission from user
        if(permission != false){
            //update the change to user database
            User.notifyPermissionChange(this, permission);
            //redirect the app depending on its calling activity
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
