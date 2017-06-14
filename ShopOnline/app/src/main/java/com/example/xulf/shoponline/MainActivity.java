package com.example.xulf.shoponline;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.xulf.shoponline.bean.Tab;
import com.example.xulf.shoponline.fragment.CartFragment;
import com.example.xulf.shoponline.fragment.CategoryFragment;
import com.example.xulf.shoponline.fragment.HomeFragment;
import com.example.xulf.shoponline.fragment.HotFragment;
import com.example.xulf.shoponline.fragment.MineFragment;
import com.example.xulf.shoponline.service.CartService;
import com.example.xulf.shoponline.widget.FragmentTabHost;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static MainActivity in;

    //FragmentTabHost
    private FragmentTabHost mFragmentTabHost;
    //Tab集合  5个
    private List<Tab> mTabs=new ArrayList<Tab>(5);

    private CartFragment cartFragment;

    private App app=App.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app.myActivityManager.addActivity(this);//添加Activity
        in=this;
        getSupportActionBar().hide();


        initTab();

    }
    //初始化底部选项卡
    private void  initTab(){
        Tab tabHome=new Tab(R.string.homeString,R.drawable.selector_icon_home, HomeFragment.class);
        Tab tabHot=new Tab(R.string.productStringtab,R.drawable.selector_icon_hot, HotFragment.class);
        Tab tabCategory=new Tab(R.string.categoryString,R.drawable.selector_icon_category, CategoryFragment.class);
        Tab tabCart=new Tab(R.string.carString,R.drawable.selector_icon_cart, CartFragment.class);
        Tab tabMine=new Tab(R.string.mineString,R.drawable.selector_icon_mine, MineFragment.class);
        //加入数组集合
        mTabs.add(tabHome);
        mTabs.add(tabHot);
        mTabs.add(tabCategory);
        mTabs.add(tabCart);
        mTabs.add(tabMine);

        //获取对象
        mFragmentTabHost=(FragmentTabHost) findViewById(android.R.id.tabhost);
        //设置Fragment 和 content
        mFragmentTabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(getString(tab.getTitle()));//设置标签
            tabSpec.setIndicator(buildIndicator(tab));//设置View
            mFragmentTabHost.addTab(tabSpec, tab.getFragment(), null);
        }
        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if(s==getString(R.string.carString)){
                refData();
                }
            }
        });

        mFragmentTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);//去除两个Tab之间的分分割线
        mFragmentTabHost.setCurrentTab(0);//默认为第一个

    }

public void hh(int h){
    mFragmentTabHost.setCurrentTab(h);//默认为第一个

}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            //    设置Title的图标
            builder.setIcon(R.mipmap.icon_outlogin);
            //    设置Title的内容
            builder.setTitle("退出便利店");
            //    设置Content来显示一个信息
            builder.setMessage("确定退出吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   // System.exit(0);
                    app.myActivityManager.AppExit(getApplicationContext());

                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            //    显示出该对话框
            builder.show();
        }
        return false;
    }

    //设置tab下的图标和标签
    private View buildIndicator(Tab tab) {
        View view = View.inflate(this, R.layout.tab_indicator, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.icon_tab);
        TextView textView = (TextView) view.findViewById(R.id.txt_indicator);
        textView.setText(tab.getTitle());
        imageView.setBackgroundResource(tab.getIcon());
        return view;
    }

    //购物车Tab时进行数据刷新
    private void refData(){

        if(cartFragment == null){
            Fragment fragment =  getSupportFragmentManager().findFragmentByTag(getString(R.string.carString));
            if(fragment !=null){
                cartFragment= (CartFragment) fragment;
                cartFragment.refData();
            }
        }
        else{
            cartFragment.refData();

        }
    }

    @Override
    protected void onResume() {
      /*  int id=getIntent().getIntExtra("loginsucee",0);
        if(id==1){
            mFragmentTabHost.setCurrentTab(4);
        }*/
        super.onResume();
    }
}
