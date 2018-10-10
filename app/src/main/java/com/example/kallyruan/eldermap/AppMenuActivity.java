package com.example.kallyruan.eldermap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kallyruan.eldermap.LocationPkg.FutureActivity;
import com.example.kallyruan.eldermap.LocationPkg.HistoryActivity;
import com.example.kallyruan.eldermap.NearbyLankmarkPkg.MenuActivity;
import com.example.kallyruan.eldermap.P2PPkg.ChatActivity;
import com.example.kallyruan.eldermap.P2PPkg.VideoDisplayActivity;
import com.example.kallyruan.eldermap.ProfilePkg.BaseActivity;
import com.example.kallyruan.eldermap.ProfilePkg.SettingActivity;

public class AppMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_menu);
        checkUserDecision();
    }

    /**
     * this method is to detect what feature user want to do and redirect to responding page
     */
    public void checkUserDecision(){
        //if nearByLandmark button is clicked, re-direct to nearByLandmark page
        Button nearbyButton = (Button) findViewById(R.id.nearby_button);
        nearbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearbyAction();
            }
        });

        //if conversation button is clicked, re-direct to conversation page
        Button conversationButton = (Button) findViewById(R.id.conversation_button);
        conversationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatAction();
            }
        });

        //if history button is clicked, re-direct to history page
        Button historyButton = (Button) findViewById(R.id.history_button);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyAction();
            }
        });

        //if profile button is clicked, re-direct to future trip plan page
        Button futureButton = (Button) findViewById(R.id.future_button);
        futureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                futureAction();
            }
        });

        //if profile button is clicked, re-direct to profile page
        Button profileButton = (Button) findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileAction();
            }
        });

    }


    /**
     * this method is to re-direct to nearByLandmark page
     */
    public void nearbyAction(){
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(i);
    }

    /**
     * this method is to re-direct to Chat page
     */
    public void chatAction(){
        Intent i = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(i);
    }

    /**
     * this method is to re-direct to History page
     */
    public void historyAction(){
        Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(i);
    }

    /**
     * this method is to re-direct to History page
     */
    public void futureAction(){
        Intent i = new Intent(getApplicationContext(), FutureActivity.class);
        startActivity(i);
    }

    /**
     * this method is to re-direct to setting page
     */
    public void profileAction(){
        Intent i = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(i);
    }


}
