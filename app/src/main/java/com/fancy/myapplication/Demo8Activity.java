package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author pengkuanwang
 * @date 2019-07-01
 */
public class Demo8Activity extends Activity {
    OkHttpClient okHttpClient;
    private static final int CONNECT_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 300;
    private static final int READ_TIMEOUT = 30;
    String url = "http://c.3g.163.com/photo/api/set/0001%2F2250173.json";
    TextView textView;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo8);
        textView = findViewById(R.id.textView);
        final Handler handler = new Handler();
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient = builder.build();
        RequestBody requestBody = appendParams(new HashMap<String, Object>());
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("User-Agent", "a")
                .build();
        final Call call = okHttpClient.newCall(request);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("Demo8Activity", count++ + "");
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String result = response.body().string();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(result);
                                }
                            });
                        }
                    });
                }
            }
        });
        LinkedBlockingDeque<Runnable> linkedBlockingDeque = new LinkedBlockingDeque<>();
    }


    public RequestBody appendParams(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params == null || params.isEmpty()) {
            return builder.build();
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue().toString());
        }
        return builder.build();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
