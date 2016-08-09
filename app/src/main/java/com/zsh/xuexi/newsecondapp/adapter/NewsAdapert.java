package com.zsh.xuexi.newsecondapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2016/6/20.
 */
public class NewsAdapert extends FragmentStatePagerAdapter {
    List<Fragment> list;
    List<String> titlelist;

    public List<Fragment> getList() {
        return list;
    }

    public void setList(List<Fragment> list) {
        this.list = list;
    }

    public List<String> getTitlelist() {
        return titlelist;
    }

    public void setTitlelist(List<String> titlelist) {
        Log.i("ms","sdds"+titlelist.toString());
        this.titlelist = titlelist;
    }

    public NewsAdapert(FragmentManager fm, List<Fragment> list, List<String> titlelist) {
        super(fm);
        this.list = list;
        this.titlelist = titlelist;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.i("ms","titlelist="+titlelist.get(position));
        Log.i("ms","titlelist="+titlelist.toString());
        return titlelist.get(position);
    }

}
