package com.fancy.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * @author pengkuanwang
 * @date 2019-07-11
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
