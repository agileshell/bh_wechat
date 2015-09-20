package com.bh.wechat.request;

import java.io.Serializable;

import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.Parameter;
import com.bh.wechat.gateway.PathVariable;
import com.bh.wechat.gateway.URI;

@URI(uri = ApiUri.UPDATE_CART_PRODUCT)
public class CartProductUpdateRequest implements Serializable {

    private static final long serialVersionUID = -6074203659925839208L;

    @PathVariable
    private int cartProductId;

    @Parameter
    private String token;

    @Parameter
    private int qty;

    public int getCartProductId() {
        return cartProductId;
    }

    public void setCartProductId(int cartProductId) {
        this.cartProductId = cartProductId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

}
