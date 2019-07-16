package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.fancy.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author pengkuanwang
 * @date 2019-07-16
 */
public class Demo21Activity extends Activity {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo20);
        int corePoolSize = 2;
        int maximumPoolSize = 2;
        BlockingQueue queue = new ArrayBlockingQueue<Runnable>(2);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                0, TimeUnit.SECONDS, queue);
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 10; i++) {
            final int index = i;
            pool.submit(new Runnable() {

                @Override
                public void run() {
                    log(Thread.currentThread().getName() + "begin run task :" + index);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log(Thread.currentThread().getName() + " finish run  task :" + index);
                }

            });
        }

        log("main thread before sleep!!!");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log("before shutdown()");

        pool.shutdown();

        log("after shutdown(),pool.isTerminated=" + pool.isTerminated());
        try {
            pool.awaitTermination(1000L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log("now,pool.isTerminated=" + pool.isTerminated());
    }

    protected static void log(String string) {
        Log.d(":Demo21Activity", sdf.format(new Date()) + "  " + string);
    }
}
