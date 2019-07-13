package com.fancy.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author pengkuanwang
 * @date 2019-07-04
 */
public class Person11 implements Parcelable {
    public String name;
    int age;

    public Person11(){

    }
    public Person11(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person11(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Person11> CREATOR = new Creator<Person11>() {
        @Override
        public Person11 createFromParcel(Parcel in) {
            return new Person11(in);
        }

        @Override
        public Person11[] newArray(int size) {
            return new Person11[size];
        }
    };
}
