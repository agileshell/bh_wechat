package com.bh.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.response.AccountResponse;

@Controller
public class CurrencyController extends BaseController {

    @RequestMapping(value = "/bhPoints", method = RequestMethod.GET)
    public String bhPointsView(Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("bhPoints");
            }
        }

        if (isLogined) {
            AccountResponse profile = accountService.getUserProfile(getOpenid(), getToken());

            model.addAttribute("bhPoints", profile.getBhPoints());

            return "currency/bhpoints";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/dzPoints", method = RequestMethod.GET)
    public String dzPointsView(Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("bhPoints");
            }
        }

        if (isLogined) {
            AccountResponse profile = accountService.getUserProfile(getOpenid(), getToken());

            model.addAttribute("dzPoints", profile.getDzPoints());

            return "currency/dzpoints";
        } else {
            return "redirect:/login";
        }
    }

}
