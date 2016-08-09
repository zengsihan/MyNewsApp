package com.zsh.xuexi.newsecondapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;


import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.activity.WebActivity;
import com.zsh.xuexi.newsecondapp.adapter.HotAdapter;
import com.zsh.xuexi.newsecondapp.db.HotDB;
import com.zsh.xuexi.newsecondapp.entity.HotInfo;
import com.zsh.xuexi.newsecondapp.http.HttpConnection;
import com.zsh.xuexi.newsecondapp.http.Jiexi;
import com.zsh.xuexi.newsecondapp.utils.MyItem;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by zsh on 2016/6/19.
 *
 */
public class HotFragment extends Fragment {
    View view;
    HotAdapter ha;
    static String path="http://v.juhe.cn/toutiao/index";
    static String key="88fe6728b1e35ddfe64132697b727087";
    String newsname;//关键字搜索
    String result="0";//返回结果
    List<HotInfo> list;
    List<HotInfo> alllist;
//    List<HotInfo> alllist;
    RecyclerView rv;
    HotDB hb;
    SwipeRefreshLayout sr;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.hot_fragment,null);
        sr= (SwipeRefreshLayout) view.findViewById(R.id.hot_refresh);
        rv= (RecyclerView) view.findViewById(R.id.hot_recycleview);
        //设置布局
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        list=new ArrayList<HotInfo>();
        alllist=new ArrayList<HotInfo>();
        ha=new HotAdapter(getActivity());
        hb=new HotDB(getActivity());

        if(hb.findOneType("top").size()==0){
            asd();
        }else{
            alllist=hb.findOneType("top");
            if(alllist.size()>0){
                for(int i=0;i<5;i++){
                    list.add(alllist.get(i));
                }
            }
            ha.setList(list);
        }

        //设置分隔线, MyItem是自定义的一个类，里面有各种设置分隔线的样式方法
        rv.addItemDecoration(new MyItem(getActivity(), LinearLayoutManager.HORIZONTAL,5,getResources().getColor(R.color.touming)));

        //设置监听,  接口，回调
        ha.setRvlistenner(new HotAdapter.RecycleViewClickListener() {

            @Override
            public void OnItemClickListener(View view, int position) {
                String url=list.get(position).getUrl();
                Intent i=new Intent(getActivity(), WebActivity.class);
                i.putExtra("url",url);
                startActivity(i);
            }

            @Override
            public void OnItemLongClickListener(View view, int position) {
                Toast.makeText(getActivity(), "长按", Toast.LENGTH_SHORT).show();
            }
        });

        sr.setColorSchemeResources(R.color.blue, R.color.red, R.color.green, R.color.purple);
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新操作
                asd();
            }
        });
        //动画，这里调用的系统的
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(ha);

        return view;
    }
    void asd(){
//        try {
//            newsname= URLEncoder.encode("top", "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String pathhttpget=path;
        StringBuffer sb=new StringBuffer(pathhttpget);
//        类型,,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
        sb.append("?type="+newsname);//添加查询的关键字
        sb.append("&key="+key);//把Key添加
        pathhttpget=sb.toString();
        loadData(pathhttpget, 1);
    }
    void loadData(final String path, final int position){
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (position){
                    case 1:
                        result= HttpConnection.getDataByHttpGet(path);
//                        Log.i("ms",result);
                        break;
                }
                handler.sendEmptyMessage(1);
            }
        }) .start();
    }
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if(result.equals("error")){
                        Toast.makeText(getActivity(), "网络有问题，请检查网络，重新刷新!", Toast.LENGTH_SHORT).show();
                        sr.setRefreshing(false);//取消加载中的圈圈
                    }else{
                        alllist= Jiexi.JSON_HotInfo("top", result);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        list=new ArrayList<HotInfo>();
                        if(alllist.size()>0){
                            if(alllist.size()>5){//数据库里不止5条数据，用个随机，看起来变化更清晰
                                Random rd=new Random();
                                int id=rd.nextInt(10)+alllist.size()-15;
                                for(int i=0;i<5;i++){
                                    list.add(alllist.get(i+id));
                                }
                            }else{
                                list.addAll(alllist);
                            }
                            ha.setList(list);
                            ha.notifyDataSetChanged();
                            sr.setRefreshing(false);//取消加载中的圈圈
                            if(hb.isHaveThisNews("top")){
                                hb.deleteOneType("top");
                            }
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            for(int i=0;i<list.size();i++){
                                hb.addNews(list.get(i));
                            }
                        }else{
                            Toast.makeText(getActivity(), "解析有问题，重新刷新", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
    };
}
