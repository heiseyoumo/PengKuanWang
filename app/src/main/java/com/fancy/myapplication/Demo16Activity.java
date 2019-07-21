package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author pengkuanwang
 * @date 2019-07-17
 */
public class Demo16Activity extends Activity {
    public static String TAG = "DemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo16);
        findViewById(R.id.connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.interval(6000, TimeUnit.MILLISECONDS)
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                log(String.valueOf(aLong));
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
        try {
            Class<?> aClass = Class.forName("com.fancy.myapplication.bean.Person");
            if (aClass != null) {
                return;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Toast.makeText(this, "你大爷啊11111", Toast.LENGTH_SHORT).show();
        }
    }

    public void log(String message) {
        Log.d(TAG, "Demo16Activity:" + message);
    }
}
