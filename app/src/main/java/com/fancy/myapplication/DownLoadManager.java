package com.fancy.myapplication;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;

import java.util.HashMap;

/**
 * @author pengkuanwang
 * @date 2019-06-26
 */
public class DownLoadManager {
    private DownLoadManager() {
    }

    public static class InnerObject {
        private static DownLoadManager downLoadManager = new DownLoadManager();
    }

    public static DownLoadManager getInstance() {
        return InnerObject.downLoadManager;
    }

    public interface LoadBitmapListener {
        /**
         * 设置Bitmap
         *
         * @param bitmap
         */
        void setBitmap(Bitmap bitmap);
    }

    public void loadImage(final String videoUrl, final LoadBitmapListener loadBitmapListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap;
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                //根据网络视频的url获取第一帧--亲测可用。但是这个方法获取本地视频的第一帧，不可用，还没找到方法解决。
                if (Build.VERSION.SDK_INT >= 14) {
                    retriever.setDataSource(videoUrl, new HashMap<String, String>());
                } else {
                    retriever.setDataSource(videoUrl);
                }
                //获得第一帧图片
                bitmap = retriever.getFrameAtTime();
                loadBitmapListener.setBitmap(bitmap);
            }
        }).start();
    }
}
