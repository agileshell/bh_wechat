package com.bh.wechat.request;

import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.Parameter;
import com.bh.wechat.gateway.URI;

@URI(uri = ApiUri.CURRENCY_DEAL_HISTORY)
public class ListCurrencyDealRequest extends PaginationRequest {

    private static final long serialVersionUID = 7928319485184221177L;

    @Parameter
    private String token;

    @Parameter(required = false)
    private String type;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
