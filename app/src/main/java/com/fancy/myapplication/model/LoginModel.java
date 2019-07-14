package com.fancy.myapplication.model;

import android.content.Context;

import com.fancy.myapplication.callback.MvpCallBack;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class LoginModel {
    public void login(Context context, String phone, MvpCallBack mvpCallBack) {
        mvpCallBack.onFail("234", "错误信息");
    }
}
