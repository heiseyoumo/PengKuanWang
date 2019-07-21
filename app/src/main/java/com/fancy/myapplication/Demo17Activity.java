package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * @author pengkuanwang
 * @date 2019-07-17
 */
public class Demo17Activity extends Activity {
    public static String TAG = "DemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo17);
        log("onCreate");
    }

    @Override
    protected void onStart() {
        log("onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        log("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        log("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        log("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        log("onDestroy");
        super.onDestroy();
    }

    public void log(String message) {
        Log.d(TAG, "Demo17Activity:" + message);
    }
}
