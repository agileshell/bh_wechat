package com.bh.wechat.request;

import java.io.Serializable;

import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.Parameter;
import com.bh.wechat.gateway.URI;

@URI(uri = ApiUri.REGISTRY)
public class RegistryRequest implements Serializable {

    private static final long serialVersionUID = 7652965193846147419L;

    @Parameter
    private String userName;

    @Parameter
    private String mobile;

    @Parameter
    private String password;

    @Parameter
    private String openid;

    @Parameter
    private int locationId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

}
