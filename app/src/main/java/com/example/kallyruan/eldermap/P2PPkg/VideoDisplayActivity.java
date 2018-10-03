package com.example.kallyruan.eldermap.P2PPkg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.example.kallyruan.eldermap.R;

public class VideoDisplayActivity extends Activity {
    public final int RESULT_LOAD_IMG = 1;
    private static String dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2p_video);

        VideoView video = findViewById(R.id.video_play);
        video.setVideoPath(dir);
        video.start();
    }

    public void cancelVideo(View view){
        finish();
    }

    public static void setDir(String path) {
        dir = path;
    }
}
