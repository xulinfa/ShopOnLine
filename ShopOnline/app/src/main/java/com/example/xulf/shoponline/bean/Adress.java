package com.example.xulf.shoponline.bean;

import java.io.Serializable;

/**
 * Created by XuLF on 2017/3/9.
 * 地址bean
 */
public class Adress implements Serializable{
    private String addressID;

    private String userName;

    private String address;

    private String recipients;

    private String postcode;

    public String getUserName() {
        return userName;
    }

    public String getAddressID() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID = addressID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Adress{" +
                "addressID='" + addressID + '\'' +
                ", userName='" + userName + '\'' +
                ", address='" + address + '\'' +
                ", recipients='" + recipients + '\'' +
                ", postcode='" + postcode + '\'' +
                '}';
    }
}
