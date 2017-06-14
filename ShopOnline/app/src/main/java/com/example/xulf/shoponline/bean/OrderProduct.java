package com.example.xulf.shoponline.bean;

import java.io.Serializable;

/**
 * Created by XuLF on 2017/2/23.
 * 创建订单时用的产品信息，点击下单的时候
 */
public class OrderProduct extends Product{
    private int buy_number;//数量

    public int getBuy_number() {
        return buy_number;
    }

    public void setBuy_number(int buy_number) {
        this.buy_number = buy_number;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "buy_number=" + buy_number +
                '}';
    }
}
