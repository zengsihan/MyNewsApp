package com.zsh.xuexi.newsecondapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsh.xuexi.newsecondapp.R;

import java.util.List;

/**
 * Created by zsh on 2016/7/21.
 */
public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.MyViewHolder> {
    Context cont;
    List<Integer> list;
    private RecycleViewClickListener rvlistenner=null;

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public RecycleViewClickListener getRvlistenner() {
        return rvlistenner;
    }

    public void setRvlistenner(RecycleViewClickListener rvlistenner) {
        this.rvlistenner = rvlistenner;
    }

    public SettingAdapter(Context cont, List<Integer> list) {
        this.cont = cont;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(cont).inflate(R.layout.setting_item,parent,false);
        MyViewHolder vh=new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv.setBackgroundResource(list.get(position));

        if(rvlistenner!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int ps=holder.getLayoutPosition();
                    //短按监听，具体操作在调用时重写
                    rvlistenner.OnItemClickListener(holder.itemView,ps);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int ps=holder.getLayoutPosition();
                    rvlistenner.OnItemLongClickListener(holder.itemView,ps);
                    return true;//改为true，才能实现短按长按都监听，
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.setting_item_tv);
        }
    }

    //定义一个接口，用于实现item的短按，长按监听，因为recycleview没有自带的
    public interface RecycleViewClickListener{
        void OnItemClickListener(View view,int position);
        void OnItemLongClickListener(View view,int position);
    }
}
