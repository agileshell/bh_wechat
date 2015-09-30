package com.bh.wechat.controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bh.wechat.constant.GlobalProperties;
import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.LocationRequest;
import com.bh.wechat.request.RegistryRequest;
import com.bh.wechat.response.AccountResponse;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.Location;
import com.bh.wechat.response.LocationResponse;
import com.bh.wechat.wx.model.AccessTokenOAuth;
import com.bh.wechat.wx.service.WechatOAuthService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@Controller
public class AccountController extends BaseController {

    @Autowired
    private Producer captchaProducer = null;

    @RequestMapping("/login")
    public String loginView() throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthUrl("login");
            }
        }

        if (isLogined) {
            return "redirect:/profile";
        } else {
            // openid
            String openid = getOpenid();

            if (StringUtils.isBlank(openid)) {
                return getOauthUrl("login");
            } else {
                return "account/login";
            }
        }
    }

    @RequestMapping("/api/login")
    @ResponseBody
    public AccountResponse login(@RequestParam(required = true) String userName,
            @RequestParam(required = true) String password, @RequestParam(required = true) String captcha)
                    throws BhException {
        AccountResponse accountResponse = new AccountResponse();
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password) || StringUtils.isBlank(captcha)) {
            accountResponse.setRet(1000);
            accountResponse.setMsg("参数错误");
        } else {
            String captchaCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (captcha.equals(captchaCode)) {
                // openid
                String openid = getOpenid();

                accountResponse = accountService.login(userName, password, openid);
            } else {
                accountResponse.setRet(5000);
                accountResponse.setMsg("验证码不正确");
            }
        }

        return accountResponse;
    }

    @RequestMapping("/follow")
    public String follow() throws BhException {
        return getOauthUrl("follow");
    }

    @RequestMapping("/register")
    public String registerView(Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthUrl("register");
            }
        }

        if (isLogined) {
            return "redirect:/profile";
        } else {
            // openid
            String openid = getOpenid();

            if (StringUtils.isBlank(openid)) {
                return getOauthUrl("register");
            } else {
                model.addAttribute("firstLocations", getLocations(0));

                return "account/register";
            }
        }
    }

    @RequestMapping("/api/register")
    @ResponseBody
    public AccountResponse register(RegistryRequest requestData, @RequestParam(required = true) String captcha)
            throws BhException {
        AccountResponse accountResponse = new AccountResponse();
        if (StringUtils.isBlank(captcha)) {
            accountResponse.setRet(1000);
            accountResponse.setMsg("参数错误");
        } else {
            String captchaCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (captcha.equals(captchaCode)) {
                // openid
                requestData.setOpenid(getOpenid());

                accountResponse = accountService.register(requestData);
            } else {
                accountResponse.setRet(5000);
                accountResponse.setMsg("验证码不正确");
            }
        }

        return accountResponse;
    }

    @RequestMapping("/changePassword")
    public String changePasswordView() throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("changePassword");
            }
        }

        if (isLogined) {
            return "account/change-pwd";
        } else {

            return "redirect:/login";
        }
    }

    @RequestMapping("/api/changePassword")
    @ResponseBody
    public BaseResponse changePassword(@RequestParam(required = true) String password,
            @RequestParam(required = true) String oldPassword, @RequestParam(required = true) String captcha)
                    throws BhException {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isBlank(password) || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(captcha)) {
            response.setRet(1000);
            response.setMsg("参数错误");
        } else {
            String captchaCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (captcha.equals(captchaCode)) {
                response = accountService.changePassword(password, oldPassword, getToken());
            } else {
                response.setRet(5000);
                response.setMsg("验证码不正确");
            }
        }

        return response;
    }

    @RequestMapping("/changePayPassword")
    public String changePayPasswordView() throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("changePayPassword");
            }
        }

        if (isLogined) {
            return "account/change-pay-pwd";
        } else {

            return "redirect:/login";
        }
    }

    @RequestMapping("/api/changePayPassword")
    @ResponseBody
    public BaseResponse changePayPassword(@RequestParam(required = true) String password,
            @RequestParam(required = true) String oldPassword, @RequestParam(required = true) String captcha)
                    throws BhException {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isBlank(password) || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(captcha)) {
            response.setRet(1000);
            response.setMsg("参数错误");
        } else {
            String captchaCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (captcha.equals(captchaCode)) {
                response = accountService.changePayPassword(password, oldPassword, getToken());
            } else {
                response.setRet(5000);
                response.setMsg("验证码不正确");
            }
        }

        return response;
    }

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }

        return null;
    }

    @RequestMapping("/recommend/{userId}")
    public String recommend(@PathVariable int userId) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        if (isLogined) {
            return "redirect:/";
        } else {

            return "redirect:" + WechatOAuthService.getOauthUrl(
                    GlobalProperties.WX_DOAMIN.concat("/oauth/recommend/").concat(userId + ""), "UTF-8",
                    "snsapi_userinfo");
        }
    }

    @RequestMapping("/oauth/recommend/{userId}")
    public String recommendOauth(@PathVariable int userId, String code) throws BhException {
        AccessTokenOAuth accessTokenOAuth = WechatOAuthService.getOAuthAccessToken(code);
        if (null != accessTokenOAuth) {
            String openid = accessTokenOAuth.getOpenid();
            if (userId >= 0) {
                accountService.followWechat(openid, userId);
            }

            setOpenid(openid);
        }

        return "redirect:/";
    }

    private List<Location> getLocations(int parentId) throws BhException {
        LocationRequest request = new LocationRequest();
        request.setParentId(parentId);
        LocationResponse locationResponse = addressService.getLocations(request);
        if (locationResponse.isSuccess()) {
            return locationResponse.getList();
        } else {
            return new ArrayList<Location>();
        }
    }
}
