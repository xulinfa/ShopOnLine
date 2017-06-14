package com.example.xulf.shoponline.bean;

/**
 * Created by XuLF on 2016/12/28.
 * 商品类型
 */
public class Type {


    /** 类型名称 */
    private String categorys;

    /** 图片 */
    private String getCategorysImg;

    public String getCategorys() {
        return categorys;
    }

    public void setCategorys(String categorys) {
        this.categorys = categorys;
    }

    public String getGetCategorysImg() {
        return getCategorysImg;
    }

    public void setGetCategorysImg(String getCategorysImg) {
        this.getCategorysImg = getCategorysImg;
    }


    @Override
    public String toString() {
        return "Type{" +
                "categorys='" + categorys + '\'' +
                ", getCategorysImg='" + getCategorysImg + '\'' +
                '}';
    }

}
