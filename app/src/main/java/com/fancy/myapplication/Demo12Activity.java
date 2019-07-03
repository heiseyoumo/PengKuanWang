package com.fancy.myapplication;

import android.app.Activity;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;

/**
 * @author pengkuanwang
 * @date 2019-07-03
 */
public class Demo12Activity extends Activity {
    private MediaPlayer player;
    private SurfaceHolder holder;
    ProgressBar progressBar;
    SurfaceView surfaceView;
    String remoteUrl = "https://m.changyoyo.com/video/baiguoyuan.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo12);
        ClassLoader classLoader = getClassLoader();
        Resources resources = getResources();
        surfaceView = findViewById(R.id.surfaceView);
        progressBar = findViewById(R.id.progressBar);
        player = new MediaPlayer();
        try {
            player.setDataSource(this, Uri.parse(remoteUrl));
            holder = surfaceView.getHolder();
            holder.addCallback(new MyCallBack());
            player.prepare();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.INVISIBLE);
                    player.start();
                    player.setLooping(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        player.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        player.pause();
        super.onPause();
    }

    private class MyCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }
}
