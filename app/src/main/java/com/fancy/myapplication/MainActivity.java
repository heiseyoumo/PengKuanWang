package com.fancy.myapplication;

import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String A = "A";
    private static final String B = "B";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HandlerThread handlerThread=new HandlerThread("hehe");
        handlerThread.start();



        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeadLockDemo().deadLock();
            }
        });
    }

    class DeadLockDemo {
        private void deadLock() {
            Thread t1 = new Thread(new Runnable() {

                @Override
                public void run() {
                    synchronized (A) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (B) {
                            Log.d("DeadLockDemo", "DeadLockDemo11111");
                        }
                    }
                }
            });
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (B) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (A) {
                            Log.d("DeadLockDemo", "DeadLockDemo11111");
                        }
                    }
                }
            });
            t1.start();
            t2.start();
        }
    }
}
