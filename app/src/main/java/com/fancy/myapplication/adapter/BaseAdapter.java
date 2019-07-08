package com.fancy.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengkuanwang
 * @date 2018/6/12
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mData;
    protected Context mContext;

    /**
     * 每一个列表的item布局
     *
     * @return
     */
    protected abstract int getItemLayoutId();

    /**
     * 初始化数据
     *
     * @param holder
     * @param data
     * @param position
     */
    protected abstract void convert(BaseViewHolder holder, T data, int position);

    public BaseAdapter(Context context) {
        this(context, null);
    }

    public BaseAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = (data == null ? mData = new ArrayList<>() : data);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = BaseViewHolder.createViewHolder(mContext, getItemLayoutId(), parent);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        final T bean = mData.get(position);
        convert(holder, bean, position);
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(bean, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 加载更多的数据
     *
     * @param data
     */
    public void setLoadMoreData(List<T> data) {
        int size = mData.size();
        mData.addAll(data);
        notifyItemInserted(size);
    }

    /**
     * 刷新新的数据
     *
     * @param data
     */
    public void setNewData(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getmData() {
        return mData;
    }

    private OnItemClickListener<T> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
