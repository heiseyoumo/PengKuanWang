package com.fancy.myapplication.adapter;

/**
 * @author pengkuanwang
 * @date 2018/6/12
 */
public interface OnItemClickListener<T> {
    /**
     * 每个item点击事件
     *
     * @param data
     * @param position
     */
    void onItemClick(T data, int position);
}
