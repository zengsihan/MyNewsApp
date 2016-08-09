package com.zsh.xuexi.newsecondapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.zsh.xuexi.newsecondapp.R;
import com.zsh.xuexi.newsecondapp.adapter.LoveAdapter;
import com.zsh.xuexi.newsecondapp.base.BaseActivity;
import com.zsh.xuexi.newsecondapp.db.LoveDB;
import com.zsh.xuexi.newsecondapp.entity.HotInfo;
import com.zsh.xuexi.newsecondapp.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsh on 2016/7/20.
 */
public class LoveActivity extends BaseActivity{
    LinearLayout topbar;
    SwipeMenuListView lv;
    LoveAdapter la;
    RelativeLayout rl_left;
    TextView tv;
    LoveDB lb;
    List<HotInfo> list;
    @Override
    public void setLaout() {
        setContentView(R.layout.love_activity);
    }

    @Override
    public void getData() {
        lb=new LoveDB(this);
        list=new ArrayList<HotInfo>();
        list=lb.findAll();
        la=new LoveAdapter(this,list);
    }

    @Override
    public void getView() {
        lv= (SwipeMenuListView) findViewById(R.id.love_listview);
        rl_left= (RelativeLayout) findViewById(R.id.topbar_rl_left);
        tv= (TextView) findViewById(R.id.topbar_tv);
        tv.setText("我的收藏");
        topbar= (LinearLayout) findViewById(R.id.topbar_ll);
        topbar.setBackgroundResource(Tools.color);
    }

    @Override
    public void setView() {
        lv.setAdapter(la);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        lv.setMenuCreator(creator);

        // step 2. listener item click event
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                String url = list.get(position).getUrl();
                switch (index) {
                    case 0:
                        // open
                        Intent i = new Intent(LoveActivity.this, WebActivity.class);
                        i.putExtra("url", url);
                        startActivity(i);
                        break;
                    case 1:
                        // delete
                        lb.deleteOne(url);
                        list.remove(position);
                        la.setList(list);
                        la.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String url = list.get(position).getUrl();
//                Intent i = new Intent(LoveActivity.this, WebActivity.class);
//                i.putExtra("url", url);
//                startActivity(i);
//            }
//        });
//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                //删除收藏
//                String url=list.get(position).getUrl();
//                lb.deleteOne(url);
//                list.remove(position);
//                la.setList(list);
//                la.notifyDataSetChanged();
//                return true;
//            }
//        });
        rl_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}
