package com.fancy.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.fancy.myapplication.util.MemoryCacheUtil;

import java.util.HashMap;

/**
 * @author pengkuanwang
 * @date 2019-07-01
 */
public class Demo9Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo9);
        final ImageView imageView = findViewById(R.id.imageView);
        MemoryCacheUtil.getInstance().LoadBitmapByUrl("https://m.changyoyo.com/video/baiguoyuan.mp4", new MemoryCacheUtil.OnBitmapListener() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                Log.d("Demo10Activity", "图片获取成功");
            }
        });
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("hehe", "hehe");

        Context baseContext = getBaseContext();
        getApplication();
        getApplicationContext();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }
}
