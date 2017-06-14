package com.example.xulf.shoponline.tool;

/**
 * Created by XuLF on 2016/11/12.
 * 常量
 */
public class Contants {
    //将数据放入SharedPreferece中去的文件名
    public static String SHAREDPREFERENCE_NAME="shop_sharepreference";
    //从本地获取购物车
    public static final String CART_JSON="cart_json";

    //本地的用户存储，用于查看用户是否进行过登陆
    public  static  final  String  USER_JSON="user_json";

    //轮播
   public static final String BANNERURL="http://112.124.22.238:8081/course_api/banner/query?type=1";
  // public static final String BANNERURL="http://192.168.43.60:8080/ECServer_D/home";
    //热搜商品
    public static final String HOTPRODUCT="http://116.62.37.49:8080/bld/api/product?action=productHot";
    //登陆
    public static  final  String LOGIN="http://116.62.37.49:8080/bld/api/users?action=login";
    //注册
    public static  final String REGISTER="http://116.62.37.49:8080/bld/api/users?action=register";
    /*忘记密码*/
    public static  final String FORGETPWD="http://116.62.37.49:8080/bld/api/users?action=retrieve";
    /*所有产品*/
    public static final String PRODUCTLIST="http://116.62.37.49:8080/bld/api/product?action=productList";
    /*获取产品类型*/
    public  static  final  String PRODUCTCATEGORY="http://116.62.37.49:8080/bld/api/product?action=productCategorys";
    /*获取对应产品的类型的产品*/
    public  static  final  String PRODUCTWITHTYPE="http://116.62.37.49:8080/bld/api/product?action=productByCategorys";

    /*获取购物车内容*/
    public  static final String GETCARTLIST="http://116.62.37.49:8080/bld/api/cart?action=cartList";

    /*添加购物车*/
    public static  final String ADDCART="http://116.62.37.49:8080/bld/api/cart?action=addProductToCart";

    /*删除商品*/
    public static final String DELETECART="http://116.62.37.49:8080/bld/api/cart?action=deleteCart";

    /*修改用户信息*/
    public static final String ALERTUSERMSG="http://116.62.37.49:8080/bld/api/users?action=modifyUser";

    /*添加地址*/
    public static final String ADDADRESS="http://116.62.37.49:8080/bld/api/address?action=addAddress";

    /*获取个人地址*/
    public  static final String GETALLADRESS="http://116.62.37.49:8080/bld/api/address?action=getAllAddress";

    /*删除地址*/
    public static  final  String DELADRESS="http://116.62.37.49:8080/bld/api/address?action=deleteAddress";

    /*添加个人订单*/
    public static final String CREATEORDER="http://116.62.37.49:8080/bld/api/orders?action=addOrders";

    /*获取个人订单*/
    public static  final  String GETALLORDER="http://116.62.37.49:8080/bld/api/orders?action=getAllOrders";

    /*根据不同的订单状态查看你订单列表*/
    public static final String GETORDERLISTBYSTATE="http://116.62.37.49:8080/bld/api/orders?action=getAllOrdersByState";

  /*修改订单状态*/
  public static final String ALERTORDERSTATE="http://116.62.37.49:8080/bld/api/orders?action=modifyOrderState";

  /*获取商品评论*/
  public static final  String GETPRODUCTPINGLUN="http://116.62.37.49:8080/bld/api/comment?action=getAllComment";

  /*添加评论*/
    public static final String ADDPINGLUN="http://116.62.37.49:8080/bld/api/comment?action=addComment";

  /*搜索店内商品*/
  public static  final  String SEARCHPRODUCT="http://116.62.37.49:8080/bld/api/product?action=productByProductName";

  /*搜索产品模糊查询*/
  public static final String SEARCHPROMIHU="http://116.62.37.49:8080/bld/api/product?action=productByLikeName";

}
