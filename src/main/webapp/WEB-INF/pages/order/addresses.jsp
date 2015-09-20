<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
<title>填写订单</title>
<link rel="stylesheet" type="text/css" href="resources/css/checkout.css?v=${version}" />
</head>

<!-- header start -->
<c:set var="header_name" value="填写订单" />
<c:set var="current_menu" value="my-account" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- address start -->
<div class="common-wrapper">
    <div class="address">
        <c:forEach items="${addresses}" var="address">
            <div class="item-addr bdb-1px">
                <c:if test="${!empty sessionScope.addressId && address.addressId == sessionScope.addressId}">
                    <div class="ia-l"></div>
                </c:if>
                <div class="ia-m m ia-m78" onclick="javascript:selectAddress(${address.addressId})">
                    <div class="mt_new">
                        <span>${address.name}</span> <strong>${address.mobile}</strong>
                    </div>
                    <div class="mc">
                        <p>
                            <c:if test="${address.isDefault == true}">
                                <i class="sitem-tip">默认</i>
                            </c:if>
                            ${address.fullAddress}
                        </p>
                    </div>
                </div>
                <a class="ia-r" href="updateAddress/${address.addressId}?redirectURL=order/addresses"> <span class="iar-icon"></span></a>
            </div>
        </c:forEach>
    </div>

    <div class="pay-btn" style="background: #f8f8f8;">
        <a href="createAddress?redirectURL=order/addresses" class="btn1 tip-btn"><i class="plus">+</i>新建地址</a>
    </div>
</div>
<script type="text/javascript">
function selectAddress(id) {
	window.location.href = 'order/selectAddress/' + id;
}
</script>
<!-- address end -->