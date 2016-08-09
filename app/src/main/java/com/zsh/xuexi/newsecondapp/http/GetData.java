package com.zsh.xuexi.newsecondapp.http;

import android.os.Handler;
import android.os.Message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zsh on 2016/6/25.
 */
public class GetData {
    static String result;
    static String newsname;
    static List<HashMap<String,Object>> list;
    static String path="http://op.juhe.cn/onebox/news/query?";
    static String key="7750c169d909941b1298be322fbe8299";
    public  static void getResultByHttpGet(String kaysname,int position){//newsname关键字查询，position 第几个news导航
        try {
            newsname= URLEncoder.encode(kaysname, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String pathhttpget=path;
        StringBuffer sb=new StringBuffer(pathhttpget);
        sb.append("key="+key);//把Key添加
        sb.append("&q="+newsname);//添加查询的关键字
        pathhttpget=sb.toString();
        loadData(pathhttpget,position);
    }
    public static void loadData(final String path, final int position){
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (position){
                    case 1:
                    result= HttpConnection.getDataByHttpGet(path);
                    break;
                }
                handler.sendEmptyMessage(position);
            }
        }) .start();
    }

    public static  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                     list=Jiexi.JXJSON(result);
//                    Constellation_Fragment.handler.sendEmptyMessage(1);
                    break;
            }
        }
    };

    public static List<HashMap<String, Object>> getList() {
        return list;
    }

    public static void setList(List<HashMap<String, Object>> list) {
        GetData.list = list;
    }

    public static String getResult() {
        return result;
    }

    public static void setResult(String result) {
        GetData.result = result;
    }
}
