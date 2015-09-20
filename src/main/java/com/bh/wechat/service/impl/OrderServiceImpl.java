package com.bh.wechat.service.impl;

import org.springframework.stereotype.Service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.OrderCancelRequest;
import com.bh.wechat.request.OrderCreateDirectlyRequest;
import com.bh.wechat.request.OrderCreateRequest;
import com.bh.wechat.request.OrderDetailRequest;
import com.bh.wechat.request.OrderListRequest;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.OrderDetailResponse;
import com.bh.wechat.response.OrderListResponse;
import com.bh.wechat.service.OrderService;

/**
 * @author 刘飞 E-mail:liufei_it@126.com
 *
 * @version 1.0.0
 * @since 2015年8月5日 下午2:10:49
 */
@Service
public class OrderServiceImpl extends BaseService implements OrderService {

    @Override
    public OrderListResponse listOrders(OrderListRequest request) throws BhException {
        return httpGatewayTemplate.invoke(OrderListResponse.class, request);
    }

    @Override
    public OrderDetailResponse createOrderDirectly(OrderCreateDirectlyRequest request) throws BhException {
        return httpGatewayTemplate.invoke(OrderDetailResponse.class, request);
    }

    @Override
    public OrderDetailResponse createOrder(OrderCreateRequest request) throws BhException {
        return httpGatewayTemplate.invoke(OrderDetailResponse.class, request);
    }

    @Override
    public OrderDetailResponse getOrderDetail(OrderDetailRequest request) throws BhException {
        return httpGatewayTemplate.invoke(OrderDetailResponse.class, request);
    }

    @Override
    public BaseResponse cancelOrder(OrderCancelRequest request) throws BhException {
        return httpGatewayTemplate.invoke(BaseResponse.class, request);
    }

}
