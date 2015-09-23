package com.bh.wechat.gateway;

import org.springframework.http.HttpMethod;

import com.bh.wechat.constant.GlobalProperties;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月5日 下午3:30:56
 */
public enum ApiUri {
    // 注册
    REGISTRY(GlobalProperties.API_DOMAIN.concat("registry"), HttpMethod.POST),

    // 登录
    LOGIN(GlobalProperties.API_DOMAIN.concat("login"), HttpMethod.POST),

    // 权限自动续期,获取 Access Token
    REFRESH_TOKEN(GlobalProperties.API_DOMAIN.concat("token"), HttpMethod.GET),

    // 关注公众号
    FOLLOW_WECHAT(GlobalProperties.API_DOMAIN.concat("follow"), HttpMethod.POST),

    // 获取用户基本信息
    GET_USER_INFO(GlobalProperties.API_DOMAIN.concat("user"), HttpMethod.GET),

    // 修改登录密码
    CHANGE_PASSWORD(GlobalProperties.API_DOMAIN.concat("password"), HttpMethod.POST),

    // 修改支付密码
    CHANGE_PAYPASSWD(GlobalProperties.API_DOMAIN.concat("payPasswd"), HttpMethod.POST),

    // 获取地址列表
    GET_ADDRESSES(GlobalProperties.API_DOMAIN.concat("addresses"), HttpMethod.GET),

    // 新增地址
    CREATE_ADDRESS(GlobalProperties.API_DOMAIN.concat("address"), HttpMethod.POST),

    // 获取地址详情
    GET_ADDRESS_DETAIL(GlobalProperties.API_DOMAIN.concat("address/GetAdd/{addressId}"), HttpMethod.GET),

    // 修改地址
    UPDATE_ADDRESS(GlobalProperties.API_DOMAIN.concat("address/UpAdd/{addressId}"), HttpMethod.POST),

    // 删除地址
    DELETE_ADDRESS(GlobalProperties.API_DOMAIN.concat("address/DeAdd/{addressId}"), HttpMethod.DELETE),

    // 设置默认地址
    SET_DEFAULT_ADDRESS(GlobalProperties.API_DOMAIN.concat("address/defaults/{addressId}/default"), HttpMethod.POST),

    // 获取默认地址
    GET_DEFAULT_ADDRESS(GlobalProperties.API_DOMAIN.concat("address/defaults"), HttpMethod.GET),

    // 获取地区
    GET_LOCATION(GlobalProperties.API_DOMAIN.concat("locations/GetID/{parentId}"), HttpMethod.GET),

    // 站内信息列表
    GET_MESSAGES(GlobalProperties.API_DOMAIN.concat("messages"), HttpMethod.GET),

    // 阅读站内消息
    GET_MESSAGE_DETAIL(GlobalProperties.API_DOMAIN.concat("message/{messageId}"), HttpMethod.GET),

    // 宝汇币充值
    RECHARGE_BH_POINTS(GlobalProperties.API_DOMAIN.concat("bhPoints/recharge"), HttpMethod.POST),
    // 宝汇币提现
    WITHDRAW_BH_POINTS(GlobalProperties.API_DOMAIN.concat("bhPoints/withdraw"), HttpMethod.POST),
    // 宝汇币提现明细
    BH_POINTS_WITHDRAW_HISTORY(GlobalProperties.API_DOMAIN.concat("bhPoints/withdraw/history"), HttpMethod.GET),
    // 交易明细
    CURRENCY_DEAL_HISTORY(GlobalProperties.API_DOMAIN.concat("deal/history/{currency}"), HttpMethod.GET),

    // 分类
    GET_CATEGORIES(GlobalProperties.API_DOMAIN.concat("categories"), HttpMethod.GET),

    // 商品列表
    GET_PRODUCTS(GlobalProperties.API_DOMAIN.concat("products"), HttpMethod.GET),

    // 商品详情
    GET_PRODUCT_DETAIL(GlobalProperties.API_DOMAIN.concat("product/GetID/{productId}"), HttpMethod.GET),

    // 获取购物车商品列表
    GET_CART_PRODUCTS(GlobalProperties.API_DOMAIN.concat("cart/products"), HttpMethod.GET),

    // 把商品加入购物车
    ADD_CART_PRODUCT(GlobalProperties.API_DOMAIN.concat("cart/product/{productId}"), HttpMethod.POST),

    // 从购物车中移除商品
    REMOVE_CART_PRODUCT(GlobalProperties.API_DOMAIN.concat("cart/DeID/{cartProductId}"), HttpMethod.DELETE),

    // 修改购物车中商品的数量
    UPDATE_CART_PRODUCT(GlobalProperties.API_DOMAIN.concat("cart/UpID/{cartProductId}"), HttpMethod.POST),

    // 获取订单列表
    GET_ORDERS(GlobalProperties.API_DOMAIN.concat("orders"), HttpMethod.GET),

    // 直接购买
    DIRECTLY_CREATE_ORDER(GlobalProperties.API_DOMAIN.concat("order/directly"), HttpMethod.POST),

    // 下单
    CREATE_ORDER(GlobalProperties.API_DOMAIN.concat("order"), HttpMethod.POST),

    // 获取订单详情
    GET_ORDER_DETAIL(GlobalProperties.API_DOMAIN.concat("order/GetID/{orderId}"), HttpMethod.GET),

    // 取消订单
    CANCEL_ORDER(GlobalProperties.API_DOMAIN.concat("order/DeID/{orderId}"), HttpMethod.DELETE),

    // 支付订单
    PAY_ORDER(GlobalProperties.API_DOMAIN.concat("order/{orderId}/pay"), HttpMethod.GET),

    // 活动列表
    GET_CAMPAIGNS(GlobalProperties.API_DOMAIN.concat("campaigns"), HttpMethod.GET),

    // 活动详情
    GET_CAMPAIGN_DETAIL(GlobalProperties.API_DOMAIN.concat("campaign/GetID/{campaignId}"), HttpMethod.GET),

    // 帮助列表
    GET_HELPS(GlobalProperties.API_DOMAIN.concat("helps"), HttpMethod.GET),

    // 帮助详情
    GET_HELP_DETAIL(GlobalProperties.API_DOMAIN.concat("help/GetID/{helpId}"), HttpMethod.GET);

    public final String uri;

    public final HttpMethod method;

    private ApiUri(String uri, HttpMethod method) {
        this.uri = uri;
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getMethod() {
        return method;
    }
}
