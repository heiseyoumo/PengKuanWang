package com.fancy.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

/**
 * @author pengkuanwang
 * @date 2019-06-22
 */
public class Demo1Activity extends Activity {
    public static final int REQUEST_PERMISSION_CODE = 100;
    public String remoteUrl = "https://mov.bn.netease.com/open-movie/nos/mp4/2017/12/04/SD3SUEFFQ_hd.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo1);
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Demo1Activity.this, VideoViewActivity.class);
                intent.putExtra("url", remoteUrl);
                startActivity(intent);
            }
        });
        ActivityCompat.requestPermissions(Demo1Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length == 2) {
                /**
                 * 获取读写权限成功
                 */
                Toast.makeText(this, "读写权限成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
