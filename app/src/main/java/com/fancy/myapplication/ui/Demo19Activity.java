package com.fancy.myapplication.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.fancy.myapplication.R;

/**
 * @author pengkuanwang
 * @date 2019-07-16
 */
public class Demo19Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo19);
        Toast.makeText(this, "getTaskId():" + getTaskId(), Toast.LENGTH_SHORT).show();
        findViewById(R.id.layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Demo19Activity.this, "我是layout", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Demo19Activity.this,Demo20Activity.class));
                Toast.makeText(Demo19Activity.this, "我是button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
