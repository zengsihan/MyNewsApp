package com.zsh.xuexi.newsecondapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zsh.xuexi.newsecondapp.entity.HotInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2016/7/14.
 */
public class LoveDB {
    Context cont;
    SQLiteDatabase db;

    public LoveDB(Context cont){
        this.cont=cont;
        MyDBHelper myDBHelper=new MyDBHelper(cont);
        db=myDBHelper.getReadableDatabase();
    }

    /**
     * 判断是否有这种类型新闻
     * @return boolean
     */
    public boolean isHaveThisNews(String url1){
        Cursor cursor=null;
        cursor=db.rawQuery("select * from love where url=?",new String[]{url1});//查找
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
    public void addNews(HotInfo hi){
        ContentValues cv=new ContentValues();
        cv.put("name",hi.getName());
        cv.put("title",hi.getTitle());
        cv.put("date",hi.getDate());
        cv.put("url",hi.getUrl());
        cv.put("img1",hi.getImg1());
        db.insert("love",null,cv);
    }
//    /**
//     * 修改
//     */
//    public void update(String uniquekey,HotInfo hi){
//        ContentValues cv=new ContentValues();
//        cv.put("name",hi.getName());
//        cv.put("title",hi.getTitle());
//        cv.put("date",hi.getDate());
//        cv.put("url",hi.getUrl());
//        cv.put("img1",hi.getImg1());
//        db.update("love", cv, "uniquekey=?", new String[]{uniquekey});
//    }

    /**
     * 查询一个
     * @param
     * @return
     */
    public HotInfo findOne(String url1){
        Cursor cursor=null;
        HotInfo hi=null;
        cursor=db.rawQuery("select * from love where url=?",new String[]{url1});
        if(cursor!=null){
            if (cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String title=cursor.getString(cursor.getColumnIndex("title"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String url=cursor.getString(cursor.getColumnIndex("url"));
                String img1=cursor.getString(cursor.getColumnIndex("img1"));
                hi=new HotInfo(name,title,date,url,img1);
            }
        }
        return hi;
    }

    /**
     * 查同一类型的新闻
     * @return
     */
    public List<HotInfo> findOneType(String name1){
        List<HotInfo> list=new ArrayList<HotInfo>();
        Cursor cursor=null;
        cursor=db.rawQuery("select * from love where name=?",new String[]{name1});
        if(cursor!=null){
            while (cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String title=cursor.getString(cursor.getColumnIndex("title"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String url=cursor.getString(cursor.getColumnIndex("url"));
                String img1=cursor.getString(cursor.getColumnIndex("img1"));
                HotInfo hi=new HotInfo(name,title,date,url,img1);
                list.add(hi);
            }
        }
        return list;
    }
    /**
     * 查所有
     * @return
     */
    public List<HotInfo> findAll(){
        List<HotInfo> list=new ArrayList<HotInfo>();
        Cursor cursor=null;
        cursor=db.rawQuery("select * from love",null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String title=cursor.getString(cursor.getColumnIndex("title"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String url=cursor.getString(cursor.getColumnIndex("url"));
                String img1=cursor.getString(cursor.getColumnIndex("img1"));
                HotInfo hi=new HotInfo(name,title,date,url,img1);
                list.add(hi);
            }
        }
        return list;
    }
    /**
     * 删除一个
     * @param
     */
    public void deleteOne(String url){
        db.delete("love","url=?",new String[]{url});
    }

    /**
     * 删除全部
     */
    public void deleteAll(){
        db.delete("love",null,null);
    }
    //删除一种类型的新闻
    public void deleteOneType(String name){
        db.delete("love","name=?",new String[]{name});
    }
}
