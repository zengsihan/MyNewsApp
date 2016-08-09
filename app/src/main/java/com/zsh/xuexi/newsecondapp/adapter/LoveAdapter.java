package com.zsh.xuexi.newsecondapp.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.base.MyBaseAdapter;
import com.zsh.xuexi.newsecondapp.entity.HotInfo;
import com.zsh.xuexi.newsecondapp.utils.VolleySingleton;

import java.util.List;

/**
 * Created by zsh on 2016/7/20.
 */
public class LoveAdapter extends BaseAdapter{
    Context cont;
    List<HotInfo> list;

    public LoveAdapter(Context cont, List<HotInfo> list) {
        this.cont = cont;
        this.list = list;
    }

    public List<HotInfo> getList() {
        return list;
    }

    public void setList(List<HotInfo> list) {
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
            view= LayoutInflater.from(cont).inflate(R.layout.item_love,null);
            vh.icon= (ImageView) view.findViewById(R.id.lvitem_two_iv);
            vh.info= (TextView) view.findViewById(R.id.lvitem_two_tv_text);
            vh.time= (TextView) view.findViewById(R.id.lvitem_two_tv_time);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        HotInfo hi=list.get(position);
        String pic_url=hi.getImg1();//图片地址
        if(pic_url.equals("null")){
            vh.icon.setBackgroundResource(R.mipmap.icon);
        }else{
            AddBitmap(pic_url,vh.icon);
        }
        vh.info.setText(hi.getTitle());
        vh.time.setText(hi.getDate());
        return view;
    }
    class ViewHolder{
        ImageView icon;
        TextView info,time;
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
