package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fancy.myapplication.R;
import com.fancy.myapplication.bean.Person;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author pengkuanwang
 * @date 2019-07-15
 */
public class Demo17Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        final Observable<Person> observable = new Observable<Person>() {
            @Override
            protected void subscribeActual(Observer<? super Person> observer) {
                observer.onNext(new Person("pkw", 28));
            }
        };
        final Observer<Person> observer = new Observer<Person>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Person person) {
                Toast.makeText(Demo17Activity.this, person.name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observable.subscribe(observer);
            }
        });
    }
}
