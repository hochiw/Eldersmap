package com.example.eldermap.P2PPkg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.example.eldermap.R;

/**
 * VideoDisplayActivity class handles activities with video display features, UI display and
 * redirecting to other activities.
 */
public class VideoDisplayActivity extends Activity {
    private static String dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2p_video);

        // Select the video to play
        VideoView video = findViewById(R.id.video_play);
        video.setVideoPath(dir);

        // Start playing the video
        video.start();
    }

    /**
     * Button to dismiss the video player
     * @param view
     */
    public void cancelVideo(View view){
        finish();
    }

    public static void setDir(String path) {
        dir = path;
    }
}
