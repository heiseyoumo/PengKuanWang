package com.fancy.myapplication.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.fancy.myapplication.R;


/**
 * @author pengkuanwang
 * @date 2019-07-10
 */
public class Demo6Activity extends Activity {

    Messenger messenger;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            Message message = Message.obtain(null, 100);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "Hello World");
            message.setData(bundle);
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo11);
        Looper.getMainLooper();
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Demo6Activity.this, MessengerService.class);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });
    }
}
