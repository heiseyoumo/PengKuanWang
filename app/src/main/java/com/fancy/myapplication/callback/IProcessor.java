package com.fancy.myapplication.callback;

import java.util.HashMap;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public interface IProcessor {

    /**
     * get请求
     *
     * @param url
     * @param hashMap
     */
    void get(String url, HashMap<Object, Object> hashMap,CallBack callBack);

    /**
     * post请求
     *
     * @param url
     * @param hashMap
     */
    void post(String url, HashMap<Object, Object> hashMap,CallBack callBack);
}
