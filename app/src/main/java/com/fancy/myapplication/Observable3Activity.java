package com.fancy.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;

/**
 * @author pengkuanwang
 * @date 2019-07-23
 */
public class Observable3Activity extends AbsBaseActivity {
    @BindView(R.id.button)
    Button button;

    @Override
    protected void init() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Observable3Activity.this, "HAHA", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getResLayout() {
        return R.layout.demo3;
    }
}
