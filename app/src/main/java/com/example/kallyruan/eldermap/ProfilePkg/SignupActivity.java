package com.example.kallyruan.eldermap.ProfilePkg;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kallyruan.eldermap.AppMenuActivity;
import com.example.kallyruan.eldermap.R;

public class SignupActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_signup);
    }

    /**
     * this method would pop up a message toast and re-direct to ChangSizeActivity page
     * @param view
     */
    public void startSurvey(View view){
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
        messageTextView.setTextSize(25);
    }

    // this method is only for demo purpose, should be deleted in the final version
    public void backActivity(View view){
        Intent i = new Intent(getApplicationContext(), AppMenuActivity.class);
        startActivity(i);
    }
}
