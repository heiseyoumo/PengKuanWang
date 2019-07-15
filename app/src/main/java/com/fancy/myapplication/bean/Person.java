package com.fancy.myapplication.bean;

import com.fancy.myapplication.annotation.SuppressWarn;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class Person extends Student {
    private double height;
    private double weight;
    User user;

    public Person(String name, int age) {
        super(name, age);
    }

    @Override
    public void student1() {
        super.student1();
    }

    @SuppressWarn(value = "hello world")
    public void setUser(User user) {
        this.user = user;
    }

    private double getHeight() {
        return height;
    }

    private void setHeight(double height) {
        this.height = height;
    }

    private double getWeight() {
        return weight;
    }

    private void setWeight(double weight) {
        this.weight = weight;
    }
}
