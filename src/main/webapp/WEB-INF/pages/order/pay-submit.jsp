<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>支付订单</title>
    <%-- <link rel="stylesheet" type="text/css" href="resources/css/pay.css?v=${version}" /> --%>
    <link rel="stylesheet" type="text/css" href="resources/css/checkout.css?v=${version}" />
</head>

<!-- header start -->
<c:set var="header_name" value="支付订单" />
<c:set var="current_menu" value="my-account" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- start -->
<div class="common-wrapper">
    <div class="w checkout" style="padding: 0px;">
        <c:forEach items="${products}" var="product">
        <div class="step3 border-1px">
            <a href="product/${product.productId}" class="s-href">
                <div class="s-item">
                    <div class="sitem-l">
                        <div class="sl-img">
                            <img src="${product.coverImg}">
                        </div>
                    </div>
                    <div class="sitem-m">
                        <p class="sitem-m-txt">${product.name}</p>
                        <p>×${product.qty}</p>
                    </div>
                    <div class="sitem-r">￥${product.buyPrice}</div>
                </div>
            </a>
        </div>
        </c:forEach>
        <div class="step4 border-1px">
            <div class="s-item">
                <div class="sitem-m">
                    交易金额：<span>￥${tradeAmount}</span>
                </div>
            </div>
        </div>
        <form method="post" action="${serverUrl}" id="payForm">
            <!--交易信息 start-->
            <input type="hidden" name="version" value="${tradeInfo.version}"/>
            <input type="hidden" name="token" value="${tradeInfo.token}"/>
            <input type="hidden" name="merchantSign" value="${tradeInfo.merchantSign}"/>
            <input type="hidden" name="merchantNum" value="${tradeInfo.merchantNum}"/>
            <input type="hidden" name="merchantRemark" value="${tradeInfo.merchantRemark}"/>
            <input type="hidden" name="tradeNum" value="${tradeInfo.tradeNum}"/>
            <input type="hidden" name="tradeName" value="${tradeInfo.tradeName}"/>
            <input type="hidden" name="tradeDescription" value="${tradeInfo.tradeDescription}"/>
            <input type="hidden" name="tradeTime" value="${tradeInfo.tradeTime}"/>
            <input type="hidden" name="tradeAmount" value="${tradeInfo.tradeAmount}"/>
            <input type="hidden" name="currency" value="${tradeInfo.currency}"/>
            <input type="hidden" name="notifyUrl" value="${tradeInfo.notifyUrl}"/>
            <input type="hidden" name="successCallbackUrl" value="${tradeInfo.successCallbackUrl}"/>
            <input type="hidden" name="failCallbackUrl" value="${tradeInfo.failCallbackUrl}"/>
            <!--交易信息 end-->
        </form>
        <div style="width: 96%; margin: 5% 2% 2% 2%;">
            <a href="javascript:;" id="J-next-btn" class="btn btn-actived">去支付</a>
        </div>
    </div>
</div>
<script>
    (function () {
        $('#J-next-btn').on('click', function () {
            $('#payForm').submit();
        });
    })();
</script>
<!-- end -->

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->