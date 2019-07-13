package com.fancy.myapplication.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fancy.myapplication.R;


/**
 * @author pengkuanwang
 * @date 2019-07-10
 */
public class VideoDialog extends Dialog {
    public VideoDialog(Context context) {
        super(context, R.style.DialogTransparentBgTheme);
        init(context);
    }

    private void init(Context context) {
        View contentView = View.inflate(context, R.layout.dialog_video, null);
        setContentView(contentView);
        TextView closeVideoTv = contentView.findViewById(R.id.closeVideoTv);
        TextView playVideoTv = contentView.findViewById(R.id.playVideoBtn);
        closeVideoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickCancel();
                }
            }
        });
        playVideoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickSure();
                }
            }
        });
    }

    OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClickCancel();

        void onClickSure();
    }
}
