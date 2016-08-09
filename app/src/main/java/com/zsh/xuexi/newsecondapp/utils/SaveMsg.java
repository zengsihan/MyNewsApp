package com.zsh.xuexi.newsecondapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by zsh on 2016/6/24.
 */
public class SaveMsg {
    /**
     * 保存是否首次进入APP
     *
     * @param context
     */
    public static void setIsFirstInApp(Context context) {
        SharedPreferences spf = context.getSharedPreferences("isfirstInApp", Context.MODE_PRIVATE);//声明对象
        SharedPreferences.Editor editor = spf.edit();//获得操作体
        editor.putBoolean("first", false);//保存数据
        editor.commit();//提交
    }

    /**
     * 获得是否首次进入APP
     *
     * @param context
     * @return
     */
    public static boolean getIsFristInApp(Context context) {
        SharedPreferences spf = context.getSharedPreferences("isfirstInApp", Context.MODE_PRIVATE);
        return spf.getBoolean("first", true);//取出共享参数的数据
    }



    /**
     * 保存搜索数据
     * @param set
     */
    public static void saveCheckInfo(Context cont,Set<String> set){
        SharedPreferences sharedPreferences=cont.getSharedPreferences("check",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=sharedPreferences.edit();
        ed.putStringSet("newstag", set);
//        Log.i("Tag","贡献参数添加"+set.toString());
        ed.commit();
    }
    /**
     * 取出新闻标签
     */
    public static ArrayList<String> getCheckInfo(Context cont){
        Set<String> set=null;
        SharedPreferences sharedPreferences=cont.getSharedPreferences("check",Context.MODE_PRIVATE);

        set=sharedPreferences.getStringSet("newstag",null);

        ArrayList<String> list=new ArrayList<>();
        if(set!=null){
            for(String s:set){
                list.add(s);
            }
        }
        return list;
    }

}
