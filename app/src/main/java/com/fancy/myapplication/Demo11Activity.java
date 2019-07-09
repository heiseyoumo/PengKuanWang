package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.fancy.myapplication.util.Api;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * @author pengkuanwang
 * @date 2019-07-08
 */
public class Demo11Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo8);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api.getDefault().login(new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return null;
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {

                    }
                });
            }
        });
    }
}
