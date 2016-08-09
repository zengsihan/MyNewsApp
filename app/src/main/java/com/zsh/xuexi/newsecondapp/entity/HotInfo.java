package com.zsh.xuexi.newsecondapp.entity;

import java.io.Serializable;

/**
 * Created by zsh on 2016/7/14.
 */
public class HotInfo implements Serializable{
    private String name;//自己定义它的类型
    private String title;//新闻标题
    private String date;//发布时间
    private String url;//新闻链接
    private String img1;//图片一

    public HotInfo() {
    }

    public HotInfo(String name, String title, String date, String url, String img1) {
        this.name = name;
        this.title = title;
        this.date = date;
        this.url = url;
        this.img1 = img1;
    }

    @Override
    public String toString() {
        return "HotInfo{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", img1='" + img1 + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }
}
