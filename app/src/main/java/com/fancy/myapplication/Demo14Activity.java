package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.Random;

/**
 * @author pengkuanwang
 * @date 2019-07-04
 */
public class Demo14Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo14);
        ThreadGroup threadGroup = new ThreadGroup("group");
        for (int i = 0; i < 10; i++) {
            new Thread(threadGroup, new MyRunnable(), "thread").start();
        }
        while (threadGroup.activeCount() > 1) {
            Log.d("Demo14Activity", "df" + threadGroup.activeCount());
        }
        finish();
    }

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            int times = new Random().nextInt(10);
            while (times-- > 0) {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
