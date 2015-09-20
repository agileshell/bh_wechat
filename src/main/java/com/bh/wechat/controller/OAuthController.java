package com.bh.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.wx.model.AccessTokenOAuth;
import com.bh.wechat.wx.service.WechatOAuthService;

@Controller
public class OAuthController extends BaseController {

    @RequestMapping("/oauth/{type}")
    public String oauthView(@PathVariable String type, String code) throws BhException {
        AccessTokenOAuth accessTokenOAuth = WechatOAuthService.getOAuthAccessToken(code);
        if (null != accessTokenOAuth) {
            String openid = accessTokenOAuth.getOpenid();
            // 每次网页授权都刷新绑定关系
            accountService.followWechat(openid, 0);

            setOpenid(openid);
        }

        if (type.equals("register")) {
            return "redirect:/register";
        } else if (type.equals("login")) {
            return "redirect:/login";
        } else {
            return "redirect:/home";
        }
    }

    @RequestMapping("/oauth/redirect/{url:.+}")
    public String oauthRedirect(@PathVariable String url, String code) throws BhException {
        AccessTokenOAuth accessTokenOAuth = WechatOAuthService.getOAuthAccessToken(code);
        if (null != accessTokenOAuth) {
            String openid = accessTokenOAuth.getOpenid();

            setOpenid(openid);
        }

        return "redirect:/".concat(url);
    }
}
