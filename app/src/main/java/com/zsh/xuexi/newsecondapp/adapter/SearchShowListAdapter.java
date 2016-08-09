package com.zsh.xuexi.newsecondapp.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.base.MyBaseAdapter;
import com.zsh.xuexi.newsecondapp.entity.HotInfo;
import com.zsh.xuexi.newsecondapp.utils.VolleySingleton;

import static com.zsh.xuexi.newsecondapp.R.id.lvitem_two_tv_time;

/**
 * Created by zsh on 2016/7/21.
 */
public class SearchShowListAdapter extends MyBaseAdapter<HotInfo> {
    public SearchShowListAdapter(Context cont) {
        super(cont);
    }

    @Override
    public View getItemView(int position, View view, ViewGroup parent) {
        ViewHolder vh=null;
        if(view==null){
            vh=new ViewHolder();
            view= LayoutInflater.from(cont).inflate(R.layout.listview_item_two,null);
            vh.iv_icon= (ImageView) view.findViewById(R.id.lvitem_two_iv);
            vh.tv_name= (TextView) view.findViewById(R.id.lvitem_two_tv_text);
            vh.tv_date= (TextView) view.findViewById(R.id.lvitem_two_tv_time);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        HotInfo hi=getDataList().get(position);
        vh.tv_name.setText(hi.getTitle());
        vh.tv_date.setText(hi.getDate());
        String img_url=hi.getUrl();
        if(img_url.equals("null")){
            vh.iv_icon.setBackgroundResource(R.mipmap.icon);
        }else{
            AddBitmap(img_url,vh.iv_icon);
        }
        return view;
    }
    class ViewHolder{
        ImageView iv_icon;
        TextView tv_name,tv_date;
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
