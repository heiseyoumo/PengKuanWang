package com.fancy.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fancy.myapplication.R;
import com.fancy.myapplication.bean.Person;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.annotation.Annotation;

import butterknife.BindView;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class Demo12Activity extends Activity {
    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo12);
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person("pkw", 23);
                Class<? extends Person> aClass = Person.class;
                Annotation[] annotations = aClass.getAnnotations();
                for (Annotation annotation : annotations) {
                    Toast.makeText(Demo12Activity.this, annotation.toString(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(Demo12Activity.this, "annotations.length:" + annotations.length, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void test() {

    }
}
