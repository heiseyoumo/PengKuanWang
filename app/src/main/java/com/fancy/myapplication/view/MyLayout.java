package com.fancy.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @author pengkuanwang
 * @date 2019-07-12
 */
public class MyLayout extends RelativeLayout {
    public static final String TAG = "MyLayout";

    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "onInterceptTouchEvent ACTION_DOWN事件");
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            Log.d(TAG, "onInterceptTouchEvent ACTION_UP事件");
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "dispatchTouchEvent ACTION_DOWN事件");
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            Log.d(TAG, "dispatchTouchEvent ACTION_UP事件");
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
