package com.fancy.myapplication.bean;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class Person {
    private double height;
    private double weight;
    public String name;
    private int age;
    User user;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

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
