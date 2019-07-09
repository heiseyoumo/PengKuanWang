package com.fancy.myapplication.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author pengkuanwang
 * @date 2019-07-08
 */
class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
