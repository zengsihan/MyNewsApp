package com.zsh.xuexi.newsecondapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zsh on 2016/7/14.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    public MyDBHelper(Context context) {
        super(context, "my", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table hot(id integer PRIMARY KEY autoincrement,name text,title text,date text,url text,img1 text)");
        db.execSQL("create table love(id integer PRIMARY KEY autoincrement,name text,title text,date text,url text,img1,text)");
        db.execSQL("create table newslist(id integer PRIMARY KEY autoincrement,name text,ename text,isShow text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
