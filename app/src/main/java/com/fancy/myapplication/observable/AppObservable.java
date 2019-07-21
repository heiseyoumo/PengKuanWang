package com.fancy.myapplication.observable;

import java.util.ArrayList;

/**
 * @author pengkuanwang
 * @date 2019-07-15
 */
public abstract class AppObservable<T> implements ObservableSource<T> {
    private ArrayList<Observer<T>> list;

    public void register(Observer observer) {
        if (list == null) {
            list = new ArrayList<>();
        }
        if (!list.contains(observer)) {
            list.add(observer);
        }
    }

    public void unregister(Observer observer) {
        if (list != null && list.contains(observer)) {
            list.remove(observer);
        }
    }

    @Override
    public void subscribe(Observer<? super T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<? super T> observer);
}
