package com.example.xulf.shoponline.bean;

/**
 * Created by XuLF on 2016/11/17.
 * 购物车
 */
public class Cart extends  Product {
    private int buyNumber;//数量
    private boolean isChecked=true;//是否选中


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public int getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(int buyNumber) {
        this.buyNumber = buyNumber;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "buyNumber=" + buyNumber +
                ", isChecked=" + isChecked +
                '}';
    }
}
