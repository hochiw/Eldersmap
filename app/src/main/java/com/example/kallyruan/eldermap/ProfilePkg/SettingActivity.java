package com.example.kallyruan.eldermap.ProfilePkg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kallyruan.eldermap.AppMenuActivity;
import com.example.kallyruan.eldermap.R;


public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setting);
    }

    public void changeSize(View view){
        Intent i = new Intent(getApplicationContext(), SurveySizeActivity.class);
        startActivityForResult(i,1);
    }

    public void changeWalking(View view){
        Intent i = new Intent(getApplicationContext(), ChangeWalkActivity.class);
        startActivityForResult(i,1);
    }

    public void changeData(View view){
        Intent i = new Intent(getApplicationContext(), ChangeDataActivity.class);
        startActivityForResult(i,1);
    }

    public void changePrivilege(View view){
        Intent i = new Intent(getApplicationContext(), ChangePrivilegeActivity.class);
        startActivityForResult(i,1);
    }

    public void backToMenu(View view){
        Intent i = new Intent(getApplicationContext(), AppMenuActivity.class);
        startActivityForResult(i,1);
    }
}
