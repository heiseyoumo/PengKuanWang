package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fancy.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author pengkuanwang
 * @date 2019-07-09
 */
public class Demo3Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo11);
        int CPU_COUNT = Runtime.getRuntime().availableProcessors();
        Toast.makeText(this, "CPU_COUNT:" + CPU_COUNT, Toast.LENGTH_SHORT).show();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 5; i++) {
                    new MyTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
                }
            }
        });

        HandlerThread handlerThread=new HandlerThread("pkw");
        handlerThread.start();
    }

    class MyTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Log.d("Demo3Activity", simpleDateFormat.format(new Date()));
            super.onPostExecute(aVoid);
        }
    }
}
