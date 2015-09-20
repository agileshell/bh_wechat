package com.bh.wechat.request;

import java.io.Serializable;

import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.Parameter;
import com.bh.wechat.gateway.URI;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年9月11日 下午9:27:23
 */
@URI(uri = ApiUri.REFRESH_TOKEN)
public class RefreshTokenRequest implements Serializable {

    private static final long serialVersionUID = 6548551213991886630L;

    @Parameter
    private String refreshToken;

    @Parameter
    private String openid;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}