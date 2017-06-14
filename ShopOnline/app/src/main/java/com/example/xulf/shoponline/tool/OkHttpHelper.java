package com.example.xulf.shoponline.tool;



import android.os.Handler;
import android.os.Looper;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;



/**
 * Created by XuLF on 2016/11/10.
 */
public class OkHttpHelper {
    //枚举，Http方法类型
    enum SubmitType{
        GET,
        POST
    }

    private  static  OkHttpHelper okHttpHelper;
    private OkHttpClient okHttpClient;
    private Gson gson;
    private Handler handler;//用hangdler来控制uI


    //静态块
    static {
        okHttpHelper= new OkHttpHelper();
    }
    //单利模式
    private OkHttpHelper(){
        //实例化对象
        okHttpClient=new OkHttpClient();
        //初始化
        okHttpClient.setConnectTimeout(20, TimeUnit.SECONDS);//20秒的连接超时限制
        okHttpClient.setWriteTimeout(20, TimeUnit.SECONDS);//20秒的写取时间
        okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);//20秒的读取时间限制超时;
        gson=new Gson();//gson初始化对戏那个
        handler=new Handler(Looper.getMainLooper());
    }

    //实例化对象获取
    public static  OkHttpHelper getInstance(){
        return  new OkHttpHelper();
    }

    //封装request方法,进行网络请求
    public void doRequest(Request request, final MyCallback myCallback){

        myCallback.doRequestBefore(request);

        //异步加载
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //网络请求失败
                Log.i("HttpError","网络请求失败！"+e.getMessage()+"request:"+request.toString());

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i("HttpError","网络请求成功！");
                //网络请求成功
                if(response.isSuccessful()){
                    Log.i("HttpError","网络请求成功247！");
                    String resultJson=response.body().string();//string()将流转换成字符串
                    try {
                        Log.i("resultJson11","resultJson"+resultJson);
                    }catch (Exception e){
                        Log.i("resultJson22","resultJson"+resultJson);
                        Log.i("resultJson33","resultJson"+e.getMessage());
                    }
                    Log.i("resultJson","resultJson"+resultJson);
                    //解析字符串如果是字符串就无需再解析，否则将其解析成想要的类型
                    if(myCallback.type==String.class){
                        Log.i("HttpError","网络请求成功24！"+response.toString());
                        Log.i("HttpError","网络请求成功245！");
                        IssuccessfulTrue(myCallback,response,myCallback.type);
                    }else {
                        Log.i("HttpError","网络请求成功23！"+response.toString());
                        Log.i("HttpError","网络请求成功246！"+myCallback.type);
                        //解析成想要的类型，如Users,Banner等javabean类型(
                        Object object=gson.fromJson(resultJson,myCallback.type);
                      //  myCallback.onSuccess(response,object);
                        IssuccessfulTrue(myCallback,response,object);
                    }

                }else{
                    Log.i("HttpError","网络请求成功25！"+response.toString());
                    IssuccessfulFalse(myCallback,response);
                    Log.i("HttpError","网络请求成功25！"+response.toString());
                }

            }
        });
    }

    //网络请求成功时isSuccessful为true时
    private  void IssuccessfulTrue(final MyCallback myCallback, final Response response, final Object o){
        handler.post(new Runnable() {
            @Override
            public void run() {
                myCallback.onSuccess(response,o);
            }
        });
    }
  //网络请求成功时isSuccessful为false时
  private  void IssuccessfulFalse(final MyCallback myCallback, final Response response){
     handler.post(new Runnable() {
         @Override
         public void run() {
            myCallback.onError(response);
         }
     });
 }

    /*构建request对象
    * 用builder创建Request对象；Request request = new Request.Builder().url(url).post(body).build();
    * 路径、参数、访问参数
    * */
    private Request buildRequest(String url, Map<String,Object> params,SubmitType type){
        Request.Builder builder=new Request.Builder();
        builder.url(url);
        //判断http数据提交类型的使用
        if (type==SubmitType.GET){
            builder.get();//使用get方法
        }else if(type==SubmitType.POST){
            //post需要一个RequestBody对象存放参数 由 RequestBody formBody = new FormEncodingBuilder().add("platform", "android").add("platform", "android").builder()获取
            RequestBody formBody=buildRequestBody(params);//创建requestbody对象
            Log.i("formBody",formBody.toString());
            builder.post(formBody);
        }
        return  builder.build();
    }


    //======================RequestBody的创建==================================================
    public RequestBody buildRequestBody(Map<String,Object> params) {
        RequestBody requestBody=null;
        try {
            /*将字符串转为字节*/
            requestBody=  RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),
                    getRequestData(params).toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return requestBody;
    }

   //============================将参数通过键值对取出来整合到url中===========================
    private static StringBuffer getRequestData(Map<String, Object> params) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                stringBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }


    /*封装get方法
      OkHttpClient mOkHttpClient = new OkHttpClient();
      //创建一个Request
      final Request request = new Request.Builder()
                .url("https://github.com/hongyangAndroid")
                .build();
     Call call = mOkHttpClient.newCall(request);
     //请求加入调度
      call.enqueue(new Callback()
    * */
    public void get(String url,MyCallback myCallback){
        Request request=buildRequest(url,null,SubmitType.GET);//创建request对象
        doRequest(request,myCallback);
    }

    /*封装post方法
    *     Request request = buildMultipartFormRequest(
          url, new File[]{file}, new String[]{fileKey}, null);
          FormEncodingBuilder builder = new FormEncodingBuilder();
          builder.add("username","张鸿洋");
          Request request = new Request.Builder() .url(url)
                .post(builder.build())
                .build();
          mOkHttpClient.newCall(request).enqueue(new Callback(){});
    * */
    public void post(String url,Map<String,Object> params,MyCallback myCallback){
        Request request=buildRequest(url,params,SubmitType.POST);
        doRequest(request,myCallback);
    }

    }
