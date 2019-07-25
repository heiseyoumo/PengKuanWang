package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * @author pengkuanwang
 * @date 2019-07-23
 */
public abstract class AbsBaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResLayout());
        ButterKnife.bind(this);
        init();
    }

    protected abstract void init();

    public abstract int getResLayout();
}
