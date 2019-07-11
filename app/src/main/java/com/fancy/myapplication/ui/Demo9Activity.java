package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fancy.myapplication.R;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author pengkuanwang
 * @date 2019-07-11
 */
public class Demo9Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo15);
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 100, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5), new ThreadFactory() {
            private final AtomicInteger automic = new AtomicInteger(1);
            private final AtomicInteger automic1 = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "创建的彭宽旺线程:" + automic.getAndIncrement());
                Log.d("Demo9Activity", "创建的彭宽旺线程:" + automic1.getAndIncrement());
                return thread;
            }
        }, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                Log.d("Demo9Activity", "老子拒绝你了");
            }
        });
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d("Demo9Activity", "当前线程为:" + Thread.currentThread().getName());
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    threadPoolExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(200);
                                Log.d("Demo9Activity", Thread.currentThread().getName());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}
