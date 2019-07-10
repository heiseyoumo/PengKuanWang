package com.fancy.myapplication.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fancy.myapplication.R;

/**
 * @author pengkuanwang
 * @date 2019-07-09
 */
public class Demo4Activity extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo11);
        imageView = findViewById(R.id.imageView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = decodeSampledBitmapFromSource(getResources(), R.drawable.ic_launcher, 72, 144);
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    public Bitmap decodeSampledBitmapFromSource(Resources resources, int resId, int requestWidth, int requestHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        /**
         * 解析资源文件
         */
        BitmapFactory.decodeResource(resources, resId, options);
        int sampleSize = calculateSampleSize(options, requestWidth, requestHeight);
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, resId, options);
    }

    private int calculateSampleSize(BitmapFactory.Options options, int requestWidth, int requestHeight) {
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;
        int inSampleSize = 1;
        if (outHeight > requestHeight || outWidth > requestWidth) {
            int i = outHeight / requestHeight;
            inSampleSize = i;
            int i1 = outWidth / requestWidth;
            if (i > i1) {
                inSampleSize = i1;
            }
        }
        return inSampleSize;
    }
}
