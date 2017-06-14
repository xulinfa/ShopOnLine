package com.example.xulf.shoponline.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.xulf.shoponline.MainActivity;
import com.example.xulf.shoponline.R;

/**
 * Created by XuLF on 2017/1/20.
 */
public class WelcomeLastFragment extends WelcomeBaseFragment implements View.OnClickListener {

    private ImageView ivImmediateExperience;//立即体验图片

    private static final float ZOOM_MAX = 1.3f;//放大1.3倍
    private static final  float ZOOM_MIN = 1.0f;//缩小

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_welcome_last,null);
        //对象获取
        ivImmediateExperience=(ImageView)view.findViewById(R.id.iv_welcome_immediate_experience);
        ivImmediateExperience.setOnClickListener(this);//点击监听
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case  R.id.iv_welcome_immediate_experience:
                Intent intent=new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            default:
                break;
        }

    }

    @Override
    public void startAnimation() {
        doAnimation();//开始动画
    }

    @Override
    public void stopAnimation() {

    }

    private void doAnimation(){
        //动画放大
        AnimationSet animationSet=new AnimationSet(true);
/*        ScaleAnimation(float fromX, float toX, float fromY, float toY,int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
        参数说明：徐林发  http://blog.csdn.net/xsl1990/article/details/17096501
        float fromX 动画起始时 X坐标上的伸缩尺寸
        float toX 动画结束时 X坐标上的伸缩尺寸
        float fromY 动画起始时Y坐标上的伸缩尺寸
        float toY 动画结束时Y坐标上的伸缩尺寸
        int pivotXType 动画在X轴相对于物件位置类型
        float pivotXValue 动画相对于物件的X坐标的开始位置
        int pivotYType 动画在Y轴相对于物件位置类型
        float pivotYValue 动画相对于物件的Y坐标的开始位置*/
        animationSet.addAnimation(new ScaleAnimation(ZOOM_MIN, ZOOM_MAX, ZOOM_MIN, ZOOM_MAX, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f));
        animationSet.addAnimation(new AlphaAnimation(1.0f,0.8f));//淡入淡出
        animationSet.setDuration(500);
        animationSet.setInterpolator(new AccelerateInterpolator());//在动画开始的地方速率比较慢，再动画结束的地方速率比较快
        animationSet.setFillAfter(true);//动画结束后，再最后时刻顿一帧

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //缩小动画
                AnimationSet animationSet1=new AnimationSet(true);
                animationSet1.addAnimation(new ScaleAnimation(ZOOM_MAX, ZOOM_MIN, ZOOM_MAX,ZOOM_MIN, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f));
                animationSet1.addAnimation(new AlphaAnimation(0.8f, 1.0f));//淡入淡出
                animationSet1.setDuration(600);
                animationSet1.setInterpolator(new DecelerateInterpolator());//再动画开始的地方速率比较慢，然后开始减速
                animationSet1.setFillAfter(false);
                ivImmediateExperience.startAnimation(animationSet1);//启动动画

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivImmediateExperience.startAnimation(animationSet);//启动动画

    }
}
