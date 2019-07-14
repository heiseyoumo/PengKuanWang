package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.fancy.myapplication.R;
import com.fancy.myapplication.bean.Person;

import java.lang.annotation.Annotation;

/**
 * @author pengkuanwang
 * @date 2019-07-14
 */
public class Demo15Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        Person person = new Person("name", 23);
        Class<? extends Person> aClass = person.getClass();
        Annotation[] annotations = aClass.getAnnotations();
        Toast.makeText(this, "annotations.length:" + annotations.length, Toast.LENGTH_SHORT).show();
    }
}
