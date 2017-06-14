package com.example.xulf.shoponline.bean;

import java.util.List;

/**
 * Created by XuLF on 2017/1/3.
 * 注册返回码
 */
public class TextJson{
   private String  code;
    private String msg;
    private RegisterReturn data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public RegisterReturn getData() {
        return data;
    }

    public void setData(RegisterReturn data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TextJson{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
