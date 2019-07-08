package com.fancy.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fancy.pluginlib.PluginManager;
import com.fancy.pluginlib.ProxyActivity;

import java.util.Arrays;

/**
 * @author pengkuanwang
 * @date 2019-07-03
 */
public class Demo13Activity extends Activity {
    int a[] = {23, 1, 4, 2, 6, 4, 67, 32};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo13);
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++) {
            Log.d("Demo13Activity", a[i] + "");
        }
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PluginManager.getInstance().init(Demo13Activity.this);
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/neteasepluginapk-debug.apk";
                PluginManager.getInstance().loadApk(absolutePath);
                Toast.makeText(Demo13Activity.this, "加载成功", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Demo13Activity.this, ProxyActivity.class);
                String name = PluginManager.getInstance().getPackageInfo().activities[0].name;
                intent.putExtra("className", name);
                startActivity(intent);
            }
        });
    }
}
