package com.zsh.xuexi.newsecondapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.adapter.LeadAdapter;
import com.zsh.xuexi.newsecondapp.db.NewsListDB;
import com.zsh.xuexi.newsecondapp.entity.NewsListInfo;
import com.zsh.xuexi.newsecondapp.utils.SaveMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2016/6/24.
 */
public class LeadActivity extends Activity {
    ViewPager vp;
    Button btn;
    LeadAdapter lAdapter;
    List<View> list;
    LayoutInflater li;
    NewsListInfo nli = null;
    NewsListDB nb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SaveMsg.getIsFristInApp(this)) {//判断是否第一次进入APP
            //是第一次进入app
            SaveMsg.setIsFirstInApp(this);//保存共享参数信息，下次就不走这了。
            setLayout();
            getView();
            setView();
            //添加newslist数据
            setNewsListData();

        } else {//不是第一次进入app
            Intent i = new Intent(this, LoadingActivity.class);//跳转到loading页面
            startActivity(i);
            finish();
        }
    }

    void setLayout() {
        setContentView(R.layout.lead_activity);
    }

    void getView() {
        vp = (ViewPager) findViewById(R.id.lead_viewpager);
        btn = (Button) findViewById(R.id.lead_btn);
    }

    void setView() {
        list = new ArrayList<View>();
        li = getLayoutInflater().from(this);
        ImageView view1 = (ImageView) li.inflate(R.layout.lead_item, null);
        view1.setImageResource(R.mipmap.lead_1);
        ImageView view2 = (ImageView) li.inflate(R.layout.lead_item, null);
        view2.setImageResource(R.mipmap.lead_2);
        ImageView view3 = (ImageView) li.inflate(R.layout.lead_item, null);
        view3.setImageResource(R.mipmap.lead_3);
        list.add(view1);
        list.add(view2);
        list.add(view3);
        lAdapter = new LeadAdapter(this, list);
        vp.setAdapter(lAdapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LeadActivity.this, LoadingActivity.class);//跳转到loading页面
                startActivity(i);
                finish();
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //设置显示隐藏button
                if (position == 2) {
                    btn.setVisibility(View.VISIBLE);
                } else {
                    btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setNewsListData() {
        nb = new NewsListDB(this);
        nli = new NewsListInfo("娱乐","yule","true");
        nb.addTitle(nli);
        nli = new NewsListInfo("国内","guonei","true");
        nb.addTitle(nli);
        nli = new NewsListInfo("国际","guoji","true");
        nb.addTitle(nli);
        nli = new NewsListInfo("军事","junshi","true");
        nb.addTitle(nli);
        nli = new NewsListInfo("电影","dianying","true");
        nb.addTitle(nli);
        nli = new NewsListInfo("科技","keji","true");
        nb.addTitle(nli);
    }
}
