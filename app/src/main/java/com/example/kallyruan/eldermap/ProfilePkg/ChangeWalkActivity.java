package com.example.kallyruan.eldermap.ProfilePkg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.kallyruan.eldermap.R;
import java.util.Objects;

/**
 * ChangeWalkActivity class is to get the User preference on walking distance preference and save/
 * update the change to Database
 */
public class ChangeWalkActivity extends BaseActivity {
    public static final int FIVEMINUTES = 5;
    public static final int TENMINUTES = 10;
    public static final int FIFTEENMINUTES = 15;
    public static final int TWENTYMINUTES = 20;
    public static final int DOUBLETIME = -2;
    final int EXTRAHELP = -1;
    final int SECRET = -3;
    final int INVALID = 0;
    final static String SURVEYACTIVITY = "com.example.kallyruan.eldermap.ProfilePkg.ChangeSizeActivity";
    //local variables
    String callingActivity;
    int walkingPreference = INVALID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_survey_walk);
    }

    /**
     * this method is to record user walking time preference from user action. This data would be
     * used for destination recommendation algorithm. It would re-direct
     * to different activities afterwards depending on its calling activity.
     * @param view
     */
    public void recordWalkingPreference(View view) {
        //get the calling activity
        callingActivity = Objects.requireNonNull(this.getCallingActivity()).getClassName();
        //set walking preference based on user click on UI
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

        //check calling activity and re-direct to the next activity
        checkCallingActivity(walkingPreference);

    }



    /**
     * this method is called to check the previous calling activity, different activities would be
     * re-directed to based on the checking result.
     * @param walkingPreference
     */
    public void checkCallingActivity(int walkingPreference){
        if(walkingPreference != INVALID){
            User.notifyWalkingChange(this,walkingPreference);
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

    /**
     * this is a deeper investigation of walking disability if user clicked on "uncomfortable about
     * walking". Data would be saved to database and re-direct to responding new activity.
     * @param view
     */
    public void recordDisabilityPreference(View view){
        int walkingPreference = INVALID;
        //set walking ability preference based on user click on UI
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
