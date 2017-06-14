package com.example.xulf.shoponline.bean;

import java.io.Serializable;

/**
 * Created by XuLF on 2016/11/13.
 * 商品对象
 */
public class Product implements Serializable {
    /** ID */
    private int productID;

    /*产品分类*/
    private String categorys;

    /*产品名称*/
    private String productName;

    /*产品价格*/
    private double price;

    /*产品描述*/
    private String productDetail;

    /*产品图片*/
    private String productImg;

/*产品库存*//*

    private int product_number;
*/

    private int productNumber;

    private String productState;

    /*类别图片*/
    private String categorysImg;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getCategorys() {
        return categorys;
    }

    public void setCategorys(String categorys) {
        this.categorys = categorys;
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

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState;
    }

    public String getCategorysImg() {
        return categorysImg;
    }

    public void setCategorysImg(String categorysImg) {
        this.categorysImg = categorysImg;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", categorys='" + categorys + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", productDetail='" + productDetail + '\'' +
                ", productImg='" + productImg + '\'' +
                ", productNumber=" + productNumber +
                ", productState='" + productState + '\'' +
                ", categorysImg='" + categorysImg + '\'' +
                '}';
    }
}
