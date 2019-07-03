package com.fancy.pluginlib;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author pengkuanwang
 * @date 2019-07-03
 */
public interface IPlugin {
    int FROM_INTERNAL=1;

    void attach(Activity proxyActivity);

    void onCreate(Bundle saveInstance);

    void onResume();
}
