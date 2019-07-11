package com.fancy.myapplication.util;

import android.content.res.Resources;

/**
 * @author pengkuanwang
 * @date 2017/6/5
 */
public class DM {
    /**
     * Converts dp size into pixels.
     *
     * @param dp dp size to get converted
     * @return Pixel size
     */
    public static float dpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * Converts pixels size into dp
     *
     * @param px pixels size to get converted
     * @return dp size
     */
    public static float pxToDp(float px) {
        return px / Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * Obtain the Width of resolution
     *
     * @return
     */
    public static int getWidthPixels() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    /**
     * Obtain the height of resolution
     *
     * @return
     */
    public static int getHeightPixels() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static float getDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = Resources.getSystem().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
