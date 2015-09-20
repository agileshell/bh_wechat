package com.bh.wechat.service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.RefreshTokenRequest;
import com.bh.wechat.request.RegistryRequest;
import com.bh.wechat.response.AccountResponse;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.RefreshTokenResponse;

public interface AccountService {

    BaseResponse followWechat(String openid, Integer brokerId) throws BhException;

    AccountResponse register(RegistryRequest registryRequest) throws BhException;

    AccountResponse login(String account, String password, String openid) throws BhException;

    RefreshTokenResponse refreshToken(RefreshTokenRequest requestData) throws BhException;

    AccountResponse getUserProfile(String openid, String token) throws BhException;

    BaseResponse changePassword(String password, String oldPassword, String token) throws BhException;

    BaseResponse changePayPassword(String password, String oldPassword, String token) throws BhException;

}