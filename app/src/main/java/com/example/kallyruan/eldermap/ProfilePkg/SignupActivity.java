package com.example.kallyruan.eldermap.ProfilePkg;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kallyruan.eldermap.DBQuery;
import com.example.kallyruan.eldermap.R;

/**
 * SignupActivity class is to execute the sign up survey and get user preference through user clicks
 * through UI
 */
public class SignupActivity extends BaseActivity{
    static final int DEFAULT = 25;  // default text size for toast message
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_signup);

        //set title size and style
        TextView title = findViewById(R.id.signup_title);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"FormalTitle.ttf");
        title.setTypeface(typeface);
    }

    /**
     * this method would pop up a message toast and re-direct to ChangSizeActivity page
     * @param view
     */
    public void startSurvey(View view){
        //create a profile on database
        DBQuery.createProfile();
        //show a message toast on UI
        Toast message = Toast.makeText(this, "Only a short survey to go!",
                                                                            Toast.LENGTH_SHORT);
        message.show();
        setToastFrontSize(message);

        //re-direct to survey textsize page
        Intent i = new Intent(getApplicationContext(), ChangeSizeActivity.class);
        startActivityForResult(i,1);
    }

    /**
     * this is to show an alert message to users for signing up
     * @param view
     */
    public void refuseAlert(View view){
        Toast message = Toast.makeText(getApplicationContext(),
                            "You need to sign up for using this app.", Toast.LENGTH_SHORT);
        message.show();
        setToastFrontSize(message);
    }

    /**
     * this method is to set message textsize
     * @param message
     */
    public void setToastFrontSize(Toast message){
        ViewGroup group = (ViewGroup) message.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(DEFAULT);
    }

}
