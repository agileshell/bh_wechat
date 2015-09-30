package com.bh.wechat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.jdpay.PaySignEntity;
import com.bh.wechat.jdpay.WebPayReqDto;
import com.bh.wechat.jdpay.constant.MerchantConstant;
import com.bh.wechat.jdpay.utils.DESUtil;
import com.bh.wechat.jdpay.utils.SignUtil;
import com.bh.wechat.request.ListBhPointsWithdrawHistoryRequest;
import com.bh.wechat.request.ListCurrencyDealRequest;
import com.bh.wechat.request.LocationRequest;
import com.bh.wechat.request.RechargeBhPointsRequest;
import com.bh.wechat.request.WithdrawBhPointsRequest;
import com.bh.wechat.response.AccountResponse;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.BhPointsWithdrawHistory;
import com.bh.wechat.response.BhPointsWithdrawHistoryResponse;
import com.bh.wechat.response.CurrencyDealHistory;
import com.bh.wechat.response.CurrencyDealHistoryResponse;
import com.bh.wechat.response.Location;
import com.bh.wechat.response.LocationResponse;
import com.bh.wechat.response.RechargeBhPointsResponse;

@Controller
public class CurrencyController extends BaseController {

    @Resource
    private MerchantConstant merchantConstant;

    private static String version = "3.0";

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

