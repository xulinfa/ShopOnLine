package com.example.xulf.shoponline.bean;

/**
 * Created by XuLF on 2017/1/22.
 * 注册返回的data内容
 */
public class RegisterReturn{

    private String nickname ;
    private String password;
    private String sex;
    private String userName;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "RegisterReturn{" +
                "nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
