package com.example.xulf.shoponline.bean;

/**
 * Created by XuLF on 2017/2/25.
 * 评论bean
 */
public class Pinglun {
    private String userName;//用户名

    private String commentTime;//评论时间

    private String commentContent;//评论内容

    private int productID;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    @Override
    public String toString() {
        return "Pinglun{" +
                "userName='" + userName + '\'' +
                ", commentTime='" + commentTime + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", productID=" + productID +
                '}';
    }
}
