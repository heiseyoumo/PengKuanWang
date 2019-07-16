package com.fancy.myapplication.util;

import com.fancy.myapplication.adapter.Ad;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author pengkuanwang
 * @date 2019-07-16
 */
public class JsonUtil<T> {
    public T getJson(String response) {
        Gson gson = new Gson();
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] arguments = parameterizedType.getActualTypeArguments();
        T result = gson.fromJson(response, arguments[0]);
        return result;
    }

    public List<Ad> getJ(String response) {
        Gson gson = new Gson();
        List<Ad> topAds = gson.fromJson(response, new TypeToken<List<Ad>>() {
        }.getType());
        return topAds;
    }
}
