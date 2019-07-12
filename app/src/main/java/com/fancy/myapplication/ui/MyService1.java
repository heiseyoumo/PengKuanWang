package com.fancy.myapplication.ui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.fancy.myapplication.IMyAidlInterface;

/**
 * @author pengkuanwang
 * @date 2019-07-12
 */
public class MyService1 extends Service {
    private IMyAidlInterface.Stub iMyAidlInterface = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt) throws RemoteException {

        }

        @Override
        public String getName() throws RemoteException {
            return null;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return iMyAidlInterface;
    }
}
