package com.fancy.pluginlib;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

/**
 * @author pengkuanwang
 * @date 2019-07-03
 */
public class ProxyActivity extends Activity {
    private String className;
    IPlugin plugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = getIntent().getStringExtra("className");
        try {
            Class<?> aClass = PluginManager.getInstance().getDexClassLoader().loadClass(className);
            Object newInstance = aClass.newInstance();
            if (newInstance instanceof IPlugin) {
                plugin = (IPlugin) newInstance;
            }
            plugin.attach(this);
            Bundle bundle = new Bundle();
            bundle.putString("name", "pkw");
            bundle.putInt("age", 23);
            plugin.onCreate(bundle);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        if (plugin != null) {
            return PluginManager.getInstance().getDexClassLoader();
        } else {
            return super.getClassLoader();
        }
    }

    @Override
    public Resources getResources() {
        if (plugin != null) {
            return PluginManager.getInstance().getResources();
        } else {
            return super.getResources();
        }
    }

    @Override
    public AssetManager getAssets() {
        if (plugin != null) {
            return PluginManager.getInstance().getAssetManager();
        } else {
            return super.getAssets();
        }
    }
}
