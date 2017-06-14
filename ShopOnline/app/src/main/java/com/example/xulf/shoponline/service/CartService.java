package com.example.xulf.shoponline.service;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.example.xulf.shoponline.App;
import com.example.xulf.shoponline.bean.Cart;
import com.example.xulf.shoponline.bean.Product;
import com.example.xulf.shoponline.bean.ReturnJson;
import com.example.xulf.shoponline.tool.Contants;
import com.example.xulf.shoponline.tool.MyCallback;
import com.example.xulf.shoponline.tool.OkHttpHelper;
import com.example.xulf.shoponline.tool.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XuLF on 2016/11/17.
 * 购物车实现
 */
public class CartService {
    //稀疏数组记录购物车
    private SparseArray<Cart> listCart;
    //当前上下文
    private Context context;
    //Gson解析json
    private Gson gson;

    /*网络请求*/
    private OkHttpHelper okHttpHelper=OkHttpHelper.getInstance();


    //带参数的构造函数
    public CartService(Context context) {
        this.context = context;
        listCart=new SparseArray<>(10);
        gson=new Gson();//实例化GSon
        listToSparse();
    }


 /*   //带参数的构造函数
    public CartService(SparseArray<Cart> listCart, Context context) {
        this.listCart = listCart;
        this.context = context;
        gson=new Gson();//实例化GSon
    }*/

    //从sp中获取购物车内容的json数据并且转换为相应的数据类型
    public List<Cart> getCartFromSp(){
        String cartJson= SharedPreferencesUtils.getString(context, Contants.CART_JSON);//当前上下文，key
        List<Cart> carts=null;
        if(cartJson!=null){
            //SP中存有购物车内容则转换为相应的类型
            carts= (List<Cart>) gson.fromJson(cartJson,new TypeToken<List<Cart>>(){}.getType());

        }
        return  carts;
    }



    //将购物车list集合的方式转换为系数数组的方式存储
    private void listToSparse(){
        //先从sp中取出购物车
        List<Cart> cartList=getCartFromSp();
        if(cartList!=null&&cartList.size()>0){
            //如果购物车中有东西则遍历存储到稀疏数组中去
            for (Cart item:cartList) {
                //用商品Id作为key值
                listCart.put(item.getProductID(),item);
            }
        }
    }

    //将稀疏数组的东西转换为list，方便存入sp
    private List<Cart> sparseToList(){
        List<Cart> list=new ArrayList<>(listCart.size());
        for (int i=0;i<listCart.size();i++){
            list.add(listCart.valueAt(i));//将栈中的数据按照从栈顶到栈底的顺序添加到List中
        }
        return  list;
    }

    //将产品传入购物车，变为购物车的一项
    public void putIntoCart(Product product){
        Cart cart=new Cart();
        cart.setProductID(product.getProductID());
        cart.setProductName(product.getProductName());
        cart.setCategorys(product.getCategorys());
        cart.setPrice(product.getPrice());
        cart.setProductImg(product.getProductImg());
        cart.setProductDetail(product.getProductDetail());
       // cart.setProduct_number(product.getProduct_number());
        addCart(cart);
    }

 //添加进入购物车
    public void addCart(Cart cart){
        Cart item=listCart.get(cart.getProductID());//查询购物车里是否已经存在这件商品
        if(item!=null){
            //购物车里已经存在
            item.setBuyNumber(item.getBuyNumber()+1);//将其数量加1
        }else{
            item=cart;
            item.setBuyNumber(1);
        }
        listCart.put(cart.getProductID(),item);
        List<Cart> list=sparseToList();
        SharedPreferencesUtils.putString(context,Contants.CART_JSON,gson.toJson(list));//存入sp
    }

    //删除
    public void delete(Cart cart){
        listCart.delete(cart.getProductID());
        try{
            App app=App.getInstance();
            Map<String,Object> params = new HashMap<>(2);
            params.put("userName",app.getUser().getUserName());
            params.put("productID",cart.getProductID());
            okHttpHelper.post(Contants.DELETECART, params, new MyCallback<ReturnJson<String>>(context) {
                @Override
                public void doRequestBefore(Request request) {

                }

                @Override
                public void onError(Response response) {

                }

                @Override
                public void onSuccess(Response response, ReturnJson<String> stringReturnJson) {
                    String code=stringReturnJson.getCode();
                    String msg=stringReturnJson.getMsg();
                    if (code.equals("200")){
                        Log.i("deletecart","success");
                    }else{
                        Log.i("deletecarterror","code:"+code+",msg:"+msg);
                    }
                }


            });
        }catch (Exception e){
            Log.i("deleteException",e.getMessage());
        }
        List<Cart> list=sparseToList();
        SharedPreferencesUtils.putString(context,Contants.CART_JSON,gson.toJson(list));//存入sp
    }
    //更新
    public void update(Cart cart){
        listCart.put(cart.getProductID(),cart);
        List<Cart> list=sparseToList();
        SharedPreferencesUtils.putString(context,Contants.CART_JSON,gson.toJson(list));//存入sp
    }
    //获取全部
    public List<Cart> getAll(){
        return getCartFromSp();
    }

    /*清楚购物车*/

    public  void clearAll(){
        SharedPreferencesUtils.putString(context,Contants.CART_JSON,"");
    }



}
