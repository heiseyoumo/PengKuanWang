package com.fancy.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author pengkuanwang
 * @date 2019-06-22
 */
public class VideoViewActivity extends Activity {
    public String videoUrl;
    CustomVideoView mVideoView;
    TextView textView;
    ImageView imageView;
    ImageView imageView1;
    public static final int HASH_CACHE = 1000;
    private final static int VIDEO_DOWN_SUCCESS = 1002;
    private final static int VIDEO_DOWN_READY = 1003;
    private final static int VIDEO_STATE_UPDATE = 1004;
    public static final int REQUEST_PERMISSION_CODE = 100;
    private String storageCache;
    private long readSize = 0;
    String cacheVideoUrl;
    private boolean isReady = false;
    private static final int READY_BUFF = 2000 * 1024;
    private int mediaLength;
    private int curPosition = 0;
    public static final int BTN_GONE = 101;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HASH_CACHE:
                    imageView1.setVisibility(View.GONE);
                    mVideoView.setVideoPath(storageCache);
                    mVideoView.start();
                    break;
                case VIDEO_DOWN_READY:
                    imageView1.setVisibility(View.GONE);
                    isReady = !isReady;
                    mVideoView.setVideoPath(storageCache);
                    mVideoView.start();
                    break;
                case VIDEO_DOWN_SUCCESS:
                    Toast.makeText(VideoViewActivity.this, "视频下载成功,请放心观看", Toast.LENGTH_SHORT).show();
                    break;
                case VIDEO_STATE_UPDATE:
                    double cachePercent = readSize * 100.00 / mediaLength * 1.0;
                    String s = String.format("已缓存: [%.2f%%]", cachePercent);

                    if (mVideoView.isPlaying()) {
                        curPosition = mVideoView.getCurrentPosition();
                        int duration = mVideoView.getDuration();
                        duration = duration == 0 ? 1 : duration;

                        double playPercent = curPosition * 100.00 / duration * 1.0;

                        int i = curPosition / 1000;
                        int hour = i / (60 * 60);
                        int minute = i / 60 % 60;
                        int second = i % 60;

                        s += String.format(" 播放: %02d:%02d:%02d [%.2f%%]", hour,
                                minute, second, playPercent);
                        Log.d("VideoViewActivity", s);
                    }
                    mHandler.sendEmptyMessageDelayed(VIDEO_STATE_UPDATE, 1000);
                    break;
                case BTN_GONE:
                    imageView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_view);
        videoUrl = getIntent().getStringExtra("url");
        mVideoView = findViewById(R.id.videoView);
        imageView = findViewById(R.id.imageView);
        imageView1 = findViewById(R.id.imageView1);
        textView = findViewById(R.id.textView);
        int lastIndexOf = videoUrl.lastIndexOf("/");
        cacheVideoUrl = videoUrl.substring(lastIndexOf + 1);
        storageCache = getVideoCache();
        if (isHasCache()) {
            mHandler.sendEmptyMessage(HASH_CACHE);
            Toast.makeText(this, "视频已经缓存不需要流量,请放心观看", Toast.LENGTH_SHORT).show();
            textView.setVisibility(View.VISIBLE);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setVisibility(View.GONE);
                }
            }, 3000);
        } else {
            downloadVideo();
            textView.setVisibility(View.GONE);
        }
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
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
        ActivityCompat.requestPermissions(VideoViewActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
    }

    /**
     * 第一次播放视频的时候先从网上下载到本地
     */
    private void downloadVideo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File cacheFile = new File(getVideoCache());
                FileOutputStream out = null;
                InputStream is = null;
                if (!cacheFile.exists()) {
                    cacheFile.getParentFile().mkdirs();
                    try {
                        cacheFile.createNewFile();
                        URL url = new URL(videoUrl);
                        HttpURLConnection httpConnection = (HttpURLConnection) url
                                .openConnection();
                        out = new FileOutputStream(cacheFile, true);
                        httpConnection.setRequestProperty("User-Agent", "NetFox");
                        httpConnection.setRequestProperty("RANGE", "bytes="
                                + readSize + "-");

                        is = httpConnection.getInputStream();
                        mediaLength = httpConnection.getContentLength();
                        if (mediaLength == -1) {
                            return;
                        }
                        byte buffer[] = new byte[4 * 1024];
                        int size;
                        long lastReadSize = 0;
                        mHandler.sendEmptyMessage(VIDEO_STATE_UPDATE);
                        while ((size = is.read(buffer)) != -1) {
                            out.write(buffer, 0, size);
                            readSize += size;
                            if (!isReady) {
                                if ((readSize - lastReadSize) > READY_BUFF) {
                                    lastReadSize = readSize;
                                    mHandler.sendEmptyMessage(VIDEO_DOWN_READY);
                                }
                            }
                        }
                        mHandler.sendEmptyMessage(VIDEO_DOWN_SUCCESS);
                    } catch (IOException e) {
                        e.printStackTrace();
                        clearVideoCache();
                    } finally {
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length == 2) {
                playVideo();
            }
        }
    }

    private void playVideo() {
        downloadVideo();
        textView.setVisibility(View.GONE);
    }

    /**
     * 获取视频的缓存目录
     *
     * @return
     */
    public String getVideoCache() {
        return Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/" + getPackageName(this) + "/VideoCache/" + cacheVideoUrl;
    }

    /**
     * 清除缓存
     */
    public void clearVideoCache() {
        String videoCache = getVideoCache();
        File file = new File(videoCache);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 判断该视频是否已经被缓存下来了
     *
     * @return
     */
    public boolean isHasCache() {
        File file = new File(getVideoCache());
        return file.exists();
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
