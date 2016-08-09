package com.zsh.xuexi.newsecondapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.entity.HotInfo;
import com.zsh.xuexi.newsecondapp.http.Jiexi;
import com.zsh.xuexi.newsecondapp.utils.VolleySingleton;
import com.zsh.xuexi.newsecondapp.view.RippleView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by zsh on 2016/7/6.
 */
public class HotAdapter extends RecyclerView.Adapter<HotAdapter.MyViewHolder> {
    Context cont;
    List<HotInfo> list;
    HotInfo hi;
    private RecycleViewClickListener rvlistenner = null;

    public HotAdapter(Context cont) {
        this.cont = cont;
        list=new ArrayList<HotInfo>();
    }

    public List<HotInfo> getList() {
        return list;
    }

    public void setList(List<HotInfo> list) {
        this.list = list;
    }

    public RecycleViewClickListener getRvlistenner() {
        return rvlistenner;
    }

    public void setRvlistenner(RecycleViewClickListener rvlistenner) {
        this.rvlistenner = rvlistenner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cont).inflate(R.layout.hot_item, parent, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Random rd=new Random();
        int width=rd.nextInt(50)+80;
        int height=rd.nextInt(30)+50;
        holder.iv_img.setMaxWidth(width);
        holder.iv_img.setMaxHeight(height);
//         width=rd.nextInt(100)+80;
//         height=rd.nextInt(80)+50;
//        holder.tv_text.setWidth(width);
//        holder.tv_text.setHeight(height);
        float f=rd.nextInt(10)+12;
        holder.tv_text.setTextSize(f);

        hi = list.get(position);
        holder.tv_text.setText(hi.getTitle());
        String img_url=hi.getImg1();//图片地址
        AddBitmap(img_url,holder.iv_img);

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
        return list.size();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_text;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.hot_item_iv);
            tv_text = (TextView) itemView.findViewById(R.id.hot_item_tv);
        }

    }

    //定义一个接口，用于实现item的短按，长按监听，因为recycleview没有自带的
    public interface RecycleViewClickListener {
        void OnItemClickListener(View view, int position);
        void OnItemLongClickListener(View view, int position);
    }

    //增加item的方法
    public void addItem(int position,  HotInfo hi) {
        list.add(position, hi);
        notifyItemInserted(position);
    }

    //删除item的方法
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void AddBitmap( String url, final ImageView iv){
//         url="http://dl.bizhi.sogou.com/images/2013/10/30/396486.jpg";
        ImageRequest request=new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {//加载成功
//                        imglist.add(bitmap);
                        iv.setImageBitmap(bitmap);
                    }
                }, 0, 0, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {//加载失败
                        //默认的图片
//                        Bitmap bitmap=BitmapFactory.decodeResource(cont.getResources(),R.mipmap.ic_launcher);
//                        imglist.add(bitmap);
                        iv.setImageResource(R.mipmap.ic_launcher);
                    }
                }
        );
        VolleySingleton.getVolleySingleton(cont).addToRequestQueue(request);
    }

}

