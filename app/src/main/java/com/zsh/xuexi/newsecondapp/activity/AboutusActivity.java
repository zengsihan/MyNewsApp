package com.zsh.xuexi.newsecondapp.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.base.BaseActivity;
import com.zsh.xuexi.newsecondapp.utils.Tools;

/**
 * Created by zsh on 2016/7/13.
 */
public class AboutusActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout topbar;
    RelativeLayout rl_left;
    TextView tv_banben,tv_author;
    @Override
    public void setLaout() {
        setContentView(R.layout.aboutus_activity);
    }

    @Override
    public void getData() {

    }

    @Override
    public void getView() {
        rl_left= (RelativeLayout) findViewById(R.id.topbar_rl_left);
        tv_banben= (TextView) findViewById(R.id.aboutus_content_tv_banben);
        tv_author= (TextView) findViewById(R.id.aboutus_content_tv_author);
        topbar= (LinearLayout) findViewById(R.id.topbar_ll);
        topbar.setBackgroundResource(Tools.color);
    }

    @Override
    public void setView() {
        rl_left.setOnClickListener(this);
        tv_banben.setText("版本:"+Tools.getVersionCode(this));//拿软件版本号
        tv_author.setText("软件作者："+"zsh");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.topbar_rl_left:
                finish();
                break;
        }
    }
}
