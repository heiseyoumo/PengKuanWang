package com.fancy.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * @author pengkuanwang
 * @date 2019-07-09
 */
public class MyService extends Service {
    public static final String TAG = "MyService";

    class MyBinder extends Binder {
        public void getName() {
        }
    }

    @Override
    public void onCreate() {
        log("onCreate");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        log("onBind");
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("onStartCommand");
        return Service.START_NOT_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        log("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        log("onDestroy");
        super.onDestroy();
    }

    public void log(String message) {
        Log.d(TAG, message);
    }
}
