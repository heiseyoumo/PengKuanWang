package com.fancy.myapplication.bean;

import android.util.Log;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class Student {
    public String name;
    public int age;

    public Student() {

    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void student1() {
        Log.d("Student", "Student1");
    }

    public void student2() {
        Log.d("Student", "Student2");
    }
}
