package com.zsh.xuexi.newsecondapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2016/7/13.
 */
public class AddListViewAdapter extends BaseAdapter {
    Context cont;
    List<String> list;

    public AddListViewAdapter(Context cont) {
        this.cont = cont;
        list=new ArrayList<String>();
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vh=null;
        if(view==null){
            vh=new ViewHolder();
            view= LayoutInflater.from(cont).inflate(R.layout.add_listview_item,null);
            vh.tv= (TextView) view.findViewById(R.id.add_listview_item_tv);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        vh.tv.setText(list.get(position));
        return view;
    }
    class ViewHolder{
        TextView tv;
    }
}
