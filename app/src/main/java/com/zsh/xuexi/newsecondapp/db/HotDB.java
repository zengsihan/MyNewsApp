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
public class HotDB {
    Context cont;
    SQLiteDatabase db;

    public HotDB(Context cont){
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
        cursor=db.rawQuery("select * from hot where url=?",new String[]{url1});//查找
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
        db.insert("hot",null,cv);
    }
    /**
     * 修改
     */
    public void update(String uniquekey,HotInfo hi){
        ContentValues cv=new ContentValues();
        cv.put("name",hi.getName());
        cv.put("title",hi.getTitle());
        cv.put("date",hi.getDate());
        cv.put("url",hi.getUrl());
        cv.put("img1",hi.getImg1());
        db.update("hot", cv, "uniquekey=?", new String[]{uniquekey});
    }

    public List<String> mohuCheckName(String title1){
        List<String> list=new ArrayList<String>();
        Cursor cursor=null;
        cursor=db.rawQuery("select * from hot where title like ?",new String[]{'%'+title1+'%'});//Like代表通配符
//        cursor=db.rawQuery("select * from hot where title like '%"+title1+"%'",null);//这个手机上不能查分词
        if(cursor!=null){
            while (cursor.moveToNext()){
                String title=cursor.getString(cursor.getColumnIndex("title"));
                list.add(title);
            }
        }
        return list;
    }

    /**
     * 模糊查询
     * @return
     */
    public List<HotInfo> mohuCheck(String title1){
        List<HotInfo> list=new ArrayList<HotInfo>();
        Cursor cursor=null;
//        cursor=db.rawQuery("select * from hot where title=?",new String[]{title1});

//        String sql=null;
//        String str="select * from hot where title like n";
//        StringBuffer sb=new StringBuffer(str);
//        sb.append("'"+"%"+title1+"%"+"'");
//        sql=sb.toString();

//        String sql="select * from hot where title like '%"+title1+"%'";
//        db.execSQL();

//        cursor=db.(sql);
//        cursor=db.rawQuery("SELECT *, title,WHERE name LIKE n'%" + title1.trim() +"%'",null);
//        cursor=db.query("hot",null,"title",null,null,"like n'%=?%'",null);

        //六哥的
        //select  * from 表名 where 列名 like ？ ,new String []{'%'+传递进来查询的值+'%'};注意用rawQuery方法只能用单引号
         cursor=db.rawQuery("select * from hot where title like ?",new String[]{'%'+title1+'%'});//Like代表通配符

//        cursor=db.rawQuery("select * from hot where title like '%"+title1+"%'",null);
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
     * 查询一个
     * @param
     * @return
     */
    public HotInfo findOne(String url1){
        Cursor cursor=null;
        HotInfo hi=null;
        cursor=db.rawQuery("select * from hot where url=?",new String[]{url1});
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
        cursor=db.rawQuery("select * from hot where name=?",new String[]{name1});
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
        cursor=db.rawQuery("select * from hot",null);
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
    public void deleteOne(String uniquekey){
        db.delete("hot","uniquekey=?",new String[]{uniquekey});
    }


    /**
     * 删除全部
     */
    public void deleteAll(){
        db.delete("hot",null,null);
    }
    //删除一种类型的新闻
    public void deleteOneType(String name){
        db.delete("hot","name=?",new String[]{name});
    }

}
