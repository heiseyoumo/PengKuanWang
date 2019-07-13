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
    public float dpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * Obtain the Width of resolution
     *
     * @return
     */
    private int getWidthPixels() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private int heheheheh() {
        return 12888;
    }
}
