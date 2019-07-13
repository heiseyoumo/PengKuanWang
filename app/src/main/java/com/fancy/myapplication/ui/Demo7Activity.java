package com.fancy.myapplication.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.fancy.myapplication.IMyAidlInterface;
import com.fancy.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author pengkuanwang
 * @date 2019-07-12
 */
public class Demo7Activity extends Activity {
    Button pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        pause = findViewById(R.id.pause);
        EventBus.getDefault().register(this);
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(new Intent(Demo7Activity.this, MyService1.class), new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                        try {
                            iMyAidlInterface.basicTypes(23);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                }, Context.BIND_AUTO_CREATE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void hehehehehehehehe(MessageWrap message) {
        String txt = "Sticky event: " + message.message;
        pause.setText(txt);
    }
}
