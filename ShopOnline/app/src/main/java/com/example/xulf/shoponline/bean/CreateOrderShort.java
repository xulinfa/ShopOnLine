package com.example.xulf.shoponline.bean;

import java.util.List;

/**
 * Created by XuLF on 2017/3/10.
 * 下订单时json传送给后台的对象
 */
public class CreateOrderShort {

    private String userName;

    private int addressID;

    private List<CreateOrderShortNum> data;



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public List<CreateOrderShortNum> getData() {
        return data;
    }

    public void setData(List<CreateOrderShortNum> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CreateOrderShort{" +
                "userName='" + userName + '\'' +
                ", addressID=" + addressID +
                ", data=" + data +
                '}';
    }
}
