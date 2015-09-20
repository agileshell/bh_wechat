package com.bh.wechat.service.impl;

import org.springframework.stereotype.Service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.gateway.ApiUri;
import com.bh.wechat.gateway.GatewayProxyRequest;
import com.bh.wechat.request.RefreshTokenRequest;
import com.bh.wechat.request.RegistryRequest;
import com.bh.wechat.response.AccountResponse;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.RefreshTokenResponse;
import com.bh.wechat.service.AccountService;

@Service
public class AccountServiceImpl extends BaseService implements AccountService {

    @Override
    public BaseResponse followWechat(String openid, Integer brokerId) throws BhException {
        GatewayProxyRequest<BaseResponse> req = new GatewayProxyRequest<BaseResponse>()
                .responseType(BaseResponse.class).apiUri(ApiUri.FOLLOW_WECHAT).parameter("openid", openid)
                .parameter("brokerId", brokerId);

        return httpGatewayTemplate.invoke(req);
    }

    @Override
    public AccountResponse register(RegistryRequest registryRequest) throws BhException {
        AccountResponse profile = httpGatewayTemplate.invoke(AccountResponse.class, registryRequest);
        if (profile.isSuccess()) {
            setProfile(profile, registryRequest.getOpenid());

            saveToken(registryRequest.getOpenid(), profile.getToken(), profile.getRefreshToken());
        }

        return profile;
    }

    @Override
    public AccountResponse login(String account, String password, String openid) throws BhException {
        GatewayProxyRequest<AccountResponse> req = new GatewayProxyRequest<AccountResponse>()
                .responseType(AccountResponse.class).apiUri(ApiUri.LOGIN).parameter("account", account)
                .parameter("password", password).parameter("openid", openid);
        AccountResponse profile = httpGatewayTemplate.invoke(req);
        if (profile.isSuccess()) {
            setProfile(profile, openid);

            saveToken(openid, profile.getToken(), profile.getRefreshToken());
        }

        return profile;
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest requestData) throws BhException {
        RefreshTokenResponse response = httpGatewayTemplate.invoke(RefreshTokenResponse.class, requestData);
        if (response.isSuccess()) {
            saveToken(requestData.getOpenid(), response.getToken(), response.getRefreshToken());
        } else {
            clearToken(requestData.getOpenid());
        }

        return response;
    }

    @Override
    public AccountResponse getUserProfile(String openid, String token) throws BhException {
        GatewayProxyRequest<AccountResponse> req = new GatewayProxyRequest<AccountResponse>()
                .responseType(AccountResponse.class).apiUri(ApiUri.GET_USER_INFO).parameter("token", token);
        AccountResponse profile = httpGatewayTemplate.invoke(req);
        if (profile.isSuccess()) {
            setProfile(profile, openid);
        }

        return profile;
    }

    @Override
    public BaseResponse changePassword(String password, String oldPassword, String token) throws BhException {
        GatewayProxyRequest<BaseResponse> req = new GatewayProxyRequest<BaseResponse>()
                .responseType(BaseResponse.class).apiUri(ApiUri.CHANGE_PASSWORD).parameter("token", token)
                .parameter("password", password).parameter("oldPassword", oldPassword);

        return httpGatewayTemplate.invoke(req);
    }

    @Override
    public BaseResponse changePayPassword(String password, String oldPassword, String token) throws BhException {
        GatewayProxyRequest<BaseResponse> req = new GatewayProxyRequest<BaseResponse>()
                .responseType(BaseResponse.class).apiUri(ApiUri.CHANGE_PAYPASSWD).parameter("token", token)
                .parameter("password", password).parameter("oldPassword", oldPassword);

        return httpGatewayTemplate.invoke(req);
    }
}
