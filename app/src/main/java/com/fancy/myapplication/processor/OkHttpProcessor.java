package com.fancy.myapplication.processor;

import com.fancy.myapplication.callback.CallBack;
import com.fancy.myapplication.callback.IProcessor;

import java.util.HashMap;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class OkHttpProcessor implements IProcessor {
    @Override
    public void get(String url, HashMap<Object, Object> hashMap, final CallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    callBack.onSuccess("hahahaha");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void post(String url, HashMap<Object, Object> hashMap, CallBack callBack) {

    }
}
