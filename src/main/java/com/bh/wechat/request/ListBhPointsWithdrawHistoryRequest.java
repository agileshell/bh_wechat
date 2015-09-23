package com.bh.wechat.request;

import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.Parameter;
import com.bh.wechat.gateway.URI;

@URI(uri = ApiUri.BH_POINTS_WITHDRAW_HISTORY)
public class ListBhPointsWithdrawHistoryRequest extends PaginationRequest {

    private static final long serialVersionUID = 5627355249685891952L;

    @Parameter
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
