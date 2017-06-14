package com.example.xulf.shoponline;

import android.app.Application;

import com.example.xulf.shoponline.bean.User;
import com.example.xulf.shoponline.tool.MyActivityManager;
import com.example.xulf.shoponline.tool.UserLocalData;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by XuLF on 2017/1/5.
 */
public class App extends Application {

    private User user;//用户

    private static  App mInstance;//实例对象

    public MyActivityManager myActivityManager;//activity管理对象


    public static  App getInstance(){
        return  mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;//实例化对象
        initUser();//初始化操作者
        Fresco.initialize(this);
        myActivityManager=MyActivityManager.getInstance();//activity管理工具

    }


    /*初始化用户*/
    private void initUser(){
        this.user= UserLocalData.getUser(this);
    }

    /*获取用户*/
    public User getUser(){
        return  user;
    }

    /*存储用户*/
    public void putUser(User user){
        this.user=user;
        UserLocalData.putUser(this,user);
    }

    /*清除用户*/
    public void clearUser(){
        this.user=null;
        UserLocalData.clearUser(this);//本地清除
    }

}
