package com.fancy.myapplication.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.fancy.myapplication.R;
import com.fancy.myapplication.bean.Person11;

import java.util.List;

/**
 * @author pengkuanwang
 * @date 2019-07-04
 */
public class MyAdapter extends BaseAdapter<Person11> {
    public MyAdapter(Context context, List<Person11> data) {
        super(context, data);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_person;
    }

    @Override
    protected void convert(BaseViewHolder holder, Person11 data, int position) {
        ViewPager viewPager = holder.getView(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setMargins(34, 0, 45, 0);
        viewPager.setLayoutParams(layout);
        HomeTopBannerViewPagerAdapter adapter = new HomeTopBannerViewPagerAdapter(mContext);
        adapter.setData(mData);
        viewPager.setAdapter(adapter);
    }
}
