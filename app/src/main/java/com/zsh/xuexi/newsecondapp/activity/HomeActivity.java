package com.zsh.xuexi.newsecondapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.base.MyApplication;
import com.zsh.xuexi.newsecondapp.db.HotDB;
import com.zsh.xuexi.newsecondapp.db.NewsListDB;
import com.zsh.xuexi.newsecondapp.entity.HotInfo;
import com.zsh.xuexi.newsecondapp.entity.NewsListInfo;
import com.zsh.xuexi.newsecondapp.fragment.HotFragment;
import com.zsh.xuexi.newsecondapp.fragment.NewsFragment;
import com.zsh.xuexi.newsecondapp.fragment.SearchFragment;
import com.zsh.xuexi.newsecondapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by zsh on 2016/6/19.
 * 主页
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_news,iv_hot,iv_search;
    TextView tv_news,tv_hot,tv_serch;
    RelativeLayout rl_news,rl_hot,rl_search,rl_black;
    NewsFragment newsFragment;
    HotFragment hotFragment;
    SearchFragment searchFragment;
    FragmentManager fragmentManager;

    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    OnekeyShare oks;//分享的类
    RelativeLayout rl_login,rl_colction,rl_aboutus,rl_setting;

    HotDB hb;
    List<HotInfo> list;
    NewsListDB nb;
    List<NewsListInfo> nlist;
    CircleImageView circleImageView;
    boolean Login_Statce;
    TextView tv_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLaout();
        getView();
        setView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setBackgroundResource(Tools.color);
    }


    public void setLaout() {
        setContentView(R.layout.home_activity);
        ShareSDK.initSDK(this);
        oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("每天都有新资讯");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("下载我！每天都离万事通更近一点！");
        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("http://sharesdk.cn");
    }

    public void getView() {
        hb=new HotDB(this);
        list=new ArrayList<HotInfo>();
        nb=new NewsListDB(this);
        nlist=new ArrayList<NewsListInfo>();
        tv_login= (TextView) findViewById(R.id.my_drawer_login);
        rl_news= (RelativeLayout) findViewById(R.id.home_rl_news);
        rl_hot= (RelativeLayout) findViewById(R.id.home_rl_hot);
        rl_search= (RelativeLayout) findViewById(R.id.home_rl_search);

        iv_news= (ImageView) findViewById(R.id.home_rl_news_iv);
        iv_hot= (ImageView) findViewById(R.id.home_rl_hot_iv);
        iv_search= (ImageView) findViewById(R.id.home_rl_search_iv);

        tv_news= (TextView) findViewById(R.id.home_rl_news_tv);
        tv_hot= (TextView) findViewById(R.id.home_rl_hot_tv);
        tv_serch= (TextView) findViewById(R.id.home_rl_search_tv);

        toolbar= (Toolbar) findViewById(R.id.tl_custom);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.dl_left);

        rl_login= (RelativeLayout) findViewById(R.id.my_drawer_rl_login);
        rl_colction= (RelativeLayout) findViewById(R.id.my_drawer_rl_colction);
        rl_aboutus= (RelativeLayout) findViewById(R.id.my_drawer_rl_aboutus);
        rl_setting= (RelativeLayout) findViewById(R.id.my_drawer_rl_setting);
        rl_black= (RelativeLayout) findViewById(R.id.my_drawer_rl_black);

        circleImageView= (CircleImageView) findViewById(R.id.my_drawer_icon);
    }

    public void setView() {
        rl_news.setOnClickListener(this);
        rl_hot.setOnClickListener(this);
        rl_search.setOnClickListener(this);

        rl_login.setOnClickListener(this);
        rl_colction.setOnClickListener(this);
        rl_aboutus.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_black.setOnClickListener(this);
        fragmentManager=getSupportFragmentManager();//得到管理器
        setTabSelection(0);//第一次启动时，开启第0个tab

//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);//设置导航栏图标，和drawer连用会被干掉
//        toolbar.setBackgroundResource(color);
        toolbar.setLogo(R.mipmap.icon);//设置app logo
        toolbar.setTitle("zshNews");//设置Toolbar标题
//        toolbar.setSubtitle("子标题");//设置子标题
//        toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单,连用后可以不要这句，有时不起作用
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);


        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);

        mDrawerToggle.syncState();//保持toolbar和drawer同步
        mDrawerLayout.setDrawerListener(mDrawerToggle);//绑定
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 为了让Toolbar 的 Menu 有作用，必须加上这句
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        return true;
    }
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int menuItemId = item.getItemId();
            if (menuItemId == R.id.action_search) {
//                Toast.makeText(HomeActivity.this, R.string.menu_search, Toast.LENGTH_SHORT).show();
                oks.show(HomeActivity.this);
            }
            return true;
        }
    };
    /**
     * 根据传入的index，来设置开启的tab页面
     * @param index 代表对应的下标
     */
    private void setTabSelection(int index){
        //清理之前的所有状态
        clearSelection();
        //开启一个Fragment事务
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //隐藏所有的fragment，防止有多个界面显示在界面上
        hideFragments(transaction);
        switch (index){
            case 0://点击资讯时，
                // 改变控件的图片和文字颜色
                iv_news.setImageResource(R.mipmap.home_news_selected);
                tv_news.setTextColor(Color.BLUE);
                //如果newsFragment为空，则创建一个添加到界面上
                if(newsFragment==null){
                    newsFragment=new NewsFragment();
                    transaction.add(R.id.my_drawer_content,newsFragment);
                }else{
                    //不为空就直接显示
                    transaction.show(newsFragment);
                }
                break;
            case 1://点击热点时
                iv_hot.setImageResource(R.mipmap.home_hot_selected);
                tv_hot.setTextColor(Color.BLUE);
                if(hotFragment==null){
                    hotFragment=new HotFragment();
                    transaction.add(R.id.my_drawer_content,hotFragment);
                }else{
                    transaction.show(hotFragment);
                }
                break;
            case 2://点击搜索时
                iv_search.setImageResource(R.mipmap.home_search_selected);
                tv_serch.setTextColor(Color.BLUE);
                if(searchFragment==null){
                    searchFragment=new SearchFragment();
                    transaction.add(R.id.my_drawer_content,searchFragment);
                }else{
                    transaction.show(searchFragment);
                }
                break;
        }
        transaction.commit();//提交
    }
    //清理之前的所有状态
    private void clearSelection(){
        iv_news.setImageResource(R.mipmap.home_news_unselected);
        tv_news.setTextColor(Color.parseColor("#000000"));
        iv_hot.setImageResource(R.mipmap.home_hot_unselected);
        tv_hot.setTextColor(Color.parseColor("#000000"));
        iv_search.setImageResource(R.mipmap.home_search_defult);
        tv_serch.setTextColor(Color.parseColor("#000000"));
    }
    //隐藏所有的fragment
    private void hideFragments(FragmentTransaction transaction){
        if(newsFragment!=null){
            transaction.hide(newsFragment);
        }
        if(hotFragment!=null){
            transaction.hide(hotFragment);
        }
        if(searchFragment!=null){
            transaction.hide(searchFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_rl_news:
                setTabSelection(0);//选中第一个tab
                break;
            case R.id.home_rl_hot:
                setTabSelection(1);
                break;
            case R.id.home_rl_search:
                setTabSelection(2);
                break;
            case R.id.my_drawer_rl_login:
//                Toast.makeText(HomeActivity.this, "登陆", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(this,LoginActivity.class);
                startActivityForResult(i, 1);
                break;
            case R.id.my_drawer_rl_colction:
//                Toast.makeText(HomeActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                Intent ii=new Intent(this,LoveActivity.class);
                startActivity(ii);
                break;
            case R.id.my_drawer_rl_aboutus:
//                Toast.makeText(HomeActivity.this, "关于我们", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,AboutusActivity.class);
                startActivity(intent);
                break;
            case R.id.my_drawer_rl_setting:
//                Toast.makeText(HomeActivity.this, "设置", Toast.LENGTH_SHORT).show();
                Intent iii=new Intent(this,SettingActivity.class);
                startActivity(iii);
                break;
            case R.id.my_drawer_rl_black://用来强焦点的
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK://当退出时，保存十条数据
                nlist=nb.findAll();
                Log.i("ms","点击退出");
                for(int i=0;i<nlist.size();i++){
                    Log.i("ms","第一重for循环"+i);
                    if (nlist.get(i).getIsShow().equals("true")){//标签显示出来了
                        list=hb.findOneType(nlist.get(i).getName());//拿到数据库的数据。
                        Log.i("ms","拿"+nlist.get(i).getName()+"de数据"+"listsize="+list.size()+"-"+list.toString());
                        if(list.size()>10){
                            Log.i("ms","i"+i+"开始删除数据");
                            hb.deleteOneType(nlist.get(i).getName());//删除数据
                            Log.i("ms", "i"+i+"删除完毕");
                        }
                        //添加十条数据
                        if(list.size()>10&&!hb.isHaveThisNews(nlist.get(i).getName())){
                            Log.i("ms", "i"+i+"添加数据");
                            for(int j=0;j<10;j++){
                                hb.addNews(list.get(j));
                            }
                            Log.i("ms", "i"+i+"添加完毕");
                        }
                    }
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            Login_Statce = true;
            tv_login.setText("退出登录");
        }
        if (resultCode == 3) {
            Login_Statce = true;
            tv_login.setText("退出登录");
            String icon_path = data.getStringExtra("usericon");
            ImageRequest imageRequest = new ImageRequest(
                    icon_path, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    circleImageView.setImageBitmap(bitmap);
                }
            }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    circleImageView.setImageResource(R.mipmap.login_defult_img);
                }
            });
            MyApplication.getApplication(this).addToRequestQueue(imageRequest);

        }
    }
}
