package com.fancy.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.fancy.myapplication.util.MemoryCacheUtil;

import java.util.ArrayList;

/**
 * @author pengkuanwang
 * @date 2019-07-03
 */
public class Demo10Activity extends Activity {
    private ArrayList<String> list = new ArrayList<>();
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo8);
        imageView = findViewById(R.id.imageView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Demo10Activity.this,Demo9Activity.class));
            }
        });
        list.add("https://m.changyoyo.com/video/baiguoyuan.mp4");
        list.add("https://m.changyoyo.com/video/wehotel.mp4");
        list.add("https://m.changyoyo.com/video/baiguoyuan.mp4");

        MemoryCacheUtil.getInstance().LoadBitmapByUrl("https://m.changyoyo.com/video/baiguoyuan.mp4", new MemoryCacheUtil.OnBitmapListener() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                Log.d("Demo10Activity", "图片获取成功");
            }
        });
    }
}
