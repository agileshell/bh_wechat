package com.bh.wechat.wx.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.bh.wechat.constant.GlobalProperties;
import com.bh.wechat.wx.model.AccessTokenOAuth;
import com.bh.wechat.wx.model.WechatUser;
import com.bh.wechat.wx.util.WechatUtil;

/**
 * oAuth服务
 */
public class WechatOAuthService {

    public static Logger log = Logger.getLogger(WechatOAuthService.class);

    /**
     * wechat oauth url
     */
    public static String OAUTH =
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    /**
     * 通过oauth获取用户详细信息
     */
    public static String GET_USER_INFO_OAUTH =
            "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 获取oauth网页认证的token
     */
    public static String GET_ACCESS_TOKEN_OAUTH =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";


    /**
     * 获得Oauth认证的URL
     * 
     * @param redirectUrl 跳转的url
     * @param charset 字符集格式
     * @param scope OAUTH scope
     * @return oauth url
     */
    public static String getOauthUrl(String redirectUrl, String charset, String scope) {
        String url = "";
        try {
            url =
                    OAUTH.replace("APPID", GlobalProperties.WECAHT_APP_ID)
                            .replace("REDIRECT_URI", URLEncoder.encode(redirectUrl, charset)).replace("SCOPE", scope);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 获取Access_Token（oAuth认证,此access_token与基础支持的access_token不同）
     * 
     * @param code 用户授权后得到的code
     * @return AccessTokenOAuth对象
     */
    public static AccessTokenOAuth getOAuthAccessToken(String code) {
        String url =
                GET_ACCESS_TOKEN_OAUTH.replace("APPID", GlobalProperties.WECAHT_APP_ID)
                        .replace("SECRET", GlobalProperties.WECAHT_APP_SECRET).replace("CODE", code);

        JSONObject jsonObject = WechatUtil.httpsRequest(url, "POST", null);

        AccessTokenOAuth accessTokenOAuth = null;

        if (null != jsonObject) {
            String errcode = (String) jsonObject.get("errcode");
            if (StringUtils.isNotBlank(errcode) && !errcode.equals("0")) {
                log.error("获取access_token失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:"
                        + jsonObject.getString("errmsg"));
            } else {
                accessTokenOAuth = new AccessTokenOAuth();
                accessTokenOAuth.setAccessToken(jsonObject.getString("access_token"));
                accessTokenOAuth.setExpiresIn(jsonObject.getInt("expires_in"));
                accessTokenOAuth.setRefreshToken(jsonObject.getString("refresh_token"));
                accessTokenOAuth.setOpenid(jsonObject.getString("openid"));
                accessTokenOAuth.setScope(jsonObject.getString("scope"));
            }
        }
        return accessTokenOAuth;
    }

    /**
     * 通过oauth获取用户详细信息
     * 
     * @param token
     * @param openid
     * @return UserWeiXin对象
     */
    public static WechatUser getUserInfoOauth(String token, String openid) {
        WechatUser user = null;
        if (token != null) {

            String url = GET_USER_INFO_OAUTH.replace("ACCESS_TOKEN", token).replace("OPENID", openid);

            JSONObject jsonObject = WechatUtil.httpsRequest(url, "POST", null);

            if (null != jsonObject) {
                String errcode = (String) jsonObject.get("errcode");
                if (StringUtils.isNotBlank(errcode) && !errcode.equals("0")) {
                    log.error("获取用户信息失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:"
                            + jsonObject.getString("errmsg"));
                } else {
                    user = new WechatUser();
                    user.setOpenid(jsonObject.getString("openid"));
                    user.setNickname(jsonObject.getString("nickname"));
                    user.setSex(jsonObject.getInt("sex"));
                    user.setCity(jsonObject.getString("city"));
                    user.setCountry(jsonObject.getString("country"));
                    user.setProvince(jsonObject.getString("province"));
                    user.setLanguage(jsonObject.getString("language"));
                    user.setPrivilege(jsonObject.getString("privilege"));
                }
            }
        }

        return user;
    }
}
