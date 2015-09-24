package com.bh.wechat.jdpay.constant;

/**
 * 商户常量类 常量值在spring配置文件中从资源文件获取
 */
public class MerchantConstant {

    /**
     * 商户号
     */
    private String merchantNum;

    /**
     * 商户md5密钥
     */
    private String merchantMD5Key;

    /**
     * 商户des密钥
     */
    private String merchantDESKey;

    /**
     * 商户支付请求时的 rsa加密私钥
     */
    private String payRSAPrivateKey;

    /**
     * 交易查询/退款 rsa验签公钥
     */
    private String commonRSAPublicKey;

    /**
     * 支付成功 商户展示地址
     */
    private String successCallbackUrl;

    /**
     * 支付失败 商户展示地址
     */
    private String failCallbackUrl;

    /**
     * 接收异步通知地址
     */
    private String notifyUrl;

    /**
     * 网银支付服务地址
     */
    private String wangyinServerPayUrl;

    private String rechargeSuccessCallbackUrl;

    private String rechargeFailCallbackUrl;

    public String getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(String merchantNum) {
        this.merchantNum = merchantNum;
    }

    public String getMerchantMD5Key() {
        return merchantMD5Key;
    }

    public void setMerchantMD5Key(String merchantMD5Key) {
        this.merchantMD5Key = merchantMD5Key;
    }

    public String getMerchantDESKey() {
        return merchantDESKey;
    }

    public void setMerchantDESKey(String merchantDESKey) {
        this.merchantDESKey = merchantDESKey;
    }

    public String getPayRSAPrivateKey() {
        return payRSAPrivateKey;
    }

    public void setPayRSAPrivateKey(String payRSAPrivateKey) {
        this.payRSAPrivateKey = payRSAPrivateKey;
    }

    public String getCommonRSAPublicKey() {
        return commonRSAPublicKey;
    }

    public void setCommonRSAPublicKey(String commonRSAPublicKey) {
        this.commonRSAPublicKey = commonRSAPublicKey;
    }

    public String getSuccessCallbackUrl() {
        return successCallbackUrl;
    }

    public void setSuccessCallbackUrl(String successCallbackUrl) {
        this.successCallbackUrl = successCallbackUrl;
    }

    public String getFailCallbackUrl() {
        return failCallbackUrl;
    }

    public void setFailCallbackUrl(String failCallbackUrl) {
        this.failCallbackUrl = failCallbackUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getWangyinServerPayUrl() {
        return wangyinServerPayUrl;
    }

    public void setWangyinServerPayUrl(String wangyinServerPayUrl) {
        this.wangyinServerPayUrl = wangyinServerPayUrl;
    }

    public String getRechargeSuccessCallbackUrl() {
        return rechargeSuccessCallbackUrl;
    }

    public void setRechargeSuccessCallbackUrl(String rechargeSuccessCallbackUrl) {
        this.rechargeSuccessCallbackUrl = rechargeSuccessCallbackUrl;
    }

    public String getRechargeFailCallbackUrl() {
        return rechargeFailCallbackUrl;
    }

    public void setRechargeFailCallbackUrl(String rechargeFailCallbackUrl) {
        this.rechargeFailCallbackUrl = rechargeFailCallbackUrl;
    }

}
