package com.fancy.myapplication.ui;

import com.fancy.myapplication.callback.CallBack;
import com.fancy.myapplication.callback.IProcessor;

import java.util.HashMap;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class HttpHelper implements IProcessor {

    IProcessor processor;
    private volatile static HttpHelper httpHelper;

    private HttpHelper() {
    }

    public static HttpHelper getInstance() {
        if (httpHelper == null) {
            synchronized (HttpHelper.class) {
                if (httpHelper == null) {
                    httpHelper = new HttpHelper();
                }
            }
        }
        return httpHelper;
    }

    public void initProcessor(IProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void get(String url, HashMap<Object, Object> hashMap, CallBack callBack) {
        processor.get(url, hashMap,callBack);
    }

    @Override
    public void post(String url, HashMap<Object, Object> hashMap, CallBack callBack) {
        processor.post(url, hashMap,callBack);
    }
}
