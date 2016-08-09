package com.zsh.xuexi.newsecondapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.base.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2016/7/21.
 */
public class SearchJiluAdapter extends MyBaseAdapter<String> {
    public SearchJiluAdapter(Context cont) {
        super(cont);
    }

    @Override
    public View getItemView(int position, View view, ViewGroup parent) {
        ViewHolder vh=null;
        if(view==null){
            vh=new ViewHolder();
            view= LayoutInflater.from(cont).inflate(R.layout.search_jilu_item,null);
            vh.tv= (TextView) view.findViewById(R.id.search_jilu_item_tv);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        String name=getDataList().get(position);
        vh.tv.setText(name);
        return view;
    }
    class  ViewHolder{
        TextView tv;
    }
}
