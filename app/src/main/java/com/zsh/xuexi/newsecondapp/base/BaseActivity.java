package com.zsh.xuexi.newsecondapp.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zsh.xuexi.newsecondapp.utils.Tools;


/**
 * Created by zsh on 2016/6/19.
 */
public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.fullActivity(this);//强制满屏
        setLaout();
        getData();
        getView();
        setView();
    }
    public abstract void setLaout();//加载布局
    public abstract void getData();//加载数据
    public abstract void getView();//得到控件
    public abstract void setView();//对控件的操作
    /**
     * 启动跳转
     * @param c 目标Activity
     */
    public void startActivity(Class c){
        Intent intent=new Intent(this,c);
        startActivity(intent);
    }

    /**
     * 启动跳转
     * @param c 目标Activity
     * @param inId  页面载入动画
     * @param outId 页面退出动画
     */
    public void startActivity(Class c, int inId, int outId){
        Intent intent=new Intent(this,c);
        startActivity(intent);
        overridePendingTransition(inId,outId);
    }

    /**
     * 带参数传递的跳转方法
     * @param c 目标Activity
     * @param inId  页面载入动画
     * @param outId 页面退出动画
     * @param bundle 参数传递体
     */
    public void startActivity(Class c, int inId, int outId,Bundle bundle){
        Intent intent=new Intent(this,c);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(inId,outId);
    }
}
