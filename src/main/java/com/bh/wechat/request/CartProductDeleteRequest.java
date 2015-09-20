package com.bh.wechat.request;

import java.io.Serializable;

import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.Parameter;
import com.bh.wechat.gateway.URI;
import com.bh.wechat.gateway.PathVariable;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月5日 下午1:57:14
 */
@URI(uri = ApiUri.REMOVE_CART_PRODUCT)
public class CartProductDeleteRequest implements Serializable {

    private static final long serialVersionUID = -8282418688605312545L;

    @PathVariable
    private int cartProductId;

    @Parameter
    private String token;

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

}
