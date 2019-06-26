package com.fancy.myapplication;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

/**
 * @author pengkuanwang
 * @date 2019-06-25
 */
public class Demo5Activity extends Activity {
    CustomVideoView videoView;
    private String mVideoUriPath = "http://oleeed73x.bkt.clouddn.com/me.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo5);
        videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse(mVideoUriPath));
        videoView.setStateListener(new CustomVideoView.StateListener() {
            @Override
            public void changeVolumn(float detlaY) {

            }

            @Override
            public void changeBrightness(float detlaX) {

            }

            @Override
            public void hideHint() {

            }
        });
    }
}
