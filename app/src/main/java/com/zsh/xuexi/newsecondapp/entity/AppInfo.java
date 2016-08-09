package com.zsh.xuexi.newsecondapp.entity;

import android.content.pm.PackageInfo;

import java.io.Serializable;

/**
 * Created by zsh on 2016/5/24.
 */
public class AppInfo implements Serializable{
    private PackageInfo packageInfo;
    private boolean isChecked;

    public AppInfo(PackageInfo packageInfo, boolean isChecked) {
        this.packageInfo = packageInfo;
        this.isChecked = isChecked;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "packageInfo=" + packageInfo +
                ", isChecked=" + isChecked +
                '}';
    }
}
