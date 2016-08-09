package com.zsh.xuexi.newsecondapp.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.base.BaseActivity;
import com.zsh.xuexi.newsecondapp.db.HotDB;
import com.zsh.xuexi.newsecondapp.db.LoveDB;
import com.zsh.xuexi.newsecondapp.entity.HotInfo;

/**
 * Created by zsh on 2016/7/20.
 */
public class WebActivity extends BaseActivity implements View.OnClickListener {
    WebView wb;
    FloatingActionButton fbtn;
    String url;
    ProgressBar pbar;
    HotDB hb;
    LoveDB lb;
    HotInfo hi;
//    boolean f=false;//判断是否点了收藏
    @Override
    public void setLaout() {
        setContentView(R.layout.web_activity);
    }

    @Override
    public void getData() {
        hb=new HotDB(this);
        lb=new LoveDB(this);
        hi=new HotInfo();
        Intent i=this.getIntent();
        url=i.getStringExtra("url");
    }

    @Override
    public void getView() {
        wb= (WebView) findViewById(R.id.web_web);
        fbtn= (FloatingActionButton) findViewById(R.id.web_actionbtn);
        pbar= (ProgressBar) findViewById(R.id.web_progressbar);
    }

    @Override
    public void setView() {
        wb.loadUrl(url);
        fbtn.setOnClickListener(this);
        wb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    pbar.setVisibility(View.INVISIBLE);
                }else{
                    if(View.INVISIBLE==pbar.getVisibility()){
                        pbar.setVisibility(View.VISIBLE);
                    }
                    pbar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.web_actionbtn:
    //          Toast.makeText(WebActivity.this, "点了收藏", Toast.LENGTH_SHORT).show();
                if(lb.isHaveThisNews(url)){
                    Snackbar.make(fbtn,"已经收藏",Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(fbtn,"收藏成功",Snackbar.LENGTH_LONG).show();
                    hi=hb.findOne(url);
                    lb.addNews(hi);
                }
            break;
        }
    }
}
