package com.bh.wechat.response;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月5日 下午2:01:41
 */
public class CartProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    private int cartProductId;

    private int productId;

    private String name;

    private String coverImg;

    private Map<String, String> guige;

    private float price;

    private float discountPrice;

    private int inventory;

    private int point;

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

    public Map<String, String> getGuige() {
        return guige;
    }

    public void setGuige(Map<String, String> guige) {
        this.guige = guige;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
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
