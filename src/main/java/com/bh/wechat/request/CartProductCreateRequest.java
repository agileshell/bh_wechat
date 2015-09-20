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
@URI(uri = ApiUri.ADD_CART_PRODUCT)
public class CartProductCreateRequest implements Serializable {

    private static final long serialVersionUID = -231555191242314620L;

    @PathVariable
    private int productId;

    @Parameter
    private String token;

    @Parameter
    private int qty;

    @Parameter(required = false)
    private String guige;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public String getGuige() {
        return guige;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }

}
