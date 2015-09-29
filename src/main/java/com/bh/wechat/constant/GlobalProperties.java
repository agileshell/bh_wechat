package com.bh.wechat.constant;

import java.util.Properties;

import com.bh.wechat.util.PropertiesUtil;

public final class GlobalProperties {

    private static final String PROPERTIES_PATH = "/global.properties";

    public static String WECAHT_APP_ID = "";

    public static String WECAHT_APP_SECRET = "";

    public static String WECAHT_TOKEN = "";

    public static String API_DOMAIN = "";

    public static String WX_DOAMIN = "";

    static {
        Properties globalProperties = PropertiesUtil.getProperties(PROPERTIES_PATH);
        if (PropertiesUtil.get(globalProperties, "wechat.app_id") != null) {
            WECAHT_APP_ID = PropertiesUtil.get(globalProperties, "wechat.app_id");
        }
        if (PropertiesUtil.get(globalProperties, "wechat.app_secret") != null) {
            WECAHT_APP_SECRET = PropertiesUtil.get(globalProperties, "wechat.app_secret");
        }
        if (PropertiesUtil.get(globalProperties, "wechat.token") != null) {
            WECAHT_TOKEN = PropertiesUtil.get(globalProperties, "wechat.token");
        }
        if (PropertiesUtil.get(globalProperties, "api.domain") != null) {
            API_DOMAIN = PropertiesUtil.get(globalProperties, "api.domain").concat("/");
        }
        if (PropertiesUtil.get(globalProperties, "wx.domain") != null) {
            WX_DOAMIN = PropertiesUtil.get(globalProperties, "wx.domain").concat("/");
        }
    }

    private GlobalProperties() {
        // empty
    }
}
