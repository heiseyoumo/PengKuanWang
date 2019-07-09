package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

import com.fancy.myapplication.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pengkuanwang
 * @date 2019-07-09
 */
public class Demo1Activity extends Activity {
    Button button;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo11);
        button = findViewById(R.id.button);
        final ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("Demo1Activity", Thread.currentThread().getName());
                }
            });
        }
        executorService.shutdown();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (executorService.isTerminated()) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
