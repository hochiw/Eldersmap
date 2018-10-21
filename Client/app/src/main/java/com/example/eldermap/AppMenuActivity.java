package com.example.eldermap;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eldermap.LocationPkg.FutureActivity;
import com.example.eldermap.LocationPkg.HistoryActivity;
import com.example.eldermap.NearbyLankmarkPkg.MenuActivity;
import com.example.eldermap.P2PPkg.ChatActivity;
import com.example.eldermap.ProfilePkg.BaseActivity;
import com.example.eldermap.ProfilePkg.SettingActivity;
import com.example.eldermap.R;

public class AppMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_menu);

        //set title size and style
        TextView title = findViewById(R.id.menu_title);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"FormalTitle.ttf"); // create a typeface from the raw ttf
        title.setTypeface(typeface);

        checkUserChoice();
    }

    /**
     * this method is to detect what feature user want to do and redirect to corresponding page
     */
    public void checkUserChoice(){
        //if nearByLandmark button is clicked, re-direct to nearByLandmark page
        Button nearbyButton = findViewById(R.id.nearby_button);
        nearbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearbyAction();
            }
        });

        //if conversation button is clicked, re-direct to conversation page
        Button conversationButton = findViewById(R.id.conversation_button);
        conversationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatAction();
            }
        });

        //if history button is clicked, re-direct to history page
        Button historyButton = findViewById(R.id.history_button);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyAction();
            }
        });

        //if profile button is clicked, re-direct to future trip plan page
        Button futureButton = findViewById(R.id.future_button);
        futureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                futureAction();
            }
        });

        //if profile button is clicked, re-direct to profile page
        Button profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileAction();
            }
        });

    }


    /**
     * Redirect the user to the nearByLandmark page
     */
    public void nearbyAction(){
        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(i);
    }

    /**
     * Redirect the user to the Chat page
     */
    public void chatAction(){
        Intent i = new Intent(getApplicationContext(), ChatActivity.class);
        startActivity(i);
    }

    /**
     * Redirect the user to theHistory page
     */
    public void historyAction(){
        Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
        startActivity(i);
    }

    /**
     * Redirect the user to the History page
     */
    public void futureAction(){
        Intent i = new Intent(getApplicationContext(), FutureActivity.class);
        startActivity(i);
    }

    /**
     * Redirect the user to the setting page
     */
    public void profileAction(){
        Intent i = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(i);
    }


}
