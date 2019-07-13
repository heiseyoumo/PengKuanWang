package com.fancy.myapplication.bean;

import android.util.Log;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class Person extends Student {
    public double height;
    private double weight;

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Person() {
    }

    public Person(String name, int age) {
        super(name, age);
    }

    @Override
    public void student1() {
        super.student1();
    }

    public void person1() {

    }

    private String getName() {
        return "小明";
    }

    private void setName(String name) {
        Log.d("Person", name);
    }
}
