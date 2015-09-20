package com.bh.wechat.gateway;

import com.bh.wechat.exception.BhException;

/**
 * Http网关服务代理模板.
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月4日 下午3:27:10
 */
public interface HttpGatewayTemplate {

    double NANO_UNIT = 1000000000.0;

    <T> T invoke(Class<T> responseType, Object request) throws BhException;

    <T> T invoke(GatewayProxyRequest<T> request) throws BhException;
}