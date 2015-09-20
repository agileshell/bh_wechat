package com.bh.wechat.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.bh.wechat.constant.CommonConstant;
import com.bh.wechat.gateway.HttpGatewayTemplate;
import com.bh.wechat.response.AccountResponse;
import com.bh.wechat.util.JacksonUtils;

public abstract class BaseService {

    @Autowired
    @Qualifier("jsonHttpGatewayTemplate")
    protected HttpGatewayTemplate httpGatewayTemplate;

    @Autowired
    @Qualifier("redisTemplate")
    protected StringRedisTemplate redisTemplate;

    private static long TOKEN_EXPIRES = 2;// 2hours

    private static long REFRESH_TOKEN_EXPIRES = 30;// 30days

    protected void setProfile(AccountResponse profile, String openid) {
        String json = JacksonUtils.stringify(profile);

        redisTemplate.opsForValue().set(getProfileKey(openid), json, TOKEN_EXPIRES, TimeUnit.HOURS);
    }

    protected void saveToken(String openid, String token, String refreshToken) {
        redisTemplate.opsForValue().set(getTokenKey(openid), token, TOKEN_EXPIRES, TimeUnit.HOURS);

        redisTemplate.opsForValue().set(getRefreshTokenKey(openid), refreshToken, REFRESH_TOKEN_EXPIRES, TimeUnit.DAYS);
    }

    protected void clearToken(String openid) {
        redisTemplate.delete(getTokenKey(openid));

        redisTemplate.delete(getRefreshTokenKey(openid));
    }

    private String getProfileKey(String openid) {
        return CommonConstant.PROFILE_PREFIX + openid;
    }

    private String getTokenKey(String openid) {
        return CommonConstant.TOKEN_PREFIX + openid;
    }

    private String getRefreshTokenKey(String openid) {
        return CommonConstant.REFRESH_TOKEN_PREFIX + openid;
    }

}
