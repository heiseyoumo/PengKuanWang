package com.fancy.myapplication.ui;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

/**
 * @author pengkuanwang
 * @date 2019-07-10
 */
public class MessengerService extends Service {

    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    Messenger replyTo = msg.replyTo;
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private final Messenger messenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
