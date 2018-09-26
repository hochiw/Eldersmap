package com.example.kallyruan.eldermap.ProfilePkg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kallyruan.eldermap.AppMenuActivity;
import com.example.kallyruan.eldermap.R;

public class SignupActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_signup);
    }


    public void startSurvey(View view){
        Toast message = Toast.makeText(this, "Only a short survey to go!",
                                                                            Toast.LENGTH_SHORT);
        message.show();
        setToastFrontSize(message);


        //re-direct to survey textsize page
        Intent i = new Intent(getApplicationContext(), SurveySizeActivity.class);
        startActivityForResult(i,1);
    }

    public void refuseAlert(View view){
        Toast message = Toast.makeText(getApplicationContext(),
                            "You need to sign up for using this app.", Toast.LENGTH_SHORT);
        message.show();
        setToastFrontSize(message);
    }

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
