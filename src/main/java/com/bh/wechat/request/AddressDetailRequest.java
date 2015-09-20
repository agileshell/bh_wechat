package com.bh.wechat.request;

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
@URI(uri = ApiUri.GET_ADDRESS_DETAIL)
public class AddressDetailRequest {

    @PathVariable
    private int addressId;

    @Parameter
    private String token;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
