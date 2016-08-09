package com.zsh.xuexi.newsecondapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zsh.xuexi.newsecondapp.entity.HotInfo;
import com.zsh.xuexi.newsecondapp.entity.NewsListInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2016/7/14.
 */
public class NewsListDB {
    Context cont;
    SQLiteDatabase db;

    public NewsListDB(Context cont){
        this.cont=cont;
        MyDBHelper myDBHelper=new MyDBHelper(cont);
        db=myDBHelper.getReadableDatabase();
    }

    /**
     * 判断是否有这个标题
     * @param name 名字
     * @return boolean
     */
    public boolean isHaveThisTitle(String name){
        Cursor cursor=null;
        cursor=db.rawQuery("select * from newslist where name=?",new String[]{name});//查找
        if(cursor!=null){//通过游标判断
            if(cursor.moveToNext()){
                return true;
            }
        }
        return false;
    }

    /**
     * 添加
     * @param
     */
    public void addTitle(NewsListInfo li){
        ContentValues cv=new ContentValues();
        cv.put("name",li.getName());
        cv.put("ename",li.getEname());
        cv.put("isShow",li.getIsShow());
        db.insert("newslist", null, cv);
    }
    /**
     * 修改
     */
    public void update(String name,NewsListInfo li){
        ContentValues cv=new ContentValues();
        cv.put("name",li.getName());
        cv.put("isShow",li.getIsShow());
        db.update("newslist", cv, "name=?", new String[]{name});
    }

    /**
     * 查询一个
     * @param
     * @return
     */
    public NewsListInfo findOne(String name1){
        Cursor cursor=null;
        NewsListInfo li=null;
        cursor=db.rawQuery("select * from newslist where name=?",new String[]{name1});
        if(cursor!=null){
            if (cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String ename=cursor.getString(cursor.getColumnIndex("ename"));
                String isShow=cursor.getString(cursor.getColumnIndex("isShow"));
                li=new NewsListInfo(name,ename,isShow);
            }
        }
        return li;
    }

    /**
     * 查所有
     * @return
     */
    public List<NewsListInfo> findAll(){
        List<NewsListInfo> list=new ArrayList<NewsListInfo>();
        Cursor cursor=null;
        cursor=db.rawQuery("select * from newslist",null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String ename=cursor.getString(cursor.getColumnIndex("ename"));
                String isShow=cursor.getString(cursor.getColumnIndex("isShow"));
                NewsListInfo li=new NewsListInfo(name,ename,isShow);
                list.add(li);
            }
        }
        return list;
    }
    /**
     * 删除一个
     * @param
     */
    public void deleteOne(String name){
        db.delete("newslist","name=?",new String[]{name});
    }

    /**
     * 删除全部
     */
    public void deleteAll(){
        db.delete("newslist",null,null);
    }

}
