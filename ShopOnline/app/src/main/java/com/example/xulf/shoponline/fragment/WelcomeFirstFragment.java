package com.example.xulf.shoponline.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.xulf.shoponline.R;

/**
 * 引导页的第一个页面
 * Created by XuLF on 2017/1/19.
 */
public class WelcomeFirstFragment extends WelcomeBaseFragment {

    private ImageView ivGold;//掉落的金币

    private ImageView ivLast;//最后填充的图片

    private Bitmap goldBitmap;//动画金币

    private boolean started;//是否开始动画


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_welcome_first,null);
        //对象获取
        ivGold=(ImageView)view.findViewById(R.id.iv_gold);//掉落的金币图片
        ivLast=(ImageView)view.findViewById(R.id.iv_reward);//动画最后填充的图pain

        //画初始位置的金币  小金币
        goldBitmap= BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.welcome_icon_gold);
        //开始动画
        startAnimation();

        return view;
    }

    @Override
    public void startAnimation() {
        //开始动画标志为true，开始
        started=true;
        //向下移动动画 硬币的高度*2+80   位移动画效果-》TranslateAnimation
        TranslateAnimation translateAnimation=new TranslateAnimation(0,0,0,goldBitmap.getHeight()*2+90);
        translateAnimation.setDuration(500);
        //设为true之后，界面会停留在动画播放完时的界面，否则会跳回初始位置
        translateAnimation.setFillAfter(true);

        //小金币开始下落动画
        ivGold.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            /*下落结束开始缩放*/
            @Override
            public void onAnimationEnd(Animation animation) {
                if(started){
                    //把填充的大买字显示权限开启
                    ivLast.setVisibility(View.VISIBLE);
                    //金币下落结束后开始缩放动画
                    Animation animation1= AnimationUtils.loadAnimation(getContext(),R.anim.welcome_first);
                    //填充图开始缩放动画
                    ivLast.setAnimation(animation1);
                    animation1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }
                        /*缩放动画结束 开启改变透明度动画 1，0不透明到全透明*/
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            //缩放动画结束 开启改变透明度动画 1，0不透明到全透明
                            AlphaAnimation alphaAnimation=new AlphaAnimation(1,0);
                            alphaAnimation.setDuration(1000);
                            //开始动画
                            ivLast.startAnimation(alphaAnimation);
                            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    //透明度动画结束隐藏图片
                                    ivLast.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });


                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    @Override
    public void stopAnimation() {
        started=false;//结束动画时标示符设置为false
        ivGold.clearAnimation();//清空view上的动画

    }

}
