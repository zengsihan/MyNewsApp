package com.zsh.xuexi.newsecondapp.entity;

import java.io.Serializable;

/**
 * Created by zsh on 2016/7/14.
 */
public class NewsListInfo implements Serializable {
    private String name;//所选的标题
    private String ename;//拼音
    private String isShow;//是否显示,存true和false，

    public NewsListInfo(String name, String ename, String isShow) {
        this.name = name;
        this.ename = ename;
        this.isShow = isShow;
    }

    public NewsListInfo(String name, String isShow) {
        this.name = name;
        this.isShow = isShow;
    }

    @Override
    public String toString() {
        return "NewsListInfo{" +
                "name='" + name + '\'' +
                ", ename='" + ename + '\'' +
                ", isShow='" + isShow + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }
}
