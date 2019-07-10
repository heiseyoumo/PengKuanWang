package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;

import com.fancy.myapplication.R;

/**
 * @author pengkuanwang
 * @date 2019-07-10
 */
public class Demo6Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo11);
        Looper.getMainLooper();
        Button button = findViewById(R.id.button);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Handler h = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 23:
                                Log.d("Demo6Activity", Thread.currentThread().getName());
                                break;
                            default:
                                break;
                        }
                        super.handleMessage(msg);
                    }
                };
                Message message=Message.obtain();
                h.sendEmptyMessage(23);
                Looper.loop();
            }
        }).start();
        ClassLoader classLoader = getClassLoader();
    }
}
