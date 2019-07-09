package com.fancy.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author pengkuanwang
 * @date 2019-07-09
 */
public class MyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
