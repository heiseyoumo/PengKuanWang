package com.fancy.myapplication.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * @author pengkuanwang
 * @date 2019-07-03
 */
public class MemoryCacheUtil {
    private LruCache<String, Bitmap> mLruCache;
    private Handler handler = new Handler(Looper.getMainLooper());
    private volatile static MemoryCacheUtil instance;
    private String CACHE_PATH;

    public static MemoryCacheUtil getInstance() {
        if (instance == null) {
            synchronized (MemoryCacheUtil.class) {
                if (instance == null) {
                    instance = new MemoryCacheUtil();
                }
            }
        }
        return instance;
    }

    private MemoryCacheUtil() {
        /**
         * 设置maxMemory 缓存允许的最大值(一般为可用内存的1/8),超过这个最大值，则会回收
         */
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;
        mLruCache = new LruCache<String, Bitmap>((int) maxMemory / 8) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int size = value.getRowBytes() * value.getHeight();
                return size;
            }
        };
    }

    public interface OnBitmapListener {
        /**
         * 将bitmap返回
         *
         * @param bitmap
         */
        void getBitmap(Bitmap bitmap);
    }

    /**
     * 通过URL获取Bitmap
     *
     * @param url
     */
    public void LoadBitmapByUrl(String url, OnBitmapListener onBitmapListener) {
        /**
         * 从缓存中读取
         */
        Bitmap lruCacheBitmap = getBitmapFromMemory(url);
        if (lruCacheBitmap != null) {
            if (onBitmapListener != null) {
                onBitmapListener.getBitmap(lruCacheBitmap);
            }
            return;
        }
        /**
         * 缓存中如果没有那么从本地中读取
         */
        Bitmap bitmapFromLocal = getBitmapFromLocal(url);
        if (bitmapFromLocal != null) {
            if (onBitmapListener != null) {
                onBitmapListener.getBitmap(bitmapFromLocal);
            }
            mLruCache.put(url, bitmapFromLocal);
            return;
        }
        /**
         * 本地如果没有的话那么只能从网络上下载了
         */
        loadBitmapFormNet(url, onBitmapListener);
    }

    /**
     * 根据视频的url获取视频的第一帧作为封面
     */
    private void loadBitmapFormNet(final String videoUrl, final OnBitmapListener onBitmapListener) {
        new Thread(new Runnable() {
            MediaMetadataRetriever retriever;

            @Override
            public void run() {

                try {
                    retriever = new MediaMetadataRetriever();
                    if (Build.VERSION.SDK_INT >= 14) {
                        retriever.setDataSource(videoUrl, new HashMap<String, String>());
                    } else {
                        retriever.setDataSource(videoUrl);
                    }
                    final Bitmap frameAtTime = retriever.getFrameAtTime();
                    mLruCache.put(videoUrl, frameAtTime);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (onBitmapListener != null) {
                                onBitmapListener.getBitmap(frameAtTime);
                            }
                        }
                    });
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    retriever.release();
                }
            }
        }).start();
    }

    /**
     * 图片存储到本地
     *
     * @param url
     */
    private void setBitmapToLocal(String url) {

    }

    /**
     * 从本地获取图片
     *
     * @param url
     * @return
     */
    private Bitmap getBitmapFromLocal(String url) {
        Bitmap bitmap = null;
        try {
            File file = new File(CACHE_PATH);
            if (file.exists()) {
                // 如果文件存在
                bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 通过url从内存中获取图片
     *
     * @param url
     */
    private Bitmap getBitmapFromMemory(String url) {
        Bitmap bitmap = mLruCache.get(url);
        return bitmap;
    }
}
