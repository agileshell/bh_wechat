package com.bh.wechat.wx.service;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.bh.wechat.wx.model.menu.Button;
import com.bh.wechat.wx.model.menu.Menu;
import com.bh.wechat.wx.util.WechatUtil;

/**
 * 菜单创建
 */
public class WechatMenuService {

    public static Logger log = Logger.getLogger(WechatMenuService.class);

    /**
     * 菜单创建（POST） 限100（次/天）
     */
    public static String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    /**
     * 菜单查询
     */
    public static String MENU_GET = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    /**
     * 菜单删除
     */
    public static String MENU_DEL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    /**
     * 创建菜单
     * 
     * @param jsonMenu
     *            json格式
     * @param token
     * @return 状态 0 表示成功、其他表示失败
     */
    public static Integer createMenu(String jsonMenu, String token) {
        int result = 0;

        if (token != null) {
            // 拼装创建菜单的url
            String url = MENU_CREATE.replace("ACCESS_TOKEN", token);
            // 调用接口创建菜单
            JSONObject jsonObject = WechatUtil.httpsRequest(url, "POST", jsonMenu);

            if (null != jsonObject) {
                if (0 != jsonObject.getInt("errcode")) {
                    result = jsonObject.getInt("errcode");
                    log.error("创建菜单失败 errcode:" + jsonObject.getInt("errcode") + "，errmsg:"
                            + jsonObject.getString("errmsg"));
                }
            }
        }
        return result;
    }

    /**
     * 创建菜单
     * 
     * @param menu
     *            菜单实例
     * @param token
     * @return 0表示成功，其他值表示失败
     */
    public static Integer createMenu(Menu menu, String token) {
        return createMenu(JSONObject.fromObject(menu).toString(), token);
    }

    /**
     * 查询菜单
     * 
     * @param token
     * @return 菜单结构json字符串
     */
    public static JSONObject getMenuJson(String token) {
        JSONObject result = null;

        if (token != null) {
            String url = MENU_GET.replace("ACCESS_TOKEN", token);
            result = WechatUtil.httpsRequest(url, "GET", null);
        }

        return result;
    }

    /**
     * 查询菜单
     * 
     * @param token
     * @return Menu 菜单对象
     */
    public static Menu getMenu(String token) {
        JSONObject json = getMenuJson(token).getJSONObject("menu");
        log.info(json);
        Menu menu = (Menu) JSONObject.toBean(json, Menu.class);

        return menu;
    }

    /**
     * 删除菜单
     * 
     * @param token
     */
    public static void deleteMenu(String token) {
        if (token != null) {
            String url = MENU_DEL.replace("ACCESS_TOKEN", token);
            WechatUtil.httpsRequest(url, "GET", null);
        }
    }

    public static void main(String[] args) {
        String token = WechatUtil.getToken();

        // getMenu(token);

        deleteMenu(token);

        Button sb1 = new Button("微商城", "view", null, "http://wx.vbaohui.com", null);
        Button sb2 = new Button("个人中心", "view", null, "http://wx.vbaohui.com/profile", null);
        Button btn1 = new Button("唯宝汇", "click", null, null, new Button[] { sb1, sb2 });

        Button btn2 = new Button("有奖活动", "view", null, "http://wx.vbaohui.com/campaigns", new Button[] {});

        Button sb3 = new Button("关于我们", "view", null, "http://wx.vbaohui.com/aboutus", null);
        Button sb4 = new Button("帮助中心", "view", null, "http://wx.vbaohui.com/helps", null);
        Button btn3 = new Button("服务", "click", null, null, new Button[] { sb3, sb4 });

        Menu menu = new Menu(new Button[] { btn1, btn2, btn3 });
        createMenu(menu, token);
    }
}