package com.fancy.myapplication.observable;

/**
 * @author pengkuanwang
 * @date 2019-07-15
 */
public interface ObservableSource<T> {

    void subscribe(Observer<? super T> observer);
}
