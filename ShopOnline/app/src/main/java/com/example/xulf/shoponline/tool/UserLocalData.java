package com.example.xulf.shoponline.tool;

import android.content.Context;

import com.example.xulf.shoponline.bean.User;
import com.google.gson.Gson;

/**
 * Created by XuLF on 2017/2/15.
 * 本地操作用户
 */
public class UserLocalData {
    public static Gson gson=new Gson();
    /*
    * 存储用户*/
    public static void putUser(Context context, User user){
        String user_json=gson.toJson(user);
        SharedPreferencesUtils.putString(context,Contants.USER_JSON,user_json);
    }


    /*得到用户*/
    public  static  User getUser(Context context){
        String user_json=SharedPreferencesUtils.getString(context,Contants.USER_JSON);
        if(user_json!=null&&!user_json.equals("")){
            //转为相应的类型
            return gson.fromJson(user_json,User.class);
        }
        return null;
    }

    /*清除用户*/
    public static  void clearUser(Context context){
        SharedPreferencesUtils.putString(context,Contants.USER_JSON,"");
    }
}
