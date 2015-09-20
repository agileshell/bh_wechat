package com.bh.wechat.wx.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.bh.wechat.wx.model.WechatUser;
import com.bh.wechat.wx.util.WechatUtil;

/**
 * 用户管理
 */
public class WechatUserService {

    public static Logger log = Logger.getLogger(WechatUserService.class);

    /**
     * 获取用户详细信息
     */
    public static String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 获取用户openid列表
     */
    public static String GET_USER_OPENID_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";

    /**
     * 获取用户详细信息
     * 
     * @param token
     * @param openid
     * @return UserWeiXin 用户详细信息
     */
    public static WechatUser getUserInfo(String openid, String token) {
        WechatUser user = null;

        if (token != null) {
            String url = GET_USER_INFO.replace("ACCESS_TOKEN", token).replace("OPENID", openid);
            JSONObject jsonObject = WechatUtil.httpsRequest(url, "POST", null);

            if (null != jsonObject) {
                String errcode = (String) jsonObject.get("errcode");
                if (StringUtils.isNotBlank(errcode) && !errcode.equals("0")) {
                    log.error("获取用户信息失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:"
                            + jsonObject.getString("errmsg"));
                } else {
                    user = new WechatUser();
                    user.setSubscribe(jsonObject.getInt("subscribe"));
                    user.setOpenid(jsonObject.getString("openid"));
                    user.setNickname(jsonObject.getString("nickname"));
                    user.setSex(jsonObject.getInt("sex"));
                    user.setCity(jsonObject.getString("city"));
                    user.setCountry(jsonObject.getString("country"));
                    user.setProvince(jsonObject.getString("province"));
                    user.setLanguage(jsonObject.getString("language"));
                    user.setHeadimgurl(jsonObject.getString("headimgurl"));
                    user.setSubscribe_time(jsonObject.getLong("subscribe_time"));
                }
            }

        }
        return user;
    }

    /**
     * 获取关注者OpenID列表
     * 
     * @param token
     * @return List<String> 关注者openID列表
     */
    public static List<String> getUserOpenIdList(String token) {
        List<String> list = null;
        if (token != null) {
            String url = GET_USER_OPENID_LIST.replace("ACCESS_TOKEN", token).replace("NEXT_OPENID", "");

            JSONObject jsonObject = WechatUtil.httpsRequest(url, "POST", null);

            if (null != jsonObject) {
                String errcode = (String) jsonObject.get("errcode");
                if (StringUtils.isNotBlank(errcode) && !errcode.equals("0")) {
                    log.error("获取关注用户列表失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:"
                            + jsonObject.getString("errmsg"));
                } else {
                    list = new ArrayList<String>();
                    JSONObject data = jsonObject.getJSONObject("data");
                    String openidStr = data.getString("openid");
                    openidStr = openidStr.substring(1, openidStr.length() - 1);
                    openidStr = openidStr.replace("\"", "");
                    String openidArr[] = openidStr.split(",");
                    for (int i = 0; i < openidArr.length; i++) {
                        list.add(openidArr[i]);
                    }
                }
            }

        }
        return list;
    }

    /**
     * 获取关注者列表
     * 
     * @param token
     * @return List<UserWeiXin> 关注者列表信息
     */
    public static List<WechatUser> getUserList(String token) {
        List<WechatUser> list = new ArrayList<WechatUser>();

        // 获取关注用户openid列表
        List<String> listStr = getUserOpenIdList(token);

        if (listStr == null || listStr.size() == 0) {
            return null;
        }
        for (int i = 0; i < listStr.size(); i++) {
            // 根据openid查询用户信息
            WechatUser user = getUserInfo(listStr.get(i), token);
            if (user != null) {
                list.add(user);
            }
        }
        return list;
    }

}
