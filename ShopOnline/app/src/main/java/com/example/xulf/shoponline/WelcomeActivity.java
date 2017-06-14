package com.example.xulf.shoponline;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.xulf.shoponline.adapter.WelcomeAdapter;
import com.example.xulf.shoponline.fragment.WelcomeBaseFragment;
import com.example.xulf.shoponline.fragment.WelcomeFirstFragment;
import com.example.xulf.shoponline.fragment.WelcomeLastFragment;
import com.example.xulf.shoponline.fragment.WelcomeNextFragment;
import com.example.xulf.shoponline.tool.MyViewpager;
import com.example.xulf.shoponline.tool.SharedPreferencesUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends FragmentActivity {


    private MyViewpager guideViewPager;//背景切换图容器（ViewPager）

   // private ViewPager guideViewPager;
    private List<WelcomeBaseFragment> list=new ArrayList<WelcomeBaseFragment>();//Fragment

    private WelcomeAdapter welcomeAdapter;//fragmentlist适配器

    private ImageView[] tips;//小点点

    private int currentSelected;//当前选择的Fragment

    private ViewGroup viewGroup;//点控件容器
    private Gson gson=new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //对象获取
        viewGroup=(ViewGroup)findViewById(R.id.viewGroup);//点点点容器，linerLayout
        guideViewPager=(MyViewpager) findViewById(R.id.viewpager_welcome_launcher);//容器背景装载

        tips=new ImageView[3];//背景图
        for (int i=0;i<tips.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10,10));
            if(i==0){
                //小圆点选中
                imageView.setBackgroundResource(R.drawable.welcome_page_indicator_focused);//选中
            }else{
                //小原点未选中
                imageView.setBackgroundResource(R.drawable.welcome_page_indicator_unfocused);//未选中
            }
            tips[i]=imageView;
            //布局参数
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin=20;//左边view的左边距
            layoutParams.rightMargin=20;//右边view的由边距
            viewGroup.addView(imageView,layoutParams);//添加进去
        }
          guideViewPager.setBackGroud(BitmapFactory.decodeResource(getResources(),R.mipmap.welcom_bg));//设置背景
        //guideViewPager.setBackgroundResource(R.mipmap.head);

        //初始化三个Fragment并添加到list中去
        WelcomeFirstFragment welcomeFirstFragment=new WelcomeFirstFragment();//第一个欢迎页面
        WelcomeNextFragment welcomeNextFragment=new WelcomeNextFragment();//第二个页面
        WelcomeLastFragment welcomeLastFragment=new WelcomeLastFragment();//第三个页面
        /*添加到list里*/
        list.add(welcomeFirstFragment);
        list.add(welcomeNextFragment);
        list.add(welcomeLastFragment);

        //适配器
        welcomeAdapter =new WelcomeAdapter(getSupportFragmentManager(),list);
        guideViewPager.setAdapter(welcomeAdapter);//设置适配器
        guideViewPager.setOffscreenPageLimit(2);//预先加载，更加流畅
        guideViewPager.setCurrentItem(0);//当前项为第一个Fragment
        guideViewPager.setOnPageChangeListener(onPageChangeListener);
    }

    /*监听viewpage的移动*/
    ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setImageBackground(position);//设置小圆点是否为选中状态
            WelcomeBaseFragment fragment=list.get(position);//获取当前的Fragment
            list.get(currentSelected).stopAnimation();//停止上一个的动画
            fragment.startAnimation();//开启当前的动画
            currentSelected=position;


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /*改变背景*/
    private void setImageBackground(int itemIndex){
        for(int i=0;i<tips.length;i++){
            if(i==itemIndex){
                tips[i].setBackgroundResource(R.drawable.welcome_page_indicator_focused);
            }else{
                tips[i].setBackgroundResource(R.drawable.welcome_page_indicator_unfocused);//未选中状态
            }
        }

    }

/*    @Override
    protected void onStop() {
        super.onStop();
        Drawable d = guideViewPager.getBackground();
        if (d != null) d.setCallback(null);
        guideViewPager.setBackGroud(null);
       // guideViewPager.setBackgroundDrawable(null);
    }*/
}
