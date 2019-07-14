package com.fancy.myapplication.callback;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public abstract class HttpResponseCallBack<T> implements CallBack {
    @Override
    public void onSuccess(String json) {
        /*Gson gson = new Gson();
        Type type = this.getClass().getGenericSuperclass();
        Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
        T result = gson.fromJson(json, arguments[0]);*/
        onFailAgain(json);
    }

    @Override
    public void onFail(String errorMsg) {

    }

    public abstract  void onSuccess(T t);

    public abstract void onFailAgain(String errorMsg);
}
