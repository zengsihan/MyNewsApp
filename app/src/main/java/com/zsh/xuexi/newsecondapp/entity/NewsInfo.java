package com.zsh.xuexi.newsecondapp.entity;

import java.io.Serializable;

/**
 * Created by zsh on 2016/7/18.
 */
public class NewsInfo implements Serializable {
    private String channelName;//news类型
    private String title;//标题
    private String content;//内容
    private String date;//时间
    private String img;//图片
    private String url;//链接
    private String nid;//唯一标识

    public NewsInfo() {
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    @Override
    public String toString() {
        return "NewsInfo{" +
                "channelName='" + channelName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                ", nid='" + nid + '\'' +
                '}';
    }

    public NewsInfo(String channelName, String title, String content, String date, String img, String url, String nid) {
        this.channelName = channelName;
        this.title = title;
        this.content = content;
        this.date = date;
        this.img = img;
        this.url = url;
        this.nid = nid;
    }
}
