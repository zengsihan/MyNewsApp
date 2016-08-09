package com.zsh.xuexi.newsecondapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.base.BaseActivity;


/**
 * Created by zsh on 2016/6/24.
 */
public class LoadingActivity extends BaseActivity {

    @Override
    public void setLaout() {
        setContentView(R.layout.loading_activity);
    }

    @Override
    public void getData() {

    }

    @Override
    public void getView() {
    }

    @Override
    public void setView() {
        new Thread(new GotoHome()).start();
    }
    class GotoHome implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(2500);//线程休眠
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                handler.sendEmptyMessage(0);//handler传递数据0
            }
        }
    }
    Handler handler=new Handler(){//多线程数据传递
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0://handler接受数据0 执行对应操作
                    Intent intent=new Intent(LoadingActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.enter_alpha,R.anim.exit_alpha);
                    break;
            }
        }
    };
}
