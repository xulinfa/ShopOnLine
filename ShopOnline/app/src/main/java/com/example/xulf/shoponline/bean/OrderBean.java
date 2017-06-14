package com.example.xulf.shoponline.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XuLF on 2017/3/10.
 * 获取个人订单时的订单数据
 */
public class OrderBean implements Serializable{

     private String orderID;            //订单id
     private  String   orderTime;        //下单时间
     private String recipients;//收货人
     private  String orderState;    //订单状态（未发货
     private  String  address;           //地址
     private List<GetOrderProduct> orderProductList;//

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<GetOrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<GetOrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "orderID='" + orderID + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", recipients='" + recipients + '\'' +
                ", orderState='" + orderState + '\'' +
                ", address='" + address + '\'' +
                ", orderProductList=" + orderProductList +
                '}';
    }
}
