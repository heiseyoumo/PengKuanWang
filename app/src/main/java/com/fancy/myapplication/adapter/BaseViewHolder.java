package com.fancy.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author pengkuanwang
 * @date 2018/6/12
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private View mConvertView;
    private SparseArray<View> mViews;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static BaseViewHolder createViewHolder(Context mContext, int itemLayoutId, ViewGroup parent) {
        View itemView = LayoutInflater.from(mContext).inflate(itemLayoutId, parent, false);
        return new BaseViewHolder(itemView);
    }

    /**
     * 通过id获得控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public BaseViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }
}