    @RequestMapping(value = "/deal/history/{currency}", method = RequestMethod.GET)
    public String currencyDealHistoryView(@PathVariable String currency, Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("deal/history/" + currency);
            }
        }

        if (isLogined) {
            ListCurrencyDealRequest requestData = new ListCurrencyDealRequest();
            requestData.setToken(getToken());
            requestData.setCurrency(currency);
            CurrencyDealHistoryResponse response = currencyService.listCurrencyDealHistory(requestData);
            if (response.isSuccess()) {
                model.addAttribute("histories", response.getList());
            } else {
                model.addAttribute("histories", new ArrayList<CurrencyDealHistory>());
            }

            return "currency/deal-history";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/api/deal/history/{currency}", method = RequestMethod.GET)
    @ResponseBody
    public CurrencyDealHistoryResponse currencyDealHistory(@PathVariable String currency,
            ListCurrencyDealRequest requestData) throws BhException {
        requestData.setToken(getToken());
        requestData.setCurrency(currency);

        return currencyService.listCurrencyDealHistory(requestData);
    }

    @RequestMapping(value = "/bhPoints/withdraw/history", method = RequestMethod.GET)
    public String bhPointsWithdrawHistoryView(Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("bhPoints/withdraw/history");
            }
        }

        if (isLogined) {
            ListBhPointsWithdrawHistoryRequest requestData = new ListBhPointsWithdrawHistoryRequest();
            requestData.setToken(getToken());
            BhPointsWithdrawHistoryResponse response = currencyService.listBhPointsWithdrawHistory(requestData);
            if (response.isSuccess()) {
                model.addAttribute("histories", response.getList());
            } else {
                model.addAttribute("histories", new ArrayList<BhPointsWithdrawHistory>());
            }

            return "currency/withdraw-history";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/api/bhPoints/withdraw/history", method = RequestMethod.GET)
    @ResponseBody
    public BhPointsWithdrawHistoryResponse bhPointsWithdrawHistory(ListBhPointsWithdrawHistoryRequest requestData)
            throws BhException {
        requestData.setToken(getToken());

        return currencyService.listBhPointsWithdrawHistory(requestData);
    }

    @RequestMapping(value = "/recharge", method = RequestMethod.GET)
    public String rechargeView(Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("recharge");
            }
        }

        if (isLogined) {
            return "currency/recharge";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/rechargeSuccess", method = RequestMethod.GET)
    public String paySuccess(@RequestParam(required = false) String token,
            @RequestParam(required = false) String tradeNum) {
        setJdToken(token);

        return "currency/recharge-success";
    }

    @RequestMapping(value = "/rechargeFail", method = RequestMethod.GET)
    public String payFail(@RequestParam(required = false) String tradeNum) {
        return "currency/recharge-fail";
    }

    @RequestMapping(value = "/goRecharge", method = RequestMethod.GET)
    public String paySign(RechargeBhPointsRequest requestData, HttpServletRequest httpServletRequest)
            throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthUrl("login");
            }
        }

        requestData.setToken(getToken());
        RechargeBhPointsResponse responseData = currencyService.rechargeBhPoints(requestData);
        if (!responseData.isSuccess()) {
            return "currency/recharge-fail";
        }

        int tradeAmount = (int) (requestData.getAmount() * 100);
        String tradeName = "充值宝汇币" + requestData.getAmount();

        WebPayReqDto webPayReqDto = new WebPayReqDto();
        webPayReqDto.setVersion(version);
        webPayReqDto.setToken(getJdToken());
        webPayReqDto.setMerchantNum(merchantConstant.getMerchantNum());
        webPayReqDto.setMerchantRemark(responseData.getId() + "");
        webPayReqDto.setTradeNum(responseData.getId() + "");
        webPayReqDto.setTradeTime(responseData.getCreatedTime());
        webPayReqDto.setTradeName(tradeName.toString());
        webPayReqDto.setTradeDescription(responseData.getId() + "");
        webPayReqDto.setCurrency("CNY");
        webPayReqDto.setTradeAmount(tradeAmount + "");
        webPayReqDto.setSuccessCallbackUrl(merchantConstant.getRechargeSuccessCallbackUrl());
        webPayReqDto.setFailCallbackUrl(merchantConstant.getRechargeFailCallbackUrl());
        webPayReqDto.setNotifyUrl(merchantConstant.getNotifyUrl());

        PaySignEntity wePayMerchantSignReqDTO = new PaySignEntity();

        wePayMerchantSignReqDTO.setVersion(webPayReqDto.getVersion());
        wePayMerchantSignReqDTO.setToken(webPayReqDto.getToken());
        wePayMerchantSignReqDTO.setMerchantNum(webPayReqDto.getMerchantNum());
        wePayMerchantSignReqDTO.setMerchantRemark(webPayReqDto.getMerchantRemark());
        wePayMerchantSignReqDTO.setTradeNum(webPayReqDto.getTradeNum());
        wePayMerchantSignReqDTO.setTradeTime(webPayReqDto.getTradeTime());
        wePayMerchantSignReqDTO.setTradeName(webPayReqDto.getTradeName());
        wePayMerchantSignReqDTO.setTradeDescription(webPayReqDto.getTradeDescription());
        wePayMerchantSignReqDTO.setCurrency(webPayReqDto.getCurrency());
        wePayMerchantSignReqDTO.setTradeAmount(webPayReqDto.getTradeAmount());
        wePayMerchantSignReqDTO.setSuccessCallbackUrl(webPayReqDto.getSuccessCallbackUrl());
        wePayMerchantSignReqDTO.setFailCallbackUrl(webPayReqDto.getFailCallbackUrl());
        wePayMerchantSignReqDTO.setNotifyUrl(webPayReqDto.getNotifyUrl());

        // 商户签名
        String signStr =
                SignUtil.sign4SelectedKeys(wePayMerchantSignReqDTO, merchantConstant.getPayRSAPrivateKey(),
                        getSignList(wePayMerchantSignReqDTO));
        webPayReqDto.setMerchantSign(signStr);

        if ("1.0".equals(version)) {
            // 敏感信息未加密
        } else if ("2.0".equals(version)) {
            // 敏感信息加密
            try {
                // 获取商户 DESkey
                String desKey = merchantConstant.getMerchantDESKey();
                // 对敏感信息进行 DES加密
                webPayReqDto.setMerchantRemark(DESUtil.encrypt(webPayReqDto.getMerchantRemark(), desKey, "UTF-8"));
                webPayReqDto.setTradeNum(DESUtil.encrypt(webPayReqDto.getTradeNum(), desKey, "UTF-8"));
                webPayReqDto.setTradeName(DESUtil.encrypt(webPayReqDto.getTradeName(), desKey, "UTF-8"));
                webPayReqDto.setTradeDescription(DESUtil.encrypt(webPayReqDto.getTradeDescription(), desKey, "UTF-8"));
                webPayReqDto.setTradeTime(DESUtil.encrypt(webPayReqDto.getTradeTime(), desKey, "UTF-8"));
                webPayReqDto.setTradeAmount(DESUtil.encrypt(webPayReqDto.getTradeAmount(), desKey, "UTF-8"));
                webPayReqDto.setCurrency(DESUtil.encrypt(webPayReqDto.getCurrency(), desKey, "UTF-8"));
                webPayReqDto.setNotifyUrl(DESUtil.encrypt(webPayReqDto.getNotifyUrl(), desKey, "UTF-8"));
                webPayReqDto.setSuccessCallbackUrl(DESUtil.encrypt(webPayReqDto.getSuccessCallbackUrl(), desKey,
                        "UTF-8"));
                webPayReqDto.setFailCallbackUrl(DESUtil.encrypt(webPayReqDto.getFailCallbackUrl(), desKey, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("3.0".equals(webPayReqDto.getVersion()) || "3.0.5".equals(webPayReqDto.getVersion())) {
            // 敏感信息加密
            try {
                // 获取商户 DESkey
                String desKey = merchantConstant.getMerchantDESKey();
                // 对敏感信息进行 DES加密
                webPayReqDto.setMerchantRemark(DESUtil.encrypt(webPayReqDto.getMerchantRemark(), desKey, "UTF-8"));
                webPayReqDto.setTradeNum(DESUtil.encrypt(webPayReqDto.getTradeNum(), desKey, "UTF-8"));
                webPayReqDto.setTradeName(DESUtil.encrypt(webPayReqDto.getTradeName(), desKey, "UTF-8"));
                webPayReqDto.setTradeDescription(DESUtil.encrypt(webPayReqDto.getTradeDescription(), desKey, "UTF-8"));
                webPayReqDto.setTradeTime(DESUtil.encrypt(webPayReqDto.getTradeTime(), desKey, "UTF-8"));
                webPayReqDto.setCurrency(DESUtil.encrypt(webPayReqDto.getCurrency(), desKey, "UTF-8"));
                webPayReqDto.setTradeAmount(DESUtil.encrypt(webPayReqDto.getTradeAmount(), desKey, "UTF-8"));
                webPayReqDto.setSuccessCallbackUrl(DESUtil.encrypt(webPayReqDto.getSuccessCallbackUrl(), desKey,
                        "UTF-8"));
                webPayReqDto.setFailCallbackUrl(DESUtil.encrypt(webPayReqDto.getFailCallbackUrl(), desKey, "UTF-8"));
                webPayReqDto.setNotifyUrl(DESUtil.encrypt(webPayReqDto.getNotifyUrl(), desKey, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        httpServletRequest.setAttribute("serverUrl", merchantConstant.getWangyinServerPayUrl());
        httpServletRequest.setAttribute("tradeInfo", webPayReqDto);
        httpServletRequest.setAttribute("tradeAmount", requestData.getAmount());
        httpServletRequest.setAttribute("tradeName", tradeName);

        return "currency/recharge-submit";
    }

    private List<String> getSignList(PaySignEntity wePayMerchantSignReqDTO) {
        List<String> signedKeyList = new ArrayList<String>();
        // 固定验签字段
        signedKeyList.add("currency");
        signedKeyList.add("merchantNum");
        signedKeyList.add("merchantRemark");
        signedKeyList.add("tradeAmount");
        signedKeyList.add("tradeDescription");
        signedKeyList.add("tradeName");
        signedKeyList.add("tradeTime");
        signedKeyList.add("tradeNum");
        signedKeyList.add("notifyUrl");
        signedKeyList.add("successCallbackUrl");
        signedKeyList.add("failCallbackUrl");

        return signedKeyList;
    }

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    public String withdrawView(Model model) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthRedirectUrl("withdraw");
            }
        }

        if (isLogined) {
        	model.addAttribute("firstLocations", getLocations(0));

            return "currency/withdraw";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/api/withdraw", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse withdraw(WithdrawBhPointsRequest requestData) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            isLogined = autoLogin();
        }

        BaseResponse response = null;
        if (isLogined) {
            requestData.setToken(getToken());
            response = currencyService.withdrawBhPoints(requestData);
        } else {
            response = new BaseResponse();
            response.setRet(1001);
        }

        return response;
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
