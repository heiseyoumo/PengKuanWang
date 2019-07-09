package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.fancy.myapplication.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(3);
        //ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(128));
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPoolExecutor.shutdown();
        while (true) {
            if (threadPoolExecutor.isTerminated()) {
                button.setText("执行结束了");
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
