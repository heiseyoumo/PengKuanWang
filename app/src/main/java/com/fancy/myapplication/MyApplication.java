package com.fancy.myapplication;

import android.app.Application;
import android.content.Context;

import com.fancy.myapplication.processor.OkHttpProcessor;
import com.fancy.myapplication.ui.HttpHelper;

/**
 * @author pengkuanwang
 * @date 2019-07-11
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HttpHelper.getInstance().initProcessor(new OkHttpProcessor());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
