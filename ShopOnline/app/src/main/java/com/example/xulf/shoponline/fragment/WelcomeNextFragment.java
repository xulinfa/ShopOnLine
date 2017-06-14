package com.example.xulf.shoponline.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.xulf.shoponline.R;

/**
 * Created by XuLF on 2017/1/19.
 */
public class WelcomeNextFragment extends WelcomeBaseFragment {

    private ImageView ivFunctionFirst,ivFunctionSecond,ivFunctionThird,ivFunctionFour;//动画图片，，1，2，3，4

    private Animation animation1,animation2,animation3,animation4;//对应4种动画

    private boolean started;//是否开启动画

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_welcome_next,null);
        //对象获取
        ivFunctionFirst=(ImageView)view.findViewById(R.id.iv_function_first);//浏览商品文字图片
        ivFunctionSecond=(ImageView)view.findViewById(R.id.iv_function_second);//进入订单文字图片
        ivFunctionThird=(ImageView)view.findViewById(R.id.iv_function_third);//注册文字图片
        ivFunctionFour=(ImageView)view.findViewById(R.id.iv_function_four);//登陆加入购物车文字图片
        return  view;
    }

    @Override
    public void startAnimation() {
        /*开始动画标志*/
        started=true;
        //起初全部为隐藏状态
        ivFunctionFirst.setVisibility(View.GONE);
        ivFunctionSecond.setVisibility(View.GONE);
        ivFunctionThird.setVisibility(View.GONE);
        ivFunctionFour.setVisibility(View.GONE);
        //延迟5秒开启动画线程
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(started){
                    doFunctionFirst();//动画1
                }

            }
        },500);

    }

    @Override
    public void stopAnimation() {
        started=false;
        //清空动画
        ivFunctionFirst.clearAnimation();
        ivFunctionSecond.clearAnimation();;
        ivFunctionThird.clearAnimation();
        ivFunctionFour.clearAnimation();
    }

    /*动画4*/
    private void doFunctionFour(){
        ivFunctionFour.setVisibility(View.VISIBLE);//设置可见
        animation4= AnimationUtils.loadAnimation(getActivity(),R.anim.welcome_next);
        ivFunctionFour.startAnimation(animation4);//开启动画
    }

    /*动画3*/
    private void doFunctionThird(){
        ivFunctionThird.setVisibility(View.VISIBLE);
        animation3=AnimationUtils.loadAnimation(getActivity(),R.anim.welcome_next);
        ivFunctionThird.startAnimation(animation3);
        animation3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(started){
                    doFunctionFour();//开启动画4
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /*动画2*/
    private  void doFunctionSecond(){
        ivFunctionSecond.setVisibility(View.VISIBLE);
        animation2=AnimationUtils.loadAnimation(getActivity(),R.anim.welcome_next);
        ivFunctionSecond.setAnimation(animation2);
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(started){
                    doFunctionThird();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    /*动画1*/
    public  void doFunctionFirst(){
        //图片可见
        ivFunctionFirst.setVisibility(View.VISIBLE);
        //动画效果
        animation1=AnimationUtils.loadAnimation(getActivity(),R.anim.welcome_next);
        //设置动画
        ivFunctionFirst.setAnimation(animation1);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (started){
                    doFunctionSecond();//动画1停止开始动画2
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }




}