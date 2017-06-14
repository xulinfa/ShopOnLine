package com.example.xulf.shoponline.bean;

import java.io.Serializable;

/**
 * Created by XuLF on 2017/3/13.
 * 获取个人订单时返回的产品相关数据对象
 */
public class GetOrderProduct implements Serializable{

    private int buyNumber;//数量
    private String productID;//产品的ID
    private String productName;//产品的名称
    private double price;//产品的价格
    private String productDetail;//产品的描述
    private String productImg;//产品的图片

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "GetOrderProduct{" +
                "buyNumber=" + buyNumber +
                ", productID='" + productID + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", productDetail='" + productDetail + '\'' +
                ", productImg='" + productImg + '\'' +
                '}';
    }
}
