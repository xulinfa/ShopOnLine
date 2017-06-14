package com.example.xulf.shoponline.fragment;

import android.support.v4.app.Fragment;

/**
 * 引导页的基类
 * Created by XuLF on 2017/1/19.
 */
public abstract class WelcomeBaseFragment extends Fragment {
    public abstract void  startAnimation();//开始动画
    public abstract void  stopAnimation();//结束动画
}
