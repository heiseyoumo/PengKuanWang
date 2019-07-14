package com.fancy.myapplication.presenter;

import android.content.Context;

import com.fancy.myapplication.bean.User;
import com.fancy.myapplication.callback.MvpCallBack;
import com.fancy.myapplication.model.LoginModel;
import com.fancy.myapplication.view_interface.LoginViewInterface;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class LoginPresenter extends BasePresenter<LoginViewInterface> {

    LoginModel loginModel = new LoginModel();

    public void login(Context context, String phone) {
        loginModel.login(context, phone, new MvpCallBack<User>() {
            @Override
            public void onSuccess(User data) {

            }

            @Override
            public void onFail(String code, String errorMsg) {
                getView().onFail(code, errorMsg);
            }
        });
    }
}
