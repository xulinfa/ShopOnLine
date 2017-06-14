package com.example.xulf.shoponline.tool;

import android.content.Context;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by XuLF on 2016/11/11.
 */
public abstract class MyCallback<T> {
    //得到泛型T的实际类型String ,User
    public Type type;
    protected Context mContext;

    public MyCallback(Context context) {
        mContext=context;
        type=getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        //获取当前对象的超累
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        //获取超类的泛型实际类型  如：User<T> 得到T的类型
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
       // return null;
    }

//执行请求操作前的操作
    public abstract void doRequestBefore(Request request) ;
//网络请求失败
    public abstract  void onError(Response response);
    //网络请求成功
    public abstract void onSuccess(Response response, T t);


}
