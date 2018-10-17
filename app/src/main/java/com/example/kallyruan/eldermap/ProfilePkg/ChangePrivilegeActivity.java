package com.example.kallyruan.eldermap.ProfilePkg;

import android.app.Activity;
import android.os.Bundle;

import com.example.kallyruan.eldermap.R;


public class ChangePrivilegeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_privilege);
    }

    /**
     * this activity is for user request an admin privilege. The request would be processed through
     * backend changes
     */
    private void requestAdmin(){

    }

}
