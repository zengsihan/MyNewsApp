package com.zsh.xuexi.newsecondapp.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.entity.HotInfo;
import com.zsh.xuexi.newsecondapp.utils.VolleySingleton;
import com.zsh.xuexi.newsecondapp.view.RippleView;

import java.util.ArrayList;
import java.util.List;

import static com.zsh.xuexi.newsecondapp.R.id.item_rippleview;

/**
 * Created by zsh on 2016/6/20.
 */
public class ConstellationAdapter extends RecyclerView.Adapter {
    public final int ITEM_HEAD=0;
    public final int ITEM_MAIN=1;
    public final int ITEM_FOOT=2;
    public int load_more_state=0;//加载状态的标签
    Context cont;
    List<HotInfo> list;
    private RecycleViewClickListener rvlistenner = null;

    public RecycleViewClickListener getRvlistenner() {
        return rvlistenner;
    }

    public void setRvlistenner(RecycleViewClickListener rvlistenner) {
        this.rvlistenner = rvlistenner;
    }

    public List<HotInfo> getList() {
        return list;
    }

    public void setList(List<HotInfo> list) {
        this.list = list;
    }

    public ConstellationAdapter(Context cont) {
        this.cont = cont;
        list=new ArrayList<HotInfo>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_HEAD){
            HeadViewHolder headViewHolder=new HeadViewHolder(LayoutInflater.from(cont).inflate(R.layout.item_head,parent,false));
            return headViewHolder;
        }else if(viewType==ITEM_FOOT){
            FootViewHolder footViewHolder=new FootViewHolder(LayoutInflater.from(cont).inflate(R.layout.item_foot,parent,false));
            return footViewHolder;
        }else{
            MainViewHolder mainViewHolder=new MainViewHolder(LayoutInflater.from(cont).inflate(R.layout.listview_item_two,parent,false));
            return  mainViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeadViewHolder){
            if(list.size()==0){
                ((HeadViewHolder) holder).iv.setBackgroundResource(R.mipmap.news_icon_refresh);
                ((HeadViewHolder) holder).tv.setText("头部");
            }else{
                HotInfo hi=list.get(position);
                String pic_url=hi.getImg1();//图片地址
                if(pic_url.equals("null")){
                    ((HeadViewHolder) holder).iv.setBackgroundResource(R.mipmap.news_icon_refresh);
                }else{
                    AddBitmap(pic_url, ((HeadViewHolder) holder).iv);
                }
                ((HeadViewHolder) holder).tv.setText(hi.getTitle());
            }
        }else if(holder instanceof FootViewHolder){
            switch (load_more_state){
                case 0:
                    ((FootViewHolder) holder).tv.setText("加载更多");
                case 1:
                    break;
                case 2:
                    ((FootViewHolder) holder).tv.setText("加载中");
            }
        }else if(holder instanceof MainViewHolder){
            HotInfo hi=list.get(position);
            String pic_url=hi.getImg1();//图片地址
            if(pic_url.equals("null")){
                ((MainViewHolder) holder).icon.setBackgroundResource(R.mipmap.icon);
            }else{
                AddBitmap(pic_url,((MainViewHolder) holder).icon);
            }
            ((MainViewHolder) holder).info.setText(hi.getTitle());
            ((MainViewHolder) holder).time.setText(hi.getDate());
        }

        if(rvlistenner!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int ps=holder.getLayoutPosition();
                    rvlistenner.OnItemClickListener(holder.itemView,ps);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int ps=holder.getLayoutPosition();
                    rvlistenner.OnItemLongClickListener(holder.itemView,ps);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }
    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return  ITEM_HEAD;
        }else if(position==list.size()){
            return ITEM_FOOT;
        }else{
            return ITEM_MAIN;
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        public HeadViewHolder(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.item_head_iv);
            tv= (TextView) itemView.findViewById(R.id.item_head_tv);
        }
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView info,time;
        public MainViewHolder(View itemView) {
            super(itemView);
            icon= (ImageView) itemView.findViewById(R.id.lvitem_two_iv);
            info= (TextView) itemView.findViewById(R.id.lvitem_two_tv_text);
            time= (TextView) itemView.findViewById(R.id.lvitem_two_tv_time);
        }
    }
    class FootViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public FootViewHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.shuaxin_foot_tv);
        }
    }
    public  void changeState(int state){
        load_more_state=state;
        notifyItemChanged(list.size());
    }
    //定义一个接口，用于实现item的短按，长按监听，因为recycleview没有自带的
    public interface RecycleViewClickListener {
        void OnItemClickListener(View view, int position);

        void OnItemLongClickListener(View view, int position);
    }

    public void AddBitmap( String url, final ImageView iv){
        ImageRequest request=new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(Bitmap bitmap) {//加载成功
//                        iv.setImageBitmap(bitmap);
                        BitmapDrawable bd=new BitmapDrawable(bitmap);
                        iv.setBackground(bd);
                    }
                }, 0, 0, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {//加载失败
                        iv.setBackgroundResource(R.mipmap.ic_launcher);
                    }
                }
        );
        VolleySingleton.getVolleySingleton(cont).addToRequestQueue(request);
    }

}
