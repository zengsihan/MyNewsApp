package com.zsh.xuexi.newsecondapp.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.zsh.xuexi.newsecondapp.R;

/**
 * Created by zsh on 2016/7/21.
 */
public class MyService extends Service {
    MediaPlayer mediaPlayer;//多媒体
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        mediaPlayer=MediaPlayer.create(this, R.raw.beyond);//实例多媒体播放器
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle=intent.getExtras();
        int a=bundle.getInt("msg");
        switch (a){
            case 0:
                if(mediaPlayer!=null&&!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);//循环播放
                }
                break;
            case 1:
                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                break;
//            case 2:
//                if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
////                    mediaPlayer.stop();
////                    mediaPlayer.release();//释放对象，在这写回报错
////                    mediaPlayer.reset();//重置，也不行
//                    mediaPlayer.pause();
//                    mediaPlayer.seekTo(0);//进度设置为0
//                }
//                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
