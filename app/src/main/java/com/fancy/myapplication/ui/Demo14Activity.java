package com.fancy.myapplication.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.fancy.myapplication.BaseActivity;
import com.fancy.myapplication.R;
import com.fancy.myapplication.bean.User;
import com.fancy.myapplication.callback.HttpResponseCallBack;
import com.fancy.myapplication.presenter.LoginPresenter;
import com.fancy.myapplication.view_interface.LoginViewInterface;

import java.util.HashMap;

/**
 * @author pengkuanwang
 * @date 2019-07-13
 */
public class Demo14Activity extends BaseActivity<LoginViewInterface, LoginPresenter> implements LoginViewInterface {

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    }) {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            User user = (User) msg.obj;
            Toast.makeText(Demo14Activity.this, user.userName, Toast.LENGTH_SHORT).show();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        presenter.login(this, "13723123731");
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<Object, Object> hashMap = new HashMap<>();
                hashMap.put("name", "pkw");
                HttpHelper.getInstance().get("hahah", hashMap, new HttpResponseCallBack<User>() {

                    @Override
                    public void onSuccess(User user) {

                    }

                    @Override
                    public void onFailAgain(String errorMsg) {
                        Message message = Message.obtain(handler, 200, new User(23, "pkw", true));
                        message.sendToTarget();
                    }
                });
            }
        });
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onLoginSuccess(User user) {

    }

    @Override
    public void onFail(String code, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
