package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fancy.myapplication.R;
import com.fancy.myapplication.util.DM;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class Demo10Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        Class<DM> dmClass = DM.class;
        try {
            Class<?> aClass = Class.forName("com.fancy.myapplication.util.DM");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        findViewById(R.id.textView1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassLoader classLoader = getClass().getClassLoader();
                try {
                    Class<?> aClass = classLoader.loadClass("com.fancy.myapplication.util.DM");
                    Method[] declaredMethods = aClass.getDeclaredMethods();
                    for (int i = 0, length = declaredMethods.length; i < length; i++) {
                        Method declaredMethod = declaredMethods[i];
                        String toGenericString = declaredMethod.toGenericString();
                        String toString = declaredMethod.toString();
                    }
                    Toast.makeText(Demo10Activity.this, "declaredMethods.length:" + declaredMethods.length, Toast.LENGTH_SHORT).show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DM dm = new DM();
                Class<? extends DM> aClass = dm.getClass();
                Method[] declaredMethods = aClass.getDeclaredMethods();
                for (int i = 0, length = declaredMethods.length; i < length; i++) {
                    Method declaredMethod = declaredMethods[i];
                    String toGenericString = declaredMethod.toGenericString();
                    String toString = declaredMethod.toString();
                }
                Toast.makeText(Demo10Activity.this, "declaredMethods.length:" + declaredMethods.length, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DM dm = new DM();
                    Class<? extends DM> aClass = dm.getClass();
                    Method method = aClass.getDeclaredMethod("dpToPx", float.class);
                    method.invoke(dm, 23);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        });
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<DM> dmClass = DM.class;
                Method[] declaredMethods = dmClass.getDeclaredMethods();
                for (int i = 0, length = declaredMethods.length; i < length; i++) {
                    Method declaredMethod = declaredMethods[i];
                    String toGenericString = declaredMethod.toGenericString();
                    String toString = declaredMethod.toString();
                }
                Toast.makeText(Demo10Activity.this, "declaredMethods.length:" + declaredMethods.length, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
