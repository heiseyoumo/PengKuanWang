package com.fancy.myapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.fancy.myapplication.R;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * @author pengkuanwang
 * @date 2019-07-10
 */
public class Demo5Activity extends Activity {
    Resources resources;
    DexClassLoader dexClassLoader;
    String dexPath = "hehehehe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo11);
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexPath);
            Configuration configuration = getResources().getConfiguration();
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            resources = new Resources(assetManager, metrics, configuration);

            File file = getDir("dex", Context.MODE_PRIVATE);
            dexClassLoader = new DexClassLoader(dexPath, file.getAbsolutePath(), null, getClassLoader());
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
