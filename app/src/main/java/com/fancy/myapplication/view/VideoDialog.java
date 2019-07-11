package com.fancy.myapplication.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.fancy.myapplication.R;

/**
 * @author pengkuanwang
 * @date 2019-07-11
 */
public class VideoDialog extends Dialog {
    public VideoDialog(Context context) {
        super(context, R.style.DialogTransparentBgTheme);
        init(context);
    }

    private void init(Context context) {
        View contentView = View.inflate(context, R.layout.dialog_video, null);
        setContentView(contentView);
    }
}
