package com.fancy.myapplication.util;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wanglijuan on 17/6/6.
 */

public class Api {
    public static ApiService SERVICE;
    private static final int CONNECT_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 300;
    private static final int READ_TIMEOUT = 30;
    private static String BASE_URL;

    public static ApiService getDefault() {
        if (SERVICE == null) {
            BASE_URL = "https://test-m-stg.ppppoints.com/";
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
            httpClientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
            httpClientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
            httpClientBuilder.addInterceptor(new LoggingInterceptor());
            SERVICE = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build().create(ApiService.class);
        }
        return SERVICE;
    }
}
