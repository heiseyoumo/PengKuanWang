package com.fancy.myapplication.util;

import android.text.TextUtils;

/**
 * @author pengkuanwang
 * @date 2019-07-21
 */
public class HttpResult<T> {
    public static final String SUCCESS = "0000";
    public static final String USER_NOT_LOGIN = "USER_NOT_LOGIN";
    private String respCode;
    private String respMsg;
    private T data;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {//接口请求成功
        if (TextUtils.equals(respCode, "0000")) {
            return true;
        }
        return false;
    }
}
