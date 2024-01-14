package com.foodorderapps.Models;

public class OrdersModel {
    int img;
    String orderTitle,orderPrice,orderNumber;

    public OrdersModel(int img, String orderTitle, String orderPrice, String orderNumber) {
        this.img = img;
        this.orderTitle = orderTitle;
        this.orderPrice = orderPrice;
        this.orderNumber = orderNumber;
    }

    public OrdersModel() {

    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
