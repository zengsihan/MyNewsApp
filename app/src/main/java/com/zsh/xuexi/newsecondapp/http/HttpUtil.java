package com.zsh.xuexi.newsecondapp.http;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

/**
 * Created by zsh on 2016/7/18.
 */
public class HttpUtil {
    public static final String URL="http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
    public static final String KEY="8072262bba176862d62a8f421aba005c";

    //编码格式转换
    public static String exchangeCodeType(String string,String codeType){
        String str=null;
        try {
            str= URLEncoder.encode(string,codeType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  str;
    }
    //头部拼接
    public static String appendHand(String url,String name,String data){
        String str=null;
        StringBuffer sb=new StringBuffer(url);
        sb.append("?"+name+"="+data);
        str=sb.toString();
        return str;
    }

    //数据拼接
    public static String appendUrl(String url,String name,String data){
        String str=null;
        StringBuffer sb=new StringBuffer(url);
        sb.append("&"+name+"="+data);
        str=sb.toString();
        return str;
    }



}

