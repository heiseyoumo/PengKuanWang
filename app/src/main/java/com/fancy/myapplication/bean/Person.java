package com.fancy.myapplication.bean;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class Person extends Student {
    private double height;
    private double weight;

    public Person(String name, int age) {
        super(name, age);
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
