package com.fancy.myapplication.view_interface;

import com.fancy.myapplication.bean.User;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public interface LoginViewInterface {
    /**
     * 登录成功回调
     *
     * @param user
     */
    void onLoginSuccess(User user);

    /**
     * 错误信息
     *
     * @param code
     * @param message
     */
    void onFail(String code, String message);
}
