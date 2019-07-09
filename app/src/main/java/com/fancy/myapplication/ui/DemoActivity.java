package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fancy.myapplication.R;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pengkuanwang
 * @date 2019-07-09
 */
public class DemoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo11);
        Button button = findViewById(R.id.button);
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 70, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(128), new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            private final AtomicInteger threadNumber1 = new AtomicInteger(1);

            @Override
            public Thread newThread(@NonNull Runnable runnable) {
                Thread thread = new Thread(runnable, "王丽娟" + threadNumber1.getAndIncrement());
                Log.d("DemoActivity", "创建线程" + threadNumber.getAndIncrement());
                return thread;
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                Log.d("DemoActivity", "拒绝执行");
            }
        });
        for (int i = 0; i < 198; i++) {
            Log.d("DemoActivity", i + "");
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                        Log.d("DemoActivity", Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPoolExecutor.shutdown();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 3; i++) {
                    threadPoolExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                Log.d("DemoActivity", Thread.currentThread().getName());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    class MyAsynctask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
