package com.fancy.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author pengkuanwang
 * @date 2019-07-10
 */
public class User implements Parcelable {
    public int userId;
    public String userName;
    public boolean isMale;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User() {
    }

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    protected User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt() == 1;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeInt(isMale ? 1 : 0);
    }
}
