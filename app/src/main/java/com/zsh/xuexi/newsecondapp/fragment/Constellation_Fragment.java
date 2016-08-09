package com.zsh.xuexi.newsecondapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.activity.WebActivity;
import com.zsh.xuexi.newsecondapp.adapter.ConstellationAdapter;
import com.zsh.xuexi.newsecondapp.db.HotDB;
import com.zsh.xuexi.newsecondapp.entity.HotInfo;
import com.zsh.xuexi.newsecondapp.entity.NewsInfo;
import com.zsh.xuexi.newsecondapp.http.HttpConnection;
import com.zsh.xuexi.newsecondapp.http.HttpUtil;
import com.zsh.xuexi.newsecondapp.http.Jiexi;
import com.zsh.xuexi.newsecondapp.utils.VolleySingleton;
import com.zsh.xuexi.newsecondapp.view.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zsh on 2016/6/20.
 * news页面的星座
 */
public class Constellation_Fragment extends Fragment {
    RippleView rippleView;
    View view;
    ConstellationAdapter cAdapter;
    List<HotInfo> list;
    String result="0";
    int page=1;
    String url;
    String newurl;
    String newsname;
    String nname;
    HotDB hb;
    SwipeRefreshLayout sr;
    RecyclerView rv;
    LinearLayoutManager layoutManager;
    boolean isloading,loadingfinish;
    boolean f=false;//区分下拉上拉handler的发送
    public String getNewsname() {
        return newsname;
    }

    public void setNewsname(String newsname) {
        this.newsname = newsname;
        nname=newsname;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.constellation_fragment,null);
        Log.i("ms","----------onceate");
        rippleView= (RippleView) view.findViewById(R.id.constellation_rippleview);
        sr= (SwipeRefreshLayout) view.findViewById(R.id.constellation_refresh);
        rv= (RecyclerView) view.findViewById(R.id.constellation_recycleview);
        hb=new HotDB(getActivity());
        list=new ArrayList<HotInfo>();
        cAdapter=new ConstellationAdapter(getActivity());
        isloading=false;
        loadingfinish=true;
        geturl();
        if(hb.findOneType(nname).size()==0){//说明数据库里没有数据
            loadData();//去网上拿数据
        }else{
            list=hb.findOneType(nname);
            cAdapter.setList(list);
        }

        cAdapter.setRvlistenner(new ConstellationAdapter.RecycleViewClickListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                String url=list.get(position).getUrl();
                final Intent i=new Intent(getActivity(), WebActivity.class);
                i.putExtra("url",url);
                rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        startActivity(i);
                    }
                });

            }
            @Override
            public void OnItemLongClickListener(View view, int position) {
//                Toast.makeText(getActivity(), "长按", Toast.LENGTH_SHORT).show();
            }
        });
        sr.setColorSchemeResources(R.color.blue,R.color.red,R.color.green,R.color.purple);
        layoutManager=new LinearLayoutManager(getActivity());
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int layoutitem=layoutManager.findLastVisibleItemPosition();
                if(layoutitem==cAdapter.getItemCount()-1){
                    switch (newState){
                        case 0://滑动结束
                            if(isloading&&loadingfinish){
                                //数据加载
                                page++;
                                f=true;
                                loadData();
                            }
                            break;
                        case 1://开始滑动
                            break;
                        case 2://滑到顶部或底部
                            if(!isloading){
                                cAdapter.changeState(2);
                                isloading=true;
                            }
                            //准备加载数据
                            break;
                    }
                }
            }
        });
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新操作
                list=new ArrayList<HotInfo>();
                page=1;
                f=false;
                loadData();
            }
        });
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(cAdapter);
        return view;
    }
    void geturl(){
        Log.i("ms","getttttttttttttttttttttttttttttttt");
        try {
            newsname= URLEncoder.encode(nname, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.i("ms","转码错误");
            e.printStackTrace();
        }
        url=HttpUtil.appendHand(HttpUtil.URL,"channelName",newsname);
    }

    void loadData(){
        newurl=HttpUtil.appendUrl(url, "page", page + "");
        Log.i("ms","url="+newurl);
        Log.i("ms","获取数据");
        StringRequest st=new StringRequest(Request.Method.GET,newurl,new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                Log.i("msg",s);
                try {
                    Log.i("ms","开始解析");
                    JSONObject jo=new JSONObject(s);
                    int code=jo.getInt("showapi_res_code");
                    if(code==0){
                        JSONObject jbody=jo.getJSONObject("showapi_res_body");
                        JSONObject jpagebean=jbody.getJSONObject("pagebean");
                        JSONArray jcontentlist=jpagebean.getJSONArray("contentlist");
                        HotInfo hi=null;
                        for(int i=0;i<jcontentlist.length();i++){
                            hi=new HotInfo();
                            JSONObject jb=jcontentlist.getJSONObject(i);
//                            Log.i("ms","11111");
                            hi.setName(nname);
//                            Log.i("ms", "2222");
                            hi.setTitle(jb.getString("title"));
//                            Log.i("ms", "3333");
                            hi.setUrl(jb.getString("link"));
//                            Log.i("ms", "44444");
                            hi.setDate(jb.getString("pubDate"));
//                            Log.i("ms", "5555");
                            JSONArray jimg=jb.getJSONArray("imageurls");
//                            Log.i("ms","6666");
                            if(jimg.length()==0){
//                                Log.i("ms","jimg=null");
                                hi.setImg1("null");
                            }else{
                                JSONObject jj=jimg.getJSONObject(0);
//                                Log.i("ms","jimg   you");
                                hi.setImg1(jj.getString("url"));
                            }
                            list.add(hi);
                        }
                        Log.i("ms","list="+list.toString());
                        if(f==false){
                            handler.sendEmptyMessage(1);
                        }else{
                            handler.sendEmptyMessage(0);
                        }
                        Log.i("ms", "发送handler");
                    }else{
                        handler.sendEmptyMessage(2);//没有拿到数据，网络有问题
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.i("ms","错误"+newurl);
                handler.sendEmptyMessage(2);//没有拿到数据，网络有问题
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("apikey",HttpUtil.KEY);
                Log.i("ms", "执行拼接");
                return params;
            }
            //解决拿数据乱码
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String str = null;
                try {
                    str = new String(response.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        VolleySingleton.getVolleySingleton(getActivity()).addToRequestQueue(st);

    }
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2://刷新，加载出问题。
                    Toast.makeText(getActivity(), "网络异常，没有拿到数据", Toast.LENGTH_SHORT).show();
                    cAdapter.changeState(0);
                    isloading=false;
                    sr.setRefreshing(false);//取消加载中的圈圈
                    break;
                case 1://刷新成功。
                        cAdapter.setList(list);
                        cAdapter.notifyDataSetChanged();
                        sr.setRefreshing(false);//取消加载中的圈圈
                        if(hb.findOneType(nname).size()>0){//清空这个表情数据库里的数据
                            hb.deleteOneType(nname);
                        }
                        for(int i=0;i<list.size();i++){
                            hb.addNews(list.get(i));
                        }
                    break;
                case 0://上拉加载成功
                        cAdapter.setList(list);
                        cAdapter.changeState(0);
                        isloading=false;
                    break;
            }
        }
    };

}
