package com.bh.wechat.request;

import java.io.Serializable;

import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.Parameter;
import com.bh.wechat.gateway.URI;

@URI(uri = ApiUri.DIRECTLY_CREATE_ORDER)
public class OrderCreateDirectlyRequest implements Serializable {

    private static final long serialVersionUID = -214382340871061255L;

    @Parameter
    private String token;

    @Parameter
    private int productId;

    @Parameter
    private int qty;

    @Parameter(required = false)
    private String guige;

    @Parameter
    private int addressId;

    @Parameter
    private int paymentMethod;

    @Parameter(required = false)
    private int baodou;

    @Parameter(required = false)
    private String payPassword;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getGuige() {
        return guige;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getBaodou() {
        return baodou;
    }

    public void setBaodou(int baodou) {
        this.baodou = baodou;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

}
