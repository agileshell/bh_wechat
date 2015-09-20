package com.bh.wechat.controller;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bh.wechat.constant.CommonConstant;
import com.bh.wechat.constant.GlobalProperties;
import com.bh.wechat.exception.BhException;
import com.bh.wechat.response.AccountResponse;
import com.bh.wechat.response.Message;
import com.bh.wechat.response.MessageDetailResponse;
import com.bh.wechat.response.MessageListResponse;
import com.bh.wechat.wx.model.JsSignature;
import com.bh.wechat.wx.service.WechatQRcodeService;

@Controller
public class UserController extends BaseController {

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profileView(Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("profile");
            }
        }

        if (isLogined) {
            AccountResponse profile = accountService.getUserProfile(getOpenid(), getToken());

            model.addAttribute("profile", profile);

            return "user/profile";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String listMessages(Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("messages");
            }
        }

        if (isLogined) {
            MessageListResponse messageResponse = messageService.listMessages(getToken());

            if (messageResponse.isSuccess()) {
                model.addAttribute("messages", null != messageResponse.getList() ? messageResponse.getList()
                        : new ArrayList<Message>());
            } else {
                model.addAttribute("messages", new ArrayList<Message>());
            }

            return "user/message-list";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/message/{messageId}", method = RequestMethod.GET)
    public String getMessageDetail(@PathVariable int messageId, Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        if (isLogined) {
            MessageDetailResponse messageResponse = messageService.getMessageDetail(messageId, getToken());
            model.addAttribute("message", messageResponse);

            return "user/message-detail";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/affiliate", method = RequestMethod.GET)
    public String affiliate(@RequestParam(defaultValue = "0") int userId, Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        if ((userId > 0 && isLogined) || isLogined) {
            AccountResponse profile = getProfile();
            userId = profile.getUserId();
            model.addAttribute("recommendUrl", GlobalProperties.WX_DOAMIN.concat("recommend/" + userId));
            model.addAttribute("doamin", GlobalProperties.WX_DOAMIN);
        } else {
            model.addAttribute("recommendUrl", "");
        }

        // get recommend QRcode from redis
        String qrcodeKey = CommonConstant.QRCODE_PREFIX + userId;
        String recommendQRcode = redisTemplate.opsForValue().get(qrcodeKey);
        if (StringUtils.isEmpty(recommendQRcode)) {
            String accessToken = getWechatAccessToken();
            String ticket = WechatQRcodeService.getTicket("QR_SCENE", userId, accessToken);
            recommendQRcode = WechatQRcodeService.getQrCodeImgURL(ticket);

            redisTemplate.opsForValue().set(qrcodeKey, recommendQRcode, 7, TimeUnit.DAYS);
        }
        model.addAttribute("recommendQRcode", recommendQRcode);

        JsSignature jsSignature = getJsSignature(GlobalProperties.WX_DOAMIN + "affiliate?userId=" + userId);
        model.addAttribute("jsSignature", jsSignature);

        return "user/affiliate";
    }
}
