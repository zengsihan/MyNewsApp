package com.zsh.xuexi.newsecondapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zsh on 2016/6/24.
 */
public class LeadAdapter extends PagerAdapter {
    Context cont;
    List<View> list;

    public LeadAdapter(Context cont, List<View> list) {
        this.cont = cont;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=list.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view=list.get(position);
        container.removeView(view);
    }
}
