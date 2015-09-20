package com.bh.wechat.wx.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.bh.wechat.wx.util.WechatUtil;

/**
 * 二维码
 */
public class WechatQRcodeService {

    public static Logger log = Logger.getLogger(WechatQRcodeService.class);

    /**
     * 获取ticket
     */
    static String QRCODE_ACTION = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

    /**
     * 获取二维码url
     */
    static String QRCODE_IMG_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

    /**
     * 二维码类型，临时
     */
    public static final String QRCODE_SCENE = "QR_SCENE";

    /**
     * 二维码类型，永久
     */
    public static final String QRCODE_LIMIT_SCENE = "QR_LIMIT_SCENE";

    /**
     * 获取ticket
     * 
     * @param actionName
     *            二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久
     * @param sceneId
     *            场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param token
     * @return ticket
     */
    public static String getTicket(String actionName, int sceneId, String token) {

        String url = QRCODE_ACTION.replace("TOKEN", token);

        String ticket = "";

        String qrdata = "{";
        if (actionName.equals("QR_SCENE")) {
            qrdata += "\"expire_seconds\": 604800, ";
        }
        qrdata += "\"action_name\": \"" + actionName + "\", \"action_info\": {\"scene\": {\"scene_id\": " + sceneId
                + "}}}";

        JSONObject jsonObject = WechatUtil.httpsRequest(url, "POST", qrdata);
        if (null != jsonObject) {
            String errcode = (String) jsonObject.get("errcode");
            if (StringUtils.isNotBlank(errcode) && !errcode.equals("0")) {
                log.error("二维码ticket请求失败，errcode:" + jsonObject.getInt("errcode") + "，errmsg:"
                        + jsonObject.getString("errmsg"));
            } else {
                ticket = jsonObject.getString("ticket");
            }
        }
        return ticket;

    }

    /**
     * 获取二维码图片链接地址
     * 
     * @param ticket
     * @return 二维码图片的url
     */
    public static String getQrCodeImgURL(String ticket) {
        try {
            ticket = URLEncoder.encode(ticket, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return QRCODE_IMG_URL.replace("TICKET", ticket);
    }

}
