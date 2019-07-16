package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fancy.myapplication.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author pengkuanwang
 * @date 2019-07-16
 */
public class Demo22Activity extends Activity {
    public static final String TAG = "Demo22Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo22);
        // RxJava的链式操作
        final Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 创建被观察者(Observable) & 定义需发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                //emitter.setDisposable(DisposableHelper.DISPOSED);
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        final Observer<Integer> observer = new Observer<Integer>() {
            // 2. 创建观察者(Observer) & 定义响应事件的行为
            // 3. 通过订阅（subscribe）连接观察者和被观察者
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "开始采用subscribe连接");
            }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "对Next事件" + value + "作出响应");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        };
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observable.subscribe(observer);
            }
        });
    }
}
