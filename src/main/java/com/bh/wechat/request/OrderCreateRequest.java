package com.bh.wechat.request;

import java.io.Serializable;

import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.Parameter;
import com.bh.wechat.gateway.URI;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月5日 下午1:57:14
 */
@URI(uri = ApiUri.CREATE_ORDER)
public class OrderCreateRequest implements Serializable {

    private static final long serialVersionUID = 3642837042489068657L;

    @Parameter
    private String token;

    @Parameter
    private String cartProductIds;

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

    public String getCartProductIds() {
        return cartProductIds;
    }

    public void setCartProductIds(String cartProductIds) {
        this.cartProductIds = cartProductIds;
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
