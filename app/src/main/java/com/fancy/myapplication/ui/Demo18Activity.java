package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.fancy.myapplication.R;
import com.fancy.myapplication.bean.Person;
import com.fancy.myapplication.bean.Student;
import com.fancy.myapplication.observable.Observable;
import com.fancy.myapplication.observable.Observer;

/**
 * @author pengkuanwang
 * @date 2019-07-15
 */
public class Demo18Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo18);
        Observable<Student> observable = new Observable<Student>() {
            @Override
            protected void subscribeActual(Observer<? super Student> observer) {
                observer.onNext(new Person("pkw222", 34));
            }
        };
        Observer<Student> observer = new Observer<Student>() {
            @Override
            public void onNext(Student student) {
                Toast.makeText(Demo18Activity.this, student.name, Toast.LENGTH_SHORT).show();
            }
        };
        observable.subscribe(observer);
    }
}
