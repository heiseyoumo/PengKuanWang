package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;

import com.fancy.myapplication.presenter.BasePresenter;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends Activity {
    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attach((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    /**
     * 创建p成
     *
     * @return
     */
    protected abstract T createPresenter();
}
