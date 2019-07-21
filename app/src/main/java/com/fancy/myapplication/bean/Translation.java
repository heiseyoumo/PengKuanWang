package com.fancy.myapplication.bean;

import android.util.Log;

/**
 * @author pengkuanwang
 * @date 2019-07-21
 */
public class Translation {
    private int status;

    private content content;

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        Log.d("RxJava", content.out);
    }
}
