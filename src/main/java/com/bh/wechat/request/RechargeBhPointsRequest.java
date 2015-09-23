package com.bh.wechat.request;

import java.io.Serializable;

import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.Parameter;
import com.bh.wechat.gateway.URI;

@URI(uri = ApiUri.RECHARGE_BH_POINTS)
public class RechargeBhPointsRequest implements Serializable {

    private static final long serialVersionUID = -2142345375308812297L;

    @Parameter
    private String token;

    @Parameter
    private double amount;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
