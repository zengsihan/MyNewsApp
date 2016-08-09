package com.zsh.xuexi.newsecondapp.http;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

/**
 * Created by lin on 2016/6/14.
 */
public class HttpConnection {
    /**
     * HttpUrlConnection GET请求方式
     */
    public static String getDataByHttpGet(String path){
        String result=null;
        StringBuffer sb=new StringBuffer();
        try {
            URL url=new URL(path);//建立一个URL
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();//通过通道打开连接
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            if(connection.getResponseCode()==200){//获得返回码 200代表成功
                InputStream in=connection.getInputStream();//连接获得流
                BufferedInputStream bis=new BufferedInputStream(in);
                byte[]b =new byte[1024];
                int len=0;
                while((len=bis.read(b))!=-1){
                    sb.append(new String(b,0,len));
                }
                result=sb.toString();
            }else {
                result="error";
            }
        } catch (MalformedURLException e) {
            Log.i("ms","http-MalformedURLException");
            result="error";
            e.printStackTrace();
        } catch (UnknownHostException e){
            Log.i("ms","http-UnknownHostException");
            result="error";
        }
        catch (IOException e) {
            Log.i("ms","http-IOException");
            result="error";
            e.printStackTrace();
        }
        Log.i("ms","http-结束");
        return result;
    }
    /**
     * Http  Post 请求方式
     */
    public static String getDataByHttpPost(String path,String menuname){
        String result=null;
        StringBuffer sb=new StringBuffer();
        try {
            URL url=new URL(path);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            String data="key=179acad6a13d1ae6284c6294c3b3ac8f"+"&menu="+ URLEncoder.encode(menuname,"utf-8");
            byte []b=data.getBytes();
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",String.valueOf(b.length));
            OutputStream out=connection.getOutputStream();
            out.write(b);//把数据发送过去
            if(connection.getResponseCode()==200){//获得返回码 200代表成功
                InputStream in=connection.getInputStream();//连接获得流
                BufferedInputStream bis=new BufferedInputStream(in);
                byte[]b1 =new byte[1024];
                int len=0;
                while((len=bis.read(b1))!=-1){
                    sb.append(new String(b1,0,len));
                }
                result=sb.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
        return result;
    }

}
