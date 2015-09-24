package com.bh.wechat.controller;

import java.beans.PropertyEditor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import com.bh.wechat.constant.CommonConstant;
import com.bh.wechat.constant.GlobalProperties;
import com.bh.wechat.constant.ResponseCode;
import com.bh.wechat.exception.BhException;
import com.bh.wechat.exception.BhExceptionFactory;
import com.bh.wechat.request.RefreshTokenRequest;
import com.bh.wechat.response.AccountResponse;
import com.bh.wechat.response.RefreshTokenResponse;
import com.bh.wechat.service.AccountService;
import com.bh.wechat.service.AddressService;
import com.bh.wechat.service.CartService;
import com.bh.wechat.service.CommonService;
import com.bh.wechat.service.CurrencyService;
import com.bh.wechat.service.MessageService;
import com.bh.wechat.service.OrderService;
import com.bh.wechat.service.ProductService;
import com.bh.wechat.service.WeChatService;
import com.bh.wechat.util.JacksonUtils;
import com.bh.wechat.wx.model.JsSignature;
import com.bh.wechat.wx.service.WechatJsService;
import com.bh.wechat.wx.service.WechatOAuthService;
import com.bh.wechat.wx.util.WechatUtil;

public abstract class BaseController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    protected AccountService accountService;

    @Autowired
    protected AddressService addressService;

    @Autowired
    protected CartService cartService;

    @Autowired
    protected CommonService commonService;

    @Autowired
    protected MessageService messageService;

    @Autowired
    protected OrderService orderService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected WeChatService weChatService;

    @Autowired
    protected CurrencyService currencyService;

    @Autowired
    protected HttpSession session;

    @Autowired
    @Qualifier("redisTemplate")
    protected StringRedisTemplate redisTemplate;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        PropertyEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ResponseCode code = ResponseCode.SERVER_ERROR;
        String errorMessage = null;

        if (ex instanceof BhException) {
            code = ((BhException) ex).getCode() != null ? ((BhException) ex).getCode() : code;
            errorMessage = ex.getMessage();
        } else {
            errorMessage = "server error";
        }

        ModelAndView mav = new ModelAndView();
        if (code.equals(ResponseCode.UNAUTHORIZED)) {
            mav.setViewName("redirect:/login");
        } else {
            mav.setViewName("redirect:/crash");
            mav.addObject("errorMessage", errorMessage);
        }

        return mav;
    }

    protected String getOpenid() {
        return (String) session.getAttribute("openid");
    }

    protected void setOpenid(String openid) {
        session.setAttribute("openid", openid);
    }

    protected boolean hasOpenid() {
        String openid = getOpenid();

        return StringUtils.isNoneBlank(openid);
    }

    protected boolean isLogin() {
        String openid = getOpenid();

        if (StringUtils.isNotEmpty(openid)) {
            String tokenKey = CommonConstant.TOKEN_PREFIX + openid;

            String token = redisTemplate.opsForValue().get(tokenKey);
            return StringUtils.isNotEmpty(token);
        } else {
            return false;
        }
    }

    protected boolean autoLogin() throws BhException {
        boolean flag = false;

        String openid = getOpenid();
        if (StringUtils.isNotEmpty(openid)) {

            String refreshTokenKey = CommonConstant.REFRESH_TOKEN_PREFIX + openid;
            String refreshToken = redisTemplate.opsForValue().get(refreshTokenKey);
            if (StringUtils.isNotEmpty(refreshToken)) {

                RefreshTokenRequest requestData = new RefreshTokenRequest();
                requestData.setOpenid(openid);
                requestData.setRefreshToken(refreshToken);

                RefreshTokenResponse response = accountService.refreshToken(requestData);
                if (response.isSuccess()) {
                    flag = true;
                }
            }
        }

        return flag;
    }

    protected AccountResponse getProfile() throws BhException {
        String openid = getOpenid();

        AccountResponse account = null;

        String json = redisTemplate.opsForValue().get(CommonConstant.PROFILE_PREFIX + openid);
        if (StringUtils.isEmpty(json)) {
            throw BhExceptionFactory.getException(BhException.class, ResponseCode.UNAUTHORIZED, null);
        } else {
            account = JacksonUtils.parse(json, AccountResponse.class);
        }

        return account;
    }

    protected String getToken() {
        String openid = getOpenid();

        return redisTemplate.opsForValue().get(CommonConstant.TOKEN_PREFIX + openid);
    }

    protected void setWechatAccessToken(String accessToken) {
        redisTemplate.opsForValue().set(CommonConstant.WEHCHAT_ACCESS_TOKEN, accessToken, 7180, TimeUnit.SECONDS);
    }

    protected String getWechatAccessToken() {
        String accessToken = redisTemplate.opsForValue().get(CommonConstant.WEHCHAT_ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = WechatUtil.getToken();

            setWechatAccessToken(accessToken);
        }

        return accessToken;
    }

    protected void setJsSignature(String url, JsSignature jsSignature) {
        String json = JacksonUtils.stringify(jsSignature);

        redisTemplate.opsForValue().set(CommonConstant.WEHCHAT_JS_SIGNATURE_PREFIX + url, json, 7180, TimeUnit.SECONDS);
    }

    protected JsSignature getJsSignature(String url) throws BhException {
        JsSignature jsSignature = null;

        String json = redisTemplate.opsForValue().get(CommonConstant.WEHCHAT_JS_SIGNATURE_PREFIX + url);
        if (StringUtils.isEmpty(json)) {
            jsSignature = WechatJsService.getJsSignature(getWechatAccessToken(), url);

            setJsSignature(url, jsSignature);
        } else {
            jsSignature = JacksonUtils.parse(json, JsSignature.class);
        }

        return jsSignature;
    }

    protected String getOauthRedirectUrl(String url) {
        return "redirect:"
                + WechatOAuthService.getOauthUrl(GlobalProperties.WX_DOAMIN.concat("oauth/redirect/").concat(url),
                        "UTF-8", "snsapi_base");
    }

    protected String getOauthUrl(String url) {
        return "redirect:"
                + WechatOAuthService.getOauthUrl(GlobalProperties.WX_DOAMIN.concat("oauth/").concat(url), "UTF-8",
                        "snsapi_base");
    }

    protected void setJdToken(String token) {
        String openid = getOpenid();

        redisTemplate.opsForValue().set(CommonConstant.JD_TOKEN__PREFIX + openid, token, 180, TimeUnit.DAYS);
    }

    protected String getJdToken() {
        String openid = getOpenid();

        return redisTemplate.opsForValue().get(CommonConstant.JD_TOKEN__PREFIX + openid);
    }

}
