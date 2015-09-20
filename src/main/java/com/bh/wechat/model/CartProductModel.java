package com.bh.wechat.model;

public class CartProductModel {

    private int cartProductId;

    private int productId;

    private String name;

    private String coverImg;

    private float price;

    private String guige;

    private int qty;

    private float baodouPercent;

    public int getCartProductId() {
        return cartProductId;
    }

    public void setCartProductId(int cartProductId) {
        this.cartProductId = cartProductId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getGuige() {
        return guige;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getBaodouPercent() {
        return baodouPercent;
    }

    public void setBaodouPercent(float baodouPercent) {
        this.baodouPercent = baodouPercent;
    }

}
