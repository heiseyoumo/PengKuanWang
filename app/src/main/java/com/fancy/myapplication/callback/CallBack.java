package com.fancy.myapplication.callback;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public interface CallBack {

    void onSuccess(String json);

    void onFail(String errorMsg);
}
