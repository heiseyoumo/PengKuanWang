package com.fancy.neteasepluginapk;

import android.app.Activity;
import android.os.Bundle;

import com.fancy.pluginlib.IPlugin;

/**
 * @author pengkuanwang
 * @date 2019-07-04
 */
public class BaseActivity extends Activity implements IPlugin {
    @Override
    public void attach(Activity proxyActivity) {

    }

    @Override
    public void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
