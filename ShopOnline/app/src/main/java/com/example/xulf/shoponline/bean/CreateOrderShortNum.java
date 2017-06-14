package com.example.xulf.shoponline.bean;

/**
 * Created by XuLF on 2017/3/10.
 * 下订单时json传送给后台的对象中商品相关的商品数量,id
 */
public class CreateOrderShortNum {
    private int buyNumber;
    private String productID;

    public int getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(int buyNumber) {
        this.buyNumber = buyNumber;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    @Override
    public String toString() {
        return "CreateOrderShortNum{" +
                "buyNumber='" + buyNumber + '\'' +
                ", productID='" + productID + '\'' +
                '}';
    }
}
