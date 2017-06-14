package com.example.xulf.shoponline.bean;

/**
 * Created by XuLF on 2016/11/10.
 * 首页的TabHost
 */
public class Tab {
    private  int title;
    private  int icon;
    private Class fragment;

    //构造方法
    public Tab(int title, int icon, Class fragment) {
        this.title = title;
        this.icon = icon;
        this.fragment = fragment;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
