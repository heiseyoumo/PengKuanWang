package com.fancy.pluginlib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * @author pengkuanwang
 * @date 2019-07-03
 */
public class PluginManager {
    private PluginManager() {
    }

    Resources resources;
    DexClassLoader dexClassLoader;
    private Context mContext;
    PackageInfo packageInfo;
    AssetManager assetManager;

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    private volatile static PluginManager instance = new PluginManager();

    public static PluginManager getInstance() {
        return instance;
    }

    public Resources getResources() {
        return resources;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void loadApk(String apkPath) {
        File file = mContext.getDir("dex", Context.MODE_PRIVATE);
        packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        dexClassLoader = new DexClassLoader(apkPath, file.getAbsolutePath(), null, mContext.getClassLoader());
        try {
            assetManager = AssetManager.class.newInstance();
            Method assetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            assetPath.invoke(assetManager, apkPath);
            resources = new Resources(assetManager, mContext.getResources().getDisplayMetrics(), mContext.getResources().getConfiguration());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
