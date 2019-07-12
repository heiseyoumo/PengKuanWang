package com.fancy.myapplication.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.fancy.myapplication.IMyAidlInterface;
import com.fancy.myapplication.R;
import com.fancy.myapplication.view.VideoDialog;

public class Demo8Activity extends Activity {

    IMyAidlInterface iMyAidlInterface;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                iMyAidlInterface.basicTypes(23);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo18);
        new VideoDialog(this).show();

        bindService(new Intent(Demo8Activity.this, MyService1.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
