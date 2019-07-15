package com.fancy.myapplication.observable;

/**
 * @author pengkuanwang
 * @date 2019-07-15
 */
public interface Observer<T> {
    void onNext(T t);
}
