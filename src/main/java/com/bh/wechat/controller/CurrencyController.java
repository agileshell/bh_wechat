package com.bh.wechat.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.ListCurrencyDealRequest;
import com.bh.wechat.response.AccountResponse;
import com.bh.wechat.response.CurrencyDealHistory;
import com.bh.wechat.response.CurrencyDealHistoryResponse;

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

            ListCurrencyDealRequest requestData = new ListCurrencyDealRequest();
            requestData.setToken(getToken());
            requestData.setCurrency("dzPoints");
            CurrencyDealHistoryResponse response = currencyService.listCurrencyDealHistory(requestData);
            if (response.isSuccess()) {
                model.addAttribute("histories", response.getList());
            } else {
                model.addAttribute("histories", new ArrayList<CurrencyDealHistory>());
            }

            return "currency/dzpoints";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/api/dzPoints/history", method = RequestMethod.GET)
    @ResponseBody
    public CurrencyDealHistoryResponse listProducts(ListCurrencyDealRequest requestData) throws BhException {
        requestData.setToken(getToken());
        requestData.setCurrency("dzPoints");

        return currencyService.listCurrencyDealHistory(requestData);
    }
}
