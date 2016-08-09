package com.zsh.xuexi.newsecondapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.entity.AppInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2016/6/24.
 */
public class Tools {
    public static int color=R.color.topbar_bg;//用于换toolbar颜色
    public static boolean f=false;//用于保存开关状态
    public static boolean ff=true;//用户判断是否更新news页面
    /**
     * 强制满屏
     * @param a 表示Activity
     */
    public static void fullActivity(Activity a){
        a.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    /**
     * 拿所有的app信息
     * @param cont
     * @return 所有的app信息list
     */
    public  static List<AppInfo> getAllList(Context cont){
        PackageManager packageManager=cont.getPackageManager();
        List<AppInfo> list=new ArrayList<AppInfo>();
        List<PackageInfo> plist=packageManager.getInstalledPackages(0);//拿到所有的app信息
        for(int i=0;i<plist.size();i++){
            list.add(new AppInfo(plist.get(i),false));//加上是否被选中的状态，全部给APplist
        }
        return list;
    }

    /**
     * 拿到用户安装的app信息
     * @param list
     * @return
     */
    public static List<AppInfo> getuserAppList(List<AppInfo> list){
        List<AppInfo> userlist=new ArrayList<AppInfo>();
        for(int i=0;i<list.size();i++){
            PackageInfo packageInfo=list.get(i).getPackageInfo();
            if((packageInfo.applicationInfo.flags&packageInfo.applicationInfo.FLAG_SYSTEM)<=0){//如果是用户安装的
                userlist.add(list.get(i));
            }
        }
        return userlist;
    }

    /**
     * 拿所有的系统app信息
     * @param list
     * @return
     */
    public static List<AppInfo> getSystemAppList(List<AppInfo> list){
        List<AppInfo> slist=new ArrayList<AppInfo>();
        for(int i=0;i<list.size();i++){
            PackageInfo packageInfo=list.get(i).getPackageInfo();
            if((packageInfo.applicationInfo.flags&packageInfo.applicationInfo.FLAG_SYSTEM)>0){//如果是用户安装的
                slist.add(list.get(i));
            }
        }
        return slist;
    }

    /**
     * 获取版本号
     */
    public static  String getVersionCode(Context mC){
        PackageManager pm=mC.getPackageManager();
        PackageInfo pi=null;
        try {
            pi =pm.getPackageInfo(mC.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi.versionName;
//        pi.versionName;//版本名
    }
    /**
     * 获取App名称
     */
    public static String getAppname(Context mC){
        ApplicationInfo ai=mC.getApplicationInfo();
        int i=ai.labelRes;
        return mC.getResources().getString(i);
    }
    /**
     * 得到字符串方式的文件大小
     *
     * @param filesize
     *            ,单位b
     * @return
     */
    private static DecimalFormat df = new DecimalFormat("#.00");
    /**单位换算*/
    public static String getFileSize(long filesize) {
        StringBuffer mstrbuf = new StringBuffer();
        if (filesize < 1024) {
            mstrbuf.append(filesize);
            mstrbuf.append(" B");
        } else if (filesize < 1048576) {
            mstrbuf.append(df.format((double) filesize / 1024));
            mstrbuf.append(" K");
        } else if (filesize < 1073741824) {
            mstrbuf.append(df.format((double) filesize / 1048576));
            mstrbuf.append(" M");
        } else {
            mstrbuf.append(df.format((double) filesize / 1073741824));
            mstrbuf.append(" G");
        }
        return mstrbuf.toString();
    }
}
