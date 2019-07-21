package com.fancy.myapplication.util;

import com.fancy.myapplication.bean.Translation;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author pengkuanwang
 * @date 2019-07-21
 */
public interface GetRequest_Interface {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();
}
