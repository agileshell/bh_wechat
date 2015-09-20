package com.bh.wechat.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ProductDetailResponse extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -8454549630425665549L;

    private int productId;

    private String number;

    private String name;

    private String coverImg;

    private float price;

    private float discountPrice;

    private int point;

    private int inventory;

    private List<String> images;

    private String description;

    private Map<String, List<String>> guige;

    private float baodouPercent;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, List<String>> getGuige() {
        return guige;
    }

    public void setGuige(Map<String, List<String>> guige) {
        this.guige = guige;
    }

    public float getBaodouPercent() {
        return baodouPercent;
    }

    public void setBaodouPercent(float baodouPercent) {
        this.baodouPercent = baodouPercent;
    }

}
