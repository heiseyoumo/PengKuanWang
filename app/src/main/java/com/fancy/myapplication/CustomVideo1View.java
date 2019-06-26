package com.fancy.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @author pengkuanwang
 * @date 2019-06-22
 */
public class CustomVideo1View extends VideoView {

    public CustomVideo1View(Context context) {
        super(context);
    }

    public CustomVideo1View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideo1View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }
}