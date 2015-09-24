<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>支付结果</title>
    <style>
    .btn-cell {
        padding-top: 10px;
        padding-bottom: 10px;
    }
    .btn-cell a {
        display: block;
        height: 35px;
        margin: 0 10px;
        border-radius: 3px;
        line-height: 35px;
        color: #fff;
    }
    .btn-red {
        background: #c00;
    }
    .btn-green {
        background: #6cb248;
    }
    </style>
</head>

<!-- header start -->
<c:set var="header_name" value="支付结果" />
<c:set var="current_menu" value="my-account" />
<c:set var="back_url" value="orders?status=all" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<!-- start -->
<div style="width:100%; max-width:640px; min-width:320px; margin:0 auto; padding-top:10px; text-align: center;">
    <div>
        <span><img src="resources/img/fail.png" width="42" height="42"></span>
    </div>
    <div>
        <span><font color="red">支付失败</font></span>
    </div>
    <div class="btn-cell">
        <a class="btn-red" href="orders?status=all"><span></span>查看订单</a>
    </div>
    <div class="btn-cell">
        <a class="btn-green" href="home"><span></span>返回首页</a>
    </div>
</div>
<!-- end -->

<!-- footer start -->
<%@ include file="../../layout/_footer.jsp"%>
<!-- footer end -->