<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>订单详情</title>
    <link rel="stylesheet" type="text/css" href="resources/css/order.css?v=${version}" />
</head>

<!-- header start -->
<c:set var="header_name" value="订单详情" />
<c:set var="current_menu" value="my-account" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- order start -->
<div class="wrap">
    <section class="order-con">
        <ul class="order-list">
            <li>
                <div class="order-box">
                    <div class="order-width">
                        <p>订单编号：${order.orderNumber}</p>
                        <p>订单金额：￥${order.totalPrice}</p>
                        <p>订单日期：${order.createdTime}</p>
                        <p>订单状态：
                            <c:choose>
                                <c:when test="${order.status == 'Notred'}">未确认</c:when>
                                <c:when test="${order.status == 'Paid'}">已付款 </c:when>
                                <c:when test="${order.status == 'Stocking'}">已备货</c:when>
                                <c:when test="${order.status == 'Delivers'}">已发货</c:when>
                                <c:when test="${order.status == 'Receipt'}">已收货 </c:when>
                            </c:choose>
                        </p>
                    </div>
                    <c:if test="${order.status == 'Notred'}">
                        <div class="pay-button">
                            <a class="pay-order" href="gopay/${order.orderId}">立即支付</a>
                            <a class="cancel-order" href="javascript:void(0);">取消订单</a>
                        </div>
                    </c:if>
                </div>
            </li>
            <li>
                <div class="order-box">
                    <ul class="book-list">
                      <c:forEach items="${order.products}" var="p">
                        <li class="border-bottom">
                            <a href="product/${p.productId}">
                                <div class="order-msg">
                                    <img src="${p.coverImg}" class="img_ware">
                                    <div class="order-msg">
                                        <p class="title">${p.name}</p>
                                        <p class="price">¥${p.buyPrice}</p>
                                        <p class="order-data">X${p.qty}</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                      </c:forEach>
                    </ul>
                </div>
            </li>
            <li>
                <div class="order-box">
                    <div class="order-width">
                        <p class="border-bottom usr-name">
                            ${order.address.name} <span class="fr">${order.address.mobile}</span>
                        </p>
                        <p class="usr-addr">${order.address.fullAddress}</p>
                    </div>
                </div>
            </li>
            <li>
                <div class="order-box">
                    <div class="order-width">
                        <p class="border-bottom usr-name">付款方式: ${order.paymentMethod.name}<span class="fr"></span></p>
                        <p>${order.paymentMethod.paymentMethodId == 1 ? '金额' : order.paymentMethod.paymentMethodId == 2 ? '宝汇币' : '乾币'}: ${order.paymentMethod.amount}</p>
                        <p>宝豆: ${order.paymentMethod.baodou}</p>
                    </div>
                </div>
            </li>
        </ul>
    </section>
</div>
<!-- order end -->

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->
