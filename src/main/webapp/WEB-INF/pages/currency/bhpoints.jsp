<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../config.jsp"%>
<head>
    <title>宝汇币</title>
    <link rel="stylesheet" type="text/css" href="resources/css/currency.css?v=${version}" />
</head>

<!-- header start -->
<c:set var="header_name" value="宝汇币" />
<c:set var="current_menu" value="my-account" />
<c:set var="back_url" value="profile" />
<%@ include file="../../layout/_header_menu.jsp"%>
<!-- header end -->

<div class="bh-detail">
    <div>
        <img width="100" height="100" src="resources/img/currency.png">
    </div>
    <div style="padding-top: 10px;">
        <span style="display:block; font-size:20px; font-weight:500;">我的宝汇币</span>
        <span style="display:block; padding-top:5px; font-size:30px; font-weight:bold;">${bhPoints}</span>
    </div>
    <div style="padding-top: 30px;">
        <span style="display:block;">
            <a href="recharge" class="btn btn-recharge" style="margin: 0 8% 0 8%;">充值</a>
        </span>
        <span style="display:block; padding-top: 15px;">
            <a href="withdraw" class="btn btn-withdraw" style="margin: 0 8% 0 8%;">提现</a>
        </span>
    </div>
    <div style="padding: 50px 0 20px 0;">
        <span style="position: relative; float: left; width: 50%;">
            <a href="deal/history/bhPoints">交易明细</a>
        </span>
        <span style="position: relative; float: right; width: 50%;">
            <a href="bhPoints/withdraw/history">提现历史</a>
        </span>
    </div>
</div>

<!-- footer start -->
<div style="clear: both; padding-top: 10px">
    <%@ include file="../../layout/_footer.jsp"%>
</div>
<!-- footer end -->