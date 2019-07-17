package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fancy.myapplication.util.Api;
import com.fancy.myapplication.util.ApiService;
import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.POST;

/**
 * @author pengkuanwang
 * @date 2019-07-08
 */
public class Demo11Activity extends Activity {
    public static final String TAG = "Demo11Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo8);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api.getDefault();
            }
        });
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService apiService = (ApiService) Proxy.newProxyInstance(ApiService.class.getClassLoader(), new Class[]{ApiService.class}, new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String name = method.getName();
                        Log.d(TAG, "方法名为:" + name);
                        POST post = method.getAnnotation(POST.class);
                        String value = post.value();
                        Log.d(TAG, "接口名为:" + value);
                        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                        for (Annotation[] annotation : parameterAnnotations) {
                            String string = Arrays.toString(annotation);
                            Log.d(TAG, "注解名字为:" + string);
                        }
                        String toString = Arrays.toString(args);
                        Log.d(TAG, "参数字符串:" + toString);
                        return null;
                    }
                });
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("name", "pkw");
                hashMap.put("age", String.valueOf(29));
                apiService.userLogin(getRequest(hashMap));
            }
        });
    }

    public RequestBody getRequest(HashMap<String, String> params) {
        String jsonParams = new Gson().toJson(params);
        return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), jsonParams);
    }
}
