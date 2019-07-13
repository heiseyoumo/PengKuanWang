package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fancy.myapplication.R;
import com.fancy.myapplication.bean.Person;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class Demo11Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Person person = new Person();
                    Method setHeight = person.getClass().getDeclaredMethod("setHeight", Double.class);
                    setHeight.invoke(person, 234);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class<Person> personClass = Person.class;
                    Method method = personClass.getDeclaredMethod("setName", String.class);
                    Field[] declaredFields = personClass.getDeclaredFields();
                    for (int i = 0, length = declaredFields.length; i < length; i++) {
                        Field declaredField = declaredFields[i];
                        String name = declaredField.getName();
                        Log.d("Demo11Activity", name);
                    }
                    Field weight = personClass.getDeclaredField("weight");
                    Class<?> type = weight.getType();
                    method.invoke(personClass.newInstance(), "hhh");
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
