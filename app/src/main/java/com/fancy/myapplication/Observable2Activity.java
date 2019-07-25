package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fancy.myapplication.bean.Person;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author pengkuanwang
 * @date 2019-07-21
 */
public class Observable2Activity extends Activity {
    String TAG = "Observable2Activity";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo11);
        button = findViewById(R.id.button);
        final Observable<Person> observable = Observable.create(new ObservableOnSubscribe<Person>() {
            @Override
            public void subscribe(ObservableEmitter<Person> e) throws Exception {
                String name = Thread.currentThread().getName();
                boolean b = Looper.myLooper() == Looper.getMainLooper();
                button.setText(name);
                e.onNext(new Person("name", 28));
            }
        });
        final Observable observable1 = observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        findViewById(R.id.imageView1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observable1.subscribe(new Observer<Person>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        String name = Thread.currentThread().getName();
                        Log.d(TAG, "observer:" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Person person) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
