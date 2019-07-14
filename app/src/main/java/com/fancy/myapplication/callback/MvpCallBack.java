package com.fancy.myapplication.callback;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public interface MvpCallBack<T> {
    void onSuccess(T data);

    void onFail(String code,String errorMsg);
}
