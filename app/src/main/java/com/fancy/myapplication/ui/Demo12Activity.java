package com.fancy.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fancy.myapplication.R;

import org.greenrobot.eventbus.EventBus;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class Demo12Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo12);
        EventBus.getDefault().register(this);
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Demo12Activity.this, Demo13Activity.class));
            }
        });
    }
}
