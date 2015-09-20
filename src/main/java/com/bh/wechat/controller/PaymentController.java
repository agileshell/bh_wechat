package com.bh.wechat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.jdpay.PaySignEntity;
import com.bh.wechat.jdpay.WebPayReqDto;
import com.bh.wechat.jdpay.constant.MerchantConstant;
import com.bh.wechat.jdpay.utils.DESUtil;
import com.bh.wechat.jdpay.utils.SignUtil;
import com.bh.wechat.request.OrderDetailRequest;
import com.bh.wechat.response.OrderDetailResponse;
import com.bh.wechat.response.OrderProduct;
import com.bh.wechat.response.PaymentMethod;

@Controller
public class PaymentController extends BaseController {

    @Resource
    private MerchantConstant merchantConstant;

    private static String version = "3.0";

    @RequestMapping(value = "/paySuccess", method = RequestMethod.GET)
    public String paySuccess(@RequestParam(required = false) String token,
            @RequestParam(required = false) String tradeNum) {
        setJdToken(token);

        return "order/pay-success";
    }

    @RequestMapping(value = "/payFail", method = RequestMethod.GET)
    public String payFail(@RequestParam(required = false) String tradeNum) {
        return "order/pay-fail";
    }

    @RequestMapping(value = "/gopay/{orderId}", method = RequestMethod.GET)
    public String paySign(@PathVariable int orderId, HttpServletRequest httpServletRequest) throws BhException {
        boolean isLogined = isLogin();
        if (!isLogined) {
            if (hasOpenid()) {
                isLogined = autoLogin();
            } else {
                return getOauthUrl("login");
            }
        }

        OrderDetailRequest orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setOrderId(orderId);
        orderDetailRequest.setToken(getToken());
        OrderDetailResponse order = orderService.getOrderDetail(orderDetailRequest);
        if (order.getRet() != 0) {// 订单不存在
            return "redirect:/order/" + orderId;
        }

        PaymentMethod paymentMethod = order.getPaymentMethod();
        if (!order.getStatus().equals("Notred") || paymentMethod.getPaymentMethodId() != 1) {// 不是未确认订单
                                                                                             // 或者
                                                                                             // 不是网银在线支付
            return "redirect:/order/" + orderId;
        }

        int tradeAmount = (int) (paymentMethod.getAmount() * 100);
        List<OrderProduct> products = order.getProducts();
        StringBuilder tradeName = new StringBuilder();
        for (OrderProduct product : products) {
            tradeName.append(product.getName()).append("|");
        }
        tradeName.delete(tradeName.length() - 1, tradeName.length());

        WebPayReqDto webPayReqDto = new WebPayReqDto();
        webPayReqDto.setVersion(version);
        webPayReqDto.setToken(getJdToken());
        webPayReqDto.setMerchantNum(merchantConstant.getMerchantNum());
        webPayReqDto.setMerchantRemark(order.getOrderId() + "");
        webPayReqDto.setTradeNum(order.getOrderNumber());
        webPayReqDto.setTradeTime(order.getCreatedTime());
        webPayReqDto.setTradeName(tradeName.toString());
        webPayReqDto.setTradeDescription(order.getOrderId() + "");
        webPayReqDto.setCurrency("CNY");
        webPayReqDto.setTradeAmount(tradeAmount + "");
        webPayReqDto.setSuccessCallbackUrl(merchantConstant.getSuccessCallbackUrl());
        webPayReqDto.setFailCallbackUrl(merchantConstant.getFailCallbackUrl());
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
        String signStr = SignUtil.sign4SelectedKeys(wePayMerchantSignReqDTO, merchantConstant.getPayRSAPrivateKey(),
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
        httpServletRequest.setAttribute("tradeAmount", paymentMethod.getAmount());
        httpServletRequest.setAttribute("products", products);

        return "order/pay-submit";
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
}
