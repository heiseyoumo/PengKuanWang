package com.fancy.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fancy.myapplication.R;
import com.fancy.myapplication.bean.Person11;

import java.util.List;

/**
 * Created by wanglijuan on 2019/7/4.
 */
public class HomeTopBannerViewPagerAdapter extends PagerAdapter {
    private List<Person11> ads;
    private Context context;

    public HomeTopBannerViewPagerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Person11> ads) {
        this.ads = ads;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        int count = ads == null || ads.size() == 0 ? 0 : ads.size();
        return count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_top_banner1, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_banner);
        if (ads != null && ads.size() > 0) {
            Person11 person11 = ads.get(position);
            imageView.setBackgroundColor(Color.parseColor("#ffbbaa"));
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
