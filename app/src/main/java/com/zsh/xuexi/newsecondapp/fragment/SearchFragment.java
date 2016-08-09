package com.zsh.xuexi.newsecondapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.activity.WebActivity;
import com.zsh.xuexi.newsecondapp.adapter.SearchJiluAdapter;
import com.zsh.xuexi.newsecondapp.adapter.SearchShowListAdapter;
import com.zsh.xuexi.newsecondapp.db.HotDB;
import com.zsh.xuexi.newsecondapp.entity.HotInfo;
import com.zsh.xuexi.newsecondapp.utils.SaveMsg;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by zsh on 2016/6/19.
 */
public class SearchFragment extends Fragment implements View.OnClickListener {
    View view;
    TextView tv_search,tv_set;
    EditText et_search;

    RelativeLayout rl_hide;
    ListView jilu_lv,show_lv;
    List<String> jilu_list;
    List<HotInfo> show_list;
    SearchJiluAdapter jiluAdapter;
    SearchShowListAdapter showAdapter;
    HotDB hb;
    TextView tv_show_info,tv_hide_info;
    Set<String> set;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.search_fragment,null);
        getview();
        setview();
        return view;
    }

    void getview(){
        rl_hide= (RelativeLayout) view.findViewById(R.id.search_hide_rl);
        jilu_lv= (ListView) view.findViewById(R.id.search_jilu_listview);
        show_lv= (ListView) view.findViewById(R.id.search_show_listview);
        tv_search= (TextView) view.findViewById(R.id.search_text);
        et_search= (EditText) view.findViewById(R.id.search_et_text);
        tv_show_info= (TextView) view.findViewById(R.id.search_tv_show_info);
        tv_hide_info= (TextView) view.findViewById(R.id.search_tv_hide_info);
        tv_set= (TextView) view.findViewById(R.id.search_tv_jilu);
    }
    void setview(){
        hb=new HotDB(getActivity());
        jilu_list=new ArrayList<String>();
        show_list=new ArrayList<HotInfo>();
        jiluAdapter=new SearchJiluAdapter(getActivity());
        showAdapter=new SearchShowListAdapter(getActivity());
        set=new HashSet<String>();

        tv_search.setOnClickListener(this);
        et_search.setOnClickListener(this);
        et_search.addTextChangedListener(tw);
        jilu_list=SaveMsg.getCheckInfo(getActivity());
        jiluAdapter.setDataToAdapter(jilu_list);
        jilu_lv.setAdapter(jiluAdapter);
        showAdapter.setDataToAdapter(show_list);
        show_lv.setAdapter(showAdapter);

        show_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url=show_list.get(position).getUrl();
                Intent i=new Intent(getActivity(), WebActivity.class);
                i.putExtra("url",url);
                startActivity(i);
            }
        });

        jilu_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_search.setText("");
                rl_hide.setVisibility(View.GONE);
                String name=jilu_list.get(position);
                //把搜索记录保存到共享参数里
                if(SaveMsg.getCheckInfo(getActivity()).size()<10){//只保存十条查询记录
                    set.add(name);
                    SaveMsg.saveCheckInfo(getActivity(),set);
                }
               showInfo(name);
            }
        });

    }

    private TextWatcher tw=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length()>0){//输入了东西
                String str=s.toString();
                showSearch(str);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_text://点了搜索按钮
                rl_hide.setVisibility(View.GONE);//隐藏历史记录框
                String name=et_search.getText().toString().trim();//获取输入框里的数据
                et_search.setText("");
                if(name.length()==0){
                    Toast.makeText(getActivity(), "搜索不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    //把搜索记录保存到共享参数里
                    if(SaveMsg.getCheckInfo(getActivity()).size()<10){//只保存十条查询记录
                        set.add(name);
                        SaveMsg.saveCheckInfo(getActivity(),set);
                    }
                    showInfo(name);
                }
                break;
            case R.id.search_et_text://点了搜索输入框
                //并且没有输入文字才显示历史记录
                if(et_search.getText().length()<=0){
                    showHistory();
                }
                break;
        }
    }

    //显示历史记录
    public void showHistory(){
        rl_hide.setVisibility(View.VISIBLE);//显示历史记录框
        tv_set.setText("历史记录：");
        jilu_list=SaveMsg.getCheckInfo(getActivity());//取数据
        if(jilu_list.size()==0){//没有历史记录
            jilu_lv.setVisibility(View.GONE);//隐藏listview
            tv_hide_info.setVisibility(View.VISIBLE);//tv显示，提示没有记录
            tv_hide_info.setText("没有搜索记录");
        }else{//有记录，刷新适配器
            tv_hide_info.setVisibility(View.GONE);
            jilu_lv.setVisibility(View.VISIBLE);//显示listview
            jiluAdapter.setDataToAdapter(jilu_list);
            jiluAdapter.notifyDataSetChanged();
        }
    }

    //隐藏历史记录或搜索信息
    public void hideHistoryOrSearch(){
        rl_hide.setVisibility(View.GONE);//隐藏历史记录框
    }

    //显示模糊搜索信息
    public void showSearch(String str){
        rl_hide.setVisibility(View.VISIBLE);//显示历史记录框
        tv_set.setText("是否要查询：");
        jilu_list=hb.mohuCheckName(str);
        if(jilu_list.size()==0){
            jilu_lv.setVisibility(View.GONE);//隐藏listview
            tv_hide_info.setVisibility(View.VISIBLE);
            tv_hide_info.setText("没有匹配到与此有关的数据");
        }else{
            tv_hide_info.setVisibility(View.GONE);
            jilu_lv.setVisibility(View.VISIBLE);//显示listview
            jiluAdapter.setDataToAdapter(jilu_list);
            jiluAdapter.notifyDataSetChanged();
        }
    }
    //显示找到的信息
    public void showInfo(String name){
        show_list=hb.mohuCheck(name);//去数据库里根据title模糊插叙
        if(show_list.size()>0){//查询到了，刷新适配器
            tv_show_info.setVisibility(View.GONE);//隐藏提示用户的信息
            show_lv.setVisibility(View.VISIBLE);//显示listview
            showAdapter.setDataToAdapter(show_list);
            showAdapter.notifyDataSetChanged();
        }else{//没有查询到
            show_lv.setVisibility(View.GONE);//隐藏listview
            tv_show_info.setVisibility(View.VISIBLE);//显示提示用户，没有查询到
        }
    }
}


