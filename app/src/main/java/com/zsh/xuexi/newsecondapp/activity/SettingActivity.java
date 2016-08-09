package com.zsh.xuexi.newsecondapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.adapter.AddRecycleViewAdapter;
import com.zsh.xuexi.newsecondapp.adapter.SettingAdapter;
import com.zsh.xuexi.newsecondapp.base.BaseActivity;
import com.zsh.xuexi.newsecondapp.entity.NewsListInfo;
import com.zsh.xuexi.newsecondapp.service.MyService;
import com.zsh.xuexi.newsecondapp.utils.MyItem;
import com.zsh.xuexi.newsecondapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2016/7/21.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout topbar;
    TextView tv_top;
    RelativeLayout rl_left;
    ToggleButton tgb;
    RecyclerView rv;
    List<Integer> list;
    SettingAdapter sa;
    private SettingAdapter.RecycleViewClickListener rvlistenner = null;

    @Override
    public void setLaout() {
        setContentView(R.layout.setting_activity);
    }

    @Override
    public void getData() {
        list = new ArrayList<Integer>();
        int arr[] = new int[]{R.color.topbar_bg, R.color.lightyellow, R.color.gold, R.color.pink, R.color.coral, R.color.fuchsia,
                R.color.palegoldenrod, R.color.lightcyan, R.color.goldenrod, R.color.mediumorchid};
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
    }

    @Override
    public void getView() {
        topbar = (LinearLayout) findViewById(R.id.topbar_ll);
        topbar.setBackgroundResource(Tools.color);
        tv_top = (TextView) findViewById(R.id.topbar_tv);
        tv_top.setText("设置");
        rl_left = (RelativeLayout) findViewById(R.id.topbar_rl_left);
        tgb = (ToggleButton) findViewById(R.id.setting_tgb);
        rv = (RecyclerView) findViewById(R.id.setting_recycleview);
    }

    @Override
    public void setView() {
        if (Tools.f) {
            tgb.setChecked(true);
        } else {
            tgb.setChecked(false);
        }
        sa = new SettingAdapter(this, list);
        rv.setLayoutManager(new GridLayoutManager(this, 10));
        //设置分隔线, MyItem是自定义的一个类，里面有各种设置分隔线的样式方法
        rv.addItemDecoration(new MyItem(this, LinearLayoutManager.HORIZONTAL, 5, getResources().getColor(R.color.touming)));
        rv.setAdapter(sa);

        //设置监听,  接口，回调
        sa.setRvlistenner(new SettingAdapter.RecycleViewClickListener() {
            @Override
            public void OnItemClickListener(View view, final int position) {
                Tools.color = list.get(position);
                topbar.setBackgroundResource(Tools.color);
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {
            }
        });
        rl_left.setOnClickListener(this);
        tgb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_rl_left:
                finish();
                break;
            case R.id.setting_tgb://音乐
                Intent i = new Intent(this, MyService.class);
                Bundle bundle = new Bundle();
                if (tgb.isChecked()) {//播放
                    Tools.f = true;//用于保存状态
                    bundle.putInt("msg", 0);
                } else {
                    Tools.f = false;
                    bundle.putInt("msg", 1);
                }
                i.putExtras(bundle);
                startService(i);
                break;
        }
    }
}
