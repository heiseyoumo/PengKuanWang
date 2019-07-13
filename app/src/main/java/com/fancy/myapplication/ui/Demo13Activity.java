package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.fancy.myapplication.R;
import com.fancy.myapplication.event.OrderEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class Demo13Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo23);
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OrderEvent(3));
            }
        });
    }
}
