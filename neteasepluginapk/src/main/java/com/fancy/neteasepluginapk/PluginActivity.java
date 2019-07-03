package com.fancy.neteasepluginapk;

import android.app.Activity;
import android.os.Bundle;

import com.fancy.pluginlib.IPlugin;

/**
 * @author pengkuanwang
 * @date 2019-07-03
 */
public class PluginActivity extends Activity implements IPlugin {
    Activity activity;
    private int mForm = FROM_INTERNAL;

    @Override
    public void attach(Activity proxyActivity) {
        activity = proxyActivity;
    }

    @Override
    public void onCreate(Bundle saveInstance) {
        if (saveInstance != null) {
            mForm = saveInstance.getInt("FROM");
        }
        if (mForm == FROM_INTERNAL) {
            super.onCreate(saveInstance);
            activity = this;
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (mForm == FROM_INTERNAL) {
            super.setContentView(layoutResID);
        } else {
            activity.setContentView(layoutResID);
        }
    }

    @Override
    public void onResume() {
        if (mForm == FROM_INTERNAL) {
            super.onResume();
        }
    }
}
