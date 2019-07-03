package com.fancy.myapplication.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author pengkuanwang
 * @date 2019-07-03
 */
public class MemoryCacheUtil {
    private LruCache<String, Bitmap> mLruCache;
    private Handler handler = new Handler(Looper.getMainLooper());
    ThreadPoolExecutor threadPoolExecutor;
    private volatile static MemoryCacheUtil instance;

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
     * 创建一个线程池以便于用来下载图片
     *
     * @param videoUrl
     * @param onBitmapListener
     */
    private void loadBitmapFormNet(final String videoUrl, final OnBitmapListener onBitmapListener) {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(3, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        }
        threadPoolExecutor.execute(new Runnable() {
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
                    final Bitmap frameBitmap = retriever.getFrameAtTime();
                    mLruCache.put(videoUrl, frameBitmap);
                    setBitmapToLocal(videoUrl, frameBitmap);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (onBitmapListener != null) {
                                onBitmapListener.getBitmap(frameBitmap);
                            }
                        }
                    });
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    retriever.release();
                }
            }
        });
    }

    /**
     * 图片存储到本地
     *
     * @param url
     * @param frameBitmap
     */
    private void setBitmapToLocal(String url, Bitmap frameBitmap) {
        File file = getFilePathByUrl(url);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            // 如果文件不存在，则创建文件夹
            parentFile.mkdirs();
        }
        try {
            if (frameBitmap != null) {
                frameBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    /**
     * 从本地获取图片
     *
     * @param url
     * @return
     */
    private Bitmap getBitmapFromLocal(String url) {
        File filePathByUrl = getFilePathByUrl(url);
        Bitmap bitmap = null;
        try {
            if (filePathByUrl.exists()) {
                // 如果文件存在
                bitmap = BitmapFactory.decodeStream(new FileInputStream(filePathByUrl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 根据url获取缓存的文件
     *
     * @param url
     * @return
     */
    private File getFilePathByUrl(String url) {
        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/com.fancy.myapplication/video";
        int indexOf = url.lastIndexOf("/");
        int lastIndexOf = url.lastIndexOf(".");
        String cacheUrl = url.substring(indexOf + 1, lastIndexOf);
        File file = new File(path, cacheUrl);
        return file;
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
