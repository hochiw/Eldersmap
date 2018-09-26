package com.example.kallyruan.eldermap.ProfilePkg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.kallyruan.eldermap.R;

import java.util.Objects;

public class ChangeWalkActivity extends Activity {
    final int FIVEMINUTES = 5;
    final int TENMINUTES = 10;
    final int FIFTEENMINUTES = 15;
    final int TWENTYMINUTES = 20;
    final int DOUBLETIME = -2;
    final int EXTRAHELP = -1;
    final int SECRET = -3;
    final int INVALID = 0;
    final static String SURVEYACTIVITY = "com.example.kallyruan.eldermap.ProfilePkg.SurveySizeActivity";
    //local variables
    String callingActivity;
    int walkingPreference = INVALID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_survey_walk);
    }


    public void recordWalkingPreference(View view) {
        callingActivity = Objects.requireNonNull(this.getCallingActivity()).getClassName();
        switch (view.getId()) {
            case R.id.walking_uncomfortable:
                setContentView(R.layout.profile_survey_walk_assistance);
                break;
            case R.id.walking_5mins:
                walkingPreference = FIVEMINUTES;
                break;
            case R.id.walking_10mins:
                walkingPreference = TENMINUTES;
                break;
            case R.id.walking_15mins:
                walkingPreference = FIFTEENMINUTES;
                break;
            case R.id.walking_20mins:
                walkingPreference = TWENTYMINUTES;
                break;
        }

        //check get button clicked and calling activity
        checkCallingActivity(walkingPreference);

    }


    // this method is to upload user preference to database
    private void saveToDatabase(int walkingPreference) {

    }

    public void checkCallingActivity(int walkingPreference){
        if(walkingPreference != INVALID){
            Log.d("test walking: ", Integer.toString(walkingPreference));
            saveToDatabase(walkingPreference);
            if(callingActivity.equals(SURVEYACTIVITY)){
                //re-direct to survey - data collection page
                Intent i = new Intent(getApplicationContext(), ChangeDataActivity.class);
                startActivityForResult(i,1);
            }else{
                //re-direct to setting page
                Intent i = new Intent(getApplicationContext(), SettingActivity.class);
                startActivityForResult(i,1);
            }
        }
    }

    public void recordDisabilityPreference(View view){
        int walkingPreference = INVALID;
        switch (view.getId()) {
            case R.id.walking_wheelchair:
                walkingPreference = DOUBLETIME;
                setContentView(R.layout.profile_survey_walk_assistance);
                break;
            case R.id.walking_extraHelp:
                walkingPreference = EXTRAHELP;
                break;
            case R.id.walking_secret:
                walkingPreference = SECRET;
                break;
        }
        //check get button clicked and calling activity
        checkCallingActivity(walkingPreference);
    }
}
