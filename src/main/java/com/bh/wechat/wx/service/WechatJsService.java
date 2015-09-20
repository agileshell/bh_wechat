package com.bh.wechat.wx.service;

import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.bh.wechat.constant.GlobalProperties;
import com.bh.wechat.wx.model.JsSignature;
import com.bh.wechat.wx.util.WechatUtil;

public class WechatJsService {

    public static Logger log = Logger.getLogger(WechatJsService.class);

    private static final String TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public static String getJsTicket(String accessToken) {
        String url = TICKET_URL.replace("ACCESS_TOKEN", accessToken);

        JSONObject jsonObject = WechatUtil.httpsRequest(url, "GET", null);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                log.error("获取access_token失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:"
                        + jsonObject.getString("errmsg"));

                return null;
            } else {
                return jsonObject.getString("ticket");
            }
        } else {
            return null;
        }
    }

    public static JsSignature getJsSignature(String token, String url) {
        String jsapi_ticket = getJsTicket(token);
        String noncestr = getRandomString(15);
        Long timestamp = System.currentTimeMillis() / 1000;

        StringBuilder sa = new StringBuilder();
        sa.append("jsapi_ticket=").append(jsapi_ticket);
        sa.append("&noncestr=").append(noncestr);
        sa.append("&timestamp=").append(timestamp);
        sa.append("&url=").append(url);
        String signature = DigestUtils.sha1Hex(sa.toString());

        JsSignature jsSignature = new JsSignature();
        jsSignature.setAppId(GlobalProperties.WECAHT_APP_ID);
        jsSignature.setNonceStr(noncestr);
        jsSignature.setTimestamp(timestamp);
        jsSignature.setSignature(signature);

        return jsSignature;
    }

    private static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }
}
