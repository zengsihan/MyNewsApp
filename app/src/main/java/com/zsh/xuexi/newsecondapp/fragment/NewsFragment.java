package com.zsh.xuexi.newsecondapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.activity.AddActivity;
import com.zsh.xuexi.newsecondapp.adapter.NewsAdapert;
import com.zsh.xuexi.newsecondapp.db.NewsListDB;
import com.zsh.xuexi.newsecondapp.entity.NewsListInfo;
import com.zsh.xuexi.newsecondapp.utils.RotateDownPageTransformer;
import com.zsh.xuexi.newsecondapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2016/6/19.
 * news 界面
 */
public class NewsFragment extends Fragment implements View.OnClickListener {
//    static int number=-1;//判断哪个界面拿数据
    NewsAdapert newsAdapert;
    View view;
    List<Fragment> list;

    Constellation_Fragment constellation_fragment;
    ViewPager vp;
    LinearLayout ll_content;
    TabLayout tab;
    List<String> toolList;
    RelativeLayout rl_add;

    NewsListDB nb;
    NewsListInfo nli;
    List<NewsListInfo> nlist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.news_fragment,null);
        Tools.ff=true;
        getview();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Tools.ff==true){
            setview();
        }
        Tools.ff=false;
    }

    public void getview(){
        vp= (ViewPager) view.findViewById(R.id.news_viewpager);
        ll_content= (LinearLayout) view.findViewById(R.id.news_content);
        tab= (TabLayout) view.findViewById(R.id.news_tablayout);
        rl_add= (RelativeLayout) view.findViewById(R.id.news_fragment_rl);
        rl_add.setOnClickListener(this);
        nb=new NewsListDB(getActivity());
        nlist=new ArrayList<NewsListInfo>();
    }

    public void setview(){
        nlist=nb.findAll();
        list=new ArrayList<Fragment>();
        toolList=new ArrayList<String>();
        for(int i=0;i<nlist.size();i++){
            if(nlist.get(i).getIsShow().equals("true")){
                constellation_fragment=new Constellation_Fragment();//new 一个fragment
                constellation_fragment.setNewsname(nlist.get(i).getName());//传一个关键字，得到不同的news
                list.add(constellation_fragment);//把fragment添加到viewpager的list
                toolList.add(nlist.get(i).getName());//把名字添加到toolbar上
            }
        }
        newsAdapert=new NewsAdapert(getChildFragmentManager(),list,toolList);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);//模式，好像是挤在一起
        tab.setSelectedTabIndicatorColor(Color.BLUE);
        tab.setTabTextColors(Color.BLUE, android.graphics.Color.parseColor("#ffbd36"));
        vp.setOffscreenPageLimit(2);//预加载个数
        vp.setAdapter(newsAdapert);
        vp.setPageTransformer(true, new RotateDownPageTransformer());//滑动的定制动画
        tab.setupWithViewPager(vp);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.news_fragment_rl:
                Intent i=new Intent(getActivity(), AddActivity.class);
                startActivity(i);
                break;
        }
    }

}
