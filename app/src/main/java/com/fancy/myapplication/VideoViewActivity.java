package com.fancy.myapplication;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.warkiz.widget.IndicatorSeekBar;

import java.lang.ref.WeakReference;

/**
 * @author pengkuanwang
 * @date 2019-06-22
 */
public class VideoViewActivity extends Activity {
    public String videoUrl;
    CustomVideoView mVideoView;
    ImageView imageView;
    ImageView titleImg;
    ImageView changeScreenImg;
    RelativeLayout rlContainer;
    ImageView coverImg;
    TextView playTimeTv;
    TextView totalTimeTv;
    IndicatorSeekBar seekBar;
    ProgressBar progressBar;
    LinearLayout timeLayout;
    public static final int BTN_GONE = 101;
    public static final int FORMAT_VIDEO_TIME = 102;
    MyHandler mHandler;
    private boolean isVerticalScreen = true;
    boolean flag = false;
    int old_duration;

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
                case FORMAT_VIDEO_TIME:
                    /**
                     * 获取当前播放的时间和当前食品的长度
                     */
                    int currentPosition = activity.mVideoView.getCurrentPosition();
                    double playPercent = currentPosition * 100.00 / activity.mVideoView.getDuration() * 1.0;
                    activity.seekBar.setProgress((int) playPercent);
                    String formatTime = activity.formatTime(currentPosition);
                    activity.playTimeTv.setText(formatTime);
                    int duration = activity.mVideoView.getCurrentPosition();
                    if (activity.old_duration == duration && activity.mVideoView.isPlaying()) {
                        if (activity.progressBar.getVisibility() == View.GONE) {
                            activity.progressBar.setVisibility(View.VISIBLE);
                        }
                        Log.d("VideoViewActivity", "播放状态卡顿");
                    } else {
                        if (activity.progressBar.getVisibility() == View.VISIBLE) {
                            activity.progressBar.setVisibility(View.GONE);
                        }
                        Log.d("VideoViewActivity", "播放状态顺畅");
                    }
                    activity.old_duration = duration;
                    activity.mHandler.sendEmptyMessageDelayed(FORMAT_VIDEO_TIME, 1000);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        videoUrl = getIntent().getStringExtra("url");
        mVideoView = findViewById(R.id.videoView);
        imageView = findViewById(R.id.imageView);
        titleImg = findViewById(R.id.titleImg);
        changeScreenImg = findViewById(R.id.changeScreenImg);
        rlContainer = findViewById(R.id.rlContainer);
        coverImg = findViewById(R.id.coverImg);
        totalTimeTv = findViewById(R.id.totalTimeTv);
        playTimeTv = findViewById(R.id.playTimeTv);
        seekBar = findViewById(R.id.indicator);
        progressBar = findViewById(R.id.progressBar);
        timeLayout = findViewById(R.id.timeLayout);
        mHandler = new MyHandler(this);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                timeLayout.setVisibility(View.VISIBLE);
                coverImg.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                totalTimeTv.setText(formatTime(mVideoView.getDuration()));
                mHandler.sendEmptyMessage(FORMAT_VIDEO_TIME);
                mp.start();
            }
        });
        setOnClickListener();
        mVideoView.setVideoURI(Uri.parse(videoUrl));
        imageView.setVisibility(View.GONE);
        DownLoadManager.getInstance().loadImage(videoUrl, new DownLoadManager.LoadBitmapListener() {
            @Override
            public void setBitmap(final Bitmap bitmap) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    /**
     * 设置按钮的监听事件
     */
    private void setOnClickListener() {
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(VideoViewActivity.this, "播放完成", Toast.LENGTH_SHORT).show();
            }
        });
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(VideoViewActivity.this, "播放失败", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        seekBar.setOnSeekChangeListener(new IndicatorSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
                if (flag) {
                    long duration = mVideoView.getDuration();
                    long newPosition = (duration * progress) / 100L;
                    mVideoView.seekTo((int) newPosition);
                    Log.d("VideoViewActivity", "newPosition=" + newPosition);
                }
            }

            @Override
            public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String tickBelowText, boolean fromUserTouch) {

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {
                flag = true;
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
                flag = false;
            }
        });
        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressBar.getVisibility() == View.VISIBLE) {
                    if (imageView.getVisibility() == View.GONE) {
                        imageView.setVisibility(View.VISIBLE);
                    } else {
                        imageView.setVisibility(View.GONE);
                    }
                    mHandler.removeMessages(BTN_GONE);
                    mHandler.sendEmptyMessageDelayed(BTN_GONE, 2000);
                }
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
        mHandler.removeCallbacksAndMessages(null);
        mVideoView = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            isVerticalScreen = true;
            changeScreenImg.setVisibility(View.VISIBLE);
            titleImg.setImageResource(R.drawable.close_video_icon);
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.dipToPx(this, 250));
        } else {
            //横屏
            isVerticalScreen = false;
            changeScreenImg.setVisibility(View.GONE);
            titleImg.setImageResource(R.drawable.back_video_icon);
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onBackPressed() {
        if (isVerticalScreen) {
            finish();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * 将视频播放的时间格式化
     *
     * @param time
     * @return
     */
    public String formatTime(int time) {
        int i = time / 1000;
        int hour = i / (60 * 60);
        int minute = i / 60 % 60;
        int second = i % 60;
        return String.format("%02d:%02d:%02d", hour, minute, second);
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
