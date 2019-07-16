package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.fancy.myapplication.R;
import com.fancy.myapplication.view.MyButton;
import com.fancy.myapplication.view.MyLayout;

/**
 * @author pengkuanwang
 * @date 2019-07-16
 */
public class Demo19Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo19);
        MyButton button = findViewById(R.id.button);
        MyLayout layout = findViewById(R.id.layout);
        View decorView = getWindow().getDecorView();
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Demo19Activity.this, "我是layout", Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Demo19Activity.this, "我是button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
