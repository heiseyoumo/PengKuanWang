package com.fancy.myapplication.util;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.observable.ObservableRepeatWhen;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * @author pengkuanwang
 * @date 2019-07-21
 */
public class Obverable1111<T> implements ObservableSource{

    public final Observable<T> repeatWhen(Function<? super Observable<Object>, ? extends ObservableSource<?>> handler) {
        ObjectHelper.requireNonNull(handler, "handler is null");
        return RxJavaPlugins.onAssembly(new ObservableRepeatWhen<T>(this, handler));
    }

    @Override
    public void subscribe(Observer observer) {

    }
}
