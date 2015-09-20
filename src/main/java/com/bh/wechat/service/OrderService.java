package com.bh.wechat.service;

import com.bh.wechat.exception.BhException;
import com.bh.wechat.request.OrderCancelRequest;
import com.bh.wechat.request.OrderCreateDirectlyRequest;
import com.bh.wechat.request.OrderCreateRequest;
import com.bh.wechat.request.OrderDetailRequest;
import com.bh.wechat.request.OrderListRequest;
import com.bh.wechat.response.BaseResponse;
import com.bh.wechat.response.OrderDetailResponse;
import com.bh.wechat.response.OrderListResponse;

public interface OrderService {

    /**
     * 获取订单列表
     * 
     * @param request
     * @return
     * @throws BhException
     */
    OrderListResponse listOrders(OrderListRequest request) throws BhException;

    /**
     * 直接下单
     * 
     * @param request
     * @return
     * @throws BhException
     */
    OrderDetailResponse createOrderDirectly(OrderCreateDirectlyRequest request) throws BhException;

    /**
     * 下单
     * 
     * @param request
     * @return
     * @throws BhException
     */
    OrderDetailResponse createOrder(OrderCreateRequest request) throws BhException;

    /**
     * 获取订单详情
     * 
     * @param request
     * @return
     * @throws BhException
     */
    OrderDetailResponse getOrderDetail(OrderDetailRequest request) throws BhException;

    /**
     * 取消订单
     * 
     * @param request
     * @return
     * @throws BhException
     */
    BaseResponse cancelOrder(OrderCancelRequest request) throws BhException;
}
