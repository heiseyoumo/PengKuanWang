package com.fancy.myapplication.util;

import com.fancy.myapplication.bean.Person11;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author pengkuanwang
 * @date 2019-07-08
 */
public interface ApiService {

    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("login")
    Observable<Person11> login(@Body RequestBody request);
}
