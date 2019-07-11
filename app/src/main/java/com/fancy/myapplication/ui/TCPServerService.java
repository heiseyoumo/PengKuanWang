package com.fancy.myapplication.ui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author pengkuanwang
 * @date 2019-07-10
 */
public class TCPServerService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
