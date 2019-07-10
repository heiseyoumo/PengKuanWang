package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.fancy.myapplication.R;

/**
 * @author pengkuanwang
 * @date 2019-07-09
 */
public class Demo2Activity extends Activity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo11);

        final ThreadGroup threadGroup = new ThreadGroup("pkw");

        Thread t1 = new MyThread(threadGroup, "t1", 12000);
        Thread t2 = new MyThread(threadGroup, "t2", 1000);
        Thread t3 = new MyThread(threadGroup, "t3", 200);
        t1.start();
        t2.start();
        t3.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (threadGroup.activeCount() <= 1) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("Demo2Activity", "结束");
                            }
                        });
                        break;
                    }
                }
            }
        }).start();
    }

    class MyThread extends Thread {
        long time;

        public MyThread(ThreadGroup group, String name, long time) {
            super(group, name);
            this.time = time;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(time);
                Log.d("Demo2Activity", "执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            super.run();
        }
    }
}
