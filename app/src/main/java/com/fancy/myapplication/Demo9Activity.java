package com.fancy.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.fancy.myapplication.util.MemoryCacheUtil;

import java.util.ArrayList;

/**
 * @author pengkuanwang
 * @date 2019-07-01
 */
public class Demo9Activity extends Activity {
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo9);
        final ImageView imageView = findViewById(R.id.imageView);
        final ImageView imageView1 = findViewById(R.id.imageView1);
        final ImageView imageView2 = findViewById(R.id.imageView2);
        list.add("https://m.changyoyo.com/video/baiguoyuan.mp4");
        list.add("https://m.changyoyo.com/video/wehotel.mp4");
        list.add("https://m.changyoyo.com/video/baiguoyuan.mp4");
        MemoryCacheUtil.getInstance().LoadBitmapByUrl(list.get(0), new MemoryCacheUtil.OnBitmapListener() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }
        });
        MemoryCacheUtil.getInstance().LoadBitmapByUrl(list.get(1), new MemoryCacheUtil.OnBitmapListener() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView1.setImageBitmap(bitmap);
            }
        });
        MemoryCacheUtil.getInstance().LoadBitmapByUrl(list.get(2), new MemoryCacheUtil.OnBitmapListener() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                imageView2.setImageBitmap(bitmap);
            }
        });
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
