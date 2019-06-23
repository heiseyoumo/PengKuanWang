package com.fancy.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

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
public class VideoActivity extends Activity {
    private ProgressDialog progressDialog = null;
    VideoView mVideoView;
    TextView tvCache;
    public String remoteUrl = "https://mov.bn.netease.com/open-movie/nos/mp4/2017/12/04/SD3SUEFFQ_hd.mp4";
    private final static int VIDEO_STATE_UPDATE = 0;
    private final static int CACHE_VIDEO_READY = 1;
    private final static int CACHE_VIDEO_UPDATE = 2;
    private final static int CACHE_VIDEO_END = 3;

    private static final int READY_BUFF = 2000 * 1024;
    private static final int CACHE_BUFF = 500 * 1024;

    private boolean isready = false;
    private boolean iserror = false;
    private int errorCnt = 0;
    private int curPosition = 0;
    private long mediaLength = 0;
    private long readSize = 0;
    public String localUrl = "/storage/emulated/0/VideoCache/1561194009263.mp4";
    public String localUrl1;


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case VIDEO_STATE_UPDATE:
                    double cachePercent = readSize * 100.00 / mediaLength * 1.0;
                    String s = String.format("已缓存: [%.2f%%]", cachePercent);

                    if (mVideoView.isPlaying()) {
                        curPosition = mVideoView.getCurrentPosition();
                        int duration = mVideoView.getDuration();
                        duration = duration == 0 ? 1 : duration;

                        double playpercent = curPosition * 100.00 / duration * 1.0;

                        int i = curPosition / 1000;
                        int hour = i / (60 * 60);
                        int minute = i / 60 % 60;
                        int second = i % 60;

                        s += String.format(" 播放: %02d:%02d:%02d [%.2f%%]", hour,
                                minute, second, playpercent);
                    }

                    tvCache.setText(s);

                    mHandler.sendEmptyMessageDelayed(VIDEO_STATE_UPDATE, 1000);
                    break;

                case CACHE_VIDEO_READY:
                    isready = true;
                    mVideoView.setVideoPath(localUrl);
                    mVideoView.start();
                    break;

                case CACHE_VIDEO_UPDATE:
                    if (iserror) {
                        mVideoView.setVideoPath(localUrl);
                        mVideoView.start();
                        iserror = false;
                    }
                    break;

                case CACHE_VIDEO_END:
                    if (iserror) {
                        mVideoView.setVideoPath(localUrl);
                        mVideoView.start();
                        iserror = false;
                    }
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
        setContentView(R.layout.activity_video);
        mVideoView = findViewById(R.id.videoView);
        tvCache = findViewById(R.id.tvCache);
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaplayer) {
                mVideoView.seekTo(curPosition);
                mediaplayer.start();
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaplayer) {
                curPosition = 0;
                mVideoView.pause();
            }
        });

        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mediaplayer, int i, int j) {
                dismissProgressDialog();
                iserror = true;
                errorCnt++;
                mVideoView.pause();
                return true;
            }
        });
        ActivityCompat.requestPermissions(VideoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length == 2) {
                playVideo();
            }
        }
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
                    progressDialog = ProgressDialog.show(VideoActivity.this,
                            "视频缓存", "正在努力加载中 ...", true, false);
                }
            }
        });
    }

    private void playVideo() {
        if (!URLUtil.isNetworkUrl(this.remoteUrl)) {
            mVideoView.setVideoPath(this.remoteUrl);
            mVideoView.start();
            return;
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                FileOutputStream out = null;
                InputStream is = null;

                try {
                    URL url = new URL(remoteUrl);
                    HttpURLConnection httpConnection = (HttpURLConnection) url
                            .openConnection();

                    if (localUrl == null) {
                        localUrl = Environment.getExternalStorageDirectory()
                                .getAbsolutePath()
                                + "/VideoCache/"
                                + System.currentTimeMillis() + ".mp4";
                    }

                    System.out.println("localUrl: " + localUrl);

                    File cacheFile = new File(localUrl);

                    if (!cacheFile.exists()) {
                        cacheFile.getParentFile().mkdirs();
                        cacheFile.createNewFile();
                    }

                    readSize = cacheFile.length();
                    out = new FileOutputStream(cacheFile, true);

                    httpConnection.setRequestProperty("User-Agent", "NetFox");
                    httpConnection.setRequestProperty("RANGE", "bytes="
                            + readSize + "-");

                    is = httpConnection.getInputStream();

                    mediaLength = httpConnection.getContentLength();
                    if (mediaLength == -1) {
                        return;
                    }

                    mediaLength += readSize;

                    byte buf[] = new byte[4 * 1024];
                    int size = 0;
                    long lastReadSize = 0;

                    mHandler.sendEmptyMessage(VIDEO_STATE_UPDATE);

                    while ((size = is.read(buf)) != -1) {
                        try {
                            out.write(buf, 0, size);
                            readSize += size;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (!isready) {
                            if ((readSize - lastReadSize) > READY_BUFF) {
                                lastReadSize = readSize;
                                mHandler.sendEmptyMessage(CACHE_VIDEO_READY);
                            }
                        } else {
                            if ((readSize - lastReadSize) > CACHE_BUFF
                                    * (errorCnt + 1)) {
                                lastReadSize = readSize;
                                mHandler.sendEmptyMessage(CACHE_VIDEO_UPDATE);
                            }
                        }
                    }

                    mHandler.sendEmptyMessage(CACHE_VIDEO_END);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            //
                        }
                    }

                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            //
                        }
                    }
                }

            }
        }).start();

    }
}
