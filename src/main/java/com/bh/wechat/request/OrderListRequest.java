package com.bh.wechat.request;

import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.Parameter;
import com.bh.wechat.gateway.URI;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月5日 下午1:57:14
 */
@URI(uri = ApiUri.GET_ORDERS)
public class OrderListRequest extends PaginationRequest {

    private static final long serialVersionUID = 4309132283253567497L;

    @Parameter
    private String token;

    @Parameter
    private String status;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
