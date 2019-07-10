package com.fancy.myapplication.ui;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * @author pengkuanwang
 * @date 2019-07-09
 */
public class MyService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public MyService() {
        super("haha");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action=intent.getStringExtra("action");
        SystemClock.sleep(300);
        Log.d("MyService", action);
    }

    @Override
    public void onDestroy() {
        Log.d("MyService", "onDestroy");
        super.onDestroy();
    }
}
