package com.fancy.myapplication.presenter;

import java.lang.ref.WeakReference;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public abstract class BasePresenter<V> {

    WeakReference<V> view;

    public void attach(V t) {
        view = new WeakReference<>(t);
    }

    public V getView() {
        if (view != null) {
            V t = view.get();
            return t;
        }
        return null;
    }

    public void detach() {
        if (view != null) {
            view.clear();
            view = null;
        }
    }
}
