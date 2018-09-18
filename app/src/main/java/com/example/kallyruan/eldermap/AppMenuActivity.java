package com.example.kallyruan.eldermap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.kallyruan.eldermap.NearbyLankmarkPkg.MenuActivity;
import com.example.kallyruan.eldermap.P2PPkg.ChatActivity;

public class AppMenuActivity extends AppCompatActivity {

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
     * redirect to nearByLandmark page
     */
    public void nearbyAction(){
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(i);
    }

    public void chatAction(){
        Intent i = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(i);
    }

    public void profileAction(){

    }
}
