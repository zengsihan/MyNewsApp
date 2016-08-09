package com.zsh.xuexi.newsecondapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.adapter.AddListViewAdapter;
import com.zsh.xuexi.newsecondapp.adapter.AddRecycleViewAdapter;
import com.zsh.xuexi.newsecondapp.base.BaseActivity;
import com.zsh.xuexi.newsecondapp.db.NewsListDB;
import com.zsh.xuexi.newsecondapp.entity.NewsListInfo;
import com.zsh.xuexi.newsecondapp.utils.MyItem;
import com.zsh.xuexi.newsecondapp.utils.Tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2016/7/13.
 */
public class AddActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout topbar;
    RelativeLayout rl_left;
    RecyclerView rv;
    ListView lv;
    AddListViewAdapter ladapter;
    AddRecycleViewAdapter radapter;
    List<String> list;
    List<String> rlist;
    NewsListDB nb;
    List<NewsListInfo> nlist;

    @Override
    public void setLaout() {
        Tools.ff=true;
        setContentView(R.layout.add_activity);
    }

    @Override
    public void getData() {
        nb=new NewsListDB(this);
        nlist=new ArrayList<NewsListInfo>();
        list=new ArrayList<String>();
        rlist=new ArrayList<String>();

        nlist=nb.findAll();
        for(int i=0;i<nlist.size();i++){
            list.add(nlist.get(i).getName());
            if(nlist.get(i).getIsShow().equals("true")){
                rlist.add(nlist.get(i).getName());
            }
        }
    }

    @Override
    public void getView() {
        rl_left= (RelativeLayout) findViewById(R.id.topbar_rl_left);
        lv= (ListView) findViewById(R.id.add_addll_listview);
        rv= (RecyclerView) findViewById(R.id.add_recycleview);
        topbar= (LinearLayout) findViewById(R.id.topbar_ll);
        topbar.setBackgroundResource(Tools.color);
    }

    @Override
    public void setView() {
        rl_left.setOnClickListener(this);
        ladapter=new AddListViewAdapter(this);
        ladapter.setList(list);
        lv.setAdapter(ladapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,  int position, long id) {
                rlist=radapter.getList();
                if(rlist.size()==0){
                    radapter.addItem(0,list.get(position));
                    nb.update(list.get(position), new NewsListInfo(list.get(position),"true"));//更新数据库
                    radapter.setList(rlist);
                    radapter.notifyDataSetChanged();
                }else {
                    for(int i=0;i<rlist.size();i++){
                        if(rlist.get(i).equals(list.get(position))){
                            Toast.makeText(AddActivity.this, list.get(position)+"已经添加", Toast.LENGTH_SHORT).show();
                            break;
                        }else if(rlist.size()-1==i){
                            radapter.addItem(rlist.size(),list.get(position));
                            nb.update(list.get(position), new NewsListInfo(list.get(position), "true"));//更新数据库
                            radapter.setList(rlist);
                            radapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }
        });

        rv.setLayoutManager(new GridLayoutManager(this, 8));
        radapter=new AddRecycleViewAdapter(this);
        radapter.setList(rlist);
        //设置分隔线, MyItem是自定义的一个类，里面有各种设置分隔线的样式方法
        rv.addItemDecoration(new MyItem(this, LinearLayoutManager.HORIZONTAL, 5, getResources().getColor(R.color.touming)));
        rv.setAdapter(radapter);
        //设置监听,  接口，回调
        radapter.setRvlistenner(new AddRecycleViewAdapter.RecycleViewClickListener() {
            @Override
            public void OnItemClickListener(View view, final int position) {
//                Toast.makeText(AddActivity.this, "短按", Toast.LENGTH_SHORT).show();
                nb.update(rlist.get(position),new NewsListInfo(rlist.get(position),"false"));//更新数据库
                radapter.removeItem(position);
                rlist=radapter.getList();
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {
                Toast.makeText(AddActivity.this, "长按", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.topbar_rl_left:
                finish();
                break;
        }
    }
}
