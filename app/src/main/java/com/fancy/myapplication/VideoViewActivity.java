package com.fancy.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * @author pengkuanwang
 * @date 2019-06-22
 */
public class VideoViewActivity extends Activity {
    private ProgressDialog progressDialog = null;
    public String videoUrl;
    CustomVideoView mVideoView;
    ImageView imageView;
    ImageView titleImg;
    ImageView changeScreenImg;
    RelativeLayout rlContainer;
    ImageView coverImg;
    public static final int BTN_GONE = 101;
    MyHandler mHandler;
    private boolean isVerticalScreen = true;

    static class MyHandler extends Handler {
        WeakReference<VideoViewActivity> weakReference;

        public MyHandler(VideoViewActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            VideoViewActivity activity = weakReference.get();
            if (activity == null) {
                return;
            }
            switch (msg.what) {
                case BTN_GONE:
                    activity.imageView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        videoUrl = getIntent().getStringExtra("url");
        mVideoView = findViewById(R.id.videoView);
        imageView = findViewById(R.id.imageView);
        titleImg = findViewById(R.id.titleImg);
        changeScreenImg = findViewById(R.id.changeScreenImg);
        rlContainer = findViewById(R.id.rlContainer);
        coverImg = findViewById(R.id.coverImg);
        mHandler = new MyHandler(this);
        //mVideoView.setMediaController(new MediaController(this));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                coverImg.setVisibility(View.GONE);
                dismissProgressDialog();
                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        /**
                         * 获取当前播放的时间和当前食品的长度
                         */
                        int currentPosition = mVideoView.getCurrentPosition();
                        int bufferPercentage = mVideoView.getBufferPercentage();
                        Log.d("VideoViewActivity", "currentPosition=" + currentPosition + ",bufferPercentage=" + bufferPercentage);
                    }
                });
                mp.start();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(VideoViewActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(VideoViewActivity.this, "播放失败", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        mVideoView.setVideoURI(Uri.parse(videoUrl));
        mHandler.sendEmptyMessageDelayed(BTN_GONE, 2000);

        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getVisibility() == View.GONE) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE);
                }
                mHandler.removeMessages(BTN_GONE);
                mHandler.sendEmptyMessageDelayed(BTN_GONE, 2000);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeMessages(BTN_GONE);
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    imageView.setBackgroundResource(R.drawable.pause_video);
                } else {
                    mVideoView.start();
                    imageView.setBackgroundResource(R.drawable.play_video);
                }
                mHandler.sendEmptyMessageDelayed(BTN_GONE, 2000);
            }
        });
        changeScreenImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });
        titleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVerticalScreen) {
                    finish();
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });
        DownLoadManager.getInstance().loadImage(videoUrl, new DownLoadManager.LoadBitmapListener() {
            @Override
            public void setBitmap(final Bitmap bitmap) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        coverImg.setImageBitmap(bitmap);
                    }
                });
            }
        });
        showProgressDialog();
    }

    private void dismissProgressDialog() {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
        });
    }

    private void showProgressDialog() {
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                if (progressDialog == null) {
                    progressDialog = ProgressDialog.show(VideoViewActivity.this,
                            "视频缓存", "正在努力加载中 ...", true, false);
                }
            }
        });
    }

    int currentPosition;

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("VideoViewActivity", "onResume");
        mVideoView.seekTo(currentPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("VideoViewActivity", "onPause");
        currentPosition = mVideoView.getCurrentPosition();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //clearVideoCache();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            isVerticalScreen = true;
            changeScreenImg.setVisibility(View.VISIBLE);
            titleImg.setBackgroundResource(R.drawable.close_video_icon);
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dipToPx(this, 250));
        } else {
            //横屏
            isVerticalScreen = false;
            changeScreenImg.setVisibility(View.GONE);
            titleImg.setBackgroundResource(R.drawable.back_video_icon);
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    /**
     * 切换尺寸
     *
     * @param width
     * @param height
     */
    public void setVideoViewScale(int width, int height) {

        ViewGroup.LayoutParams videoViewLayoutParams = mVideoView.getLayoutParams();
        videoViewLayoutParams.width = width;
        videoViewLayoutParams.height = height;
        mVideoView.setLayoutParams(videoViewLayoutParams);

        RelativeLayout.LayoutParams rlContainerLayoutParams = (RelativeLayout.LayoutParams) rlContainer.getLayoutParams();
        rlContainerLayoutParams.width = width;
        rlContainerLayoutParams.height = height;
        rlContainer.setLayoutParams(rlContainerLayoutParams);
    }
}
