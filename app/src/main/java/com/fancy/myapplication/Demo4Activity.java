package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

/**
 * @author pengkuanwang
 * @date 2019-06-25
 */
public class Demo4Activity extends Activity {
    SuperVideoView videoView;
    private String mVideoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/me.mp4";
    private String mVideoUriPath = "http://oleeed73x.bkt.clouddn.com/me.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo3);
        videoView = findViewById(R.id.container);
        videoView.register(this);
        videoView.setVideoPath(mVideoUriPath);
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.onResume();
    }
}
